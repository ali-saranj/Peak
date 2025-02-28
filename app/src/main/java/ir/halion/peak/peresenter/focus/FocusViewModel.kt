package ir.halion.peak.peresenter.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

class FocusViewModel : ViewModel() {
    private var _focusState = MutableStateFlow(FocusState())
    val focusState = _focusState.asStateFlow()


    public fun eventHandler(focusEvent: FocusEvent) {
        when (focusEvent) {
            is FocusEvent.Start -> startTimer()
            is FocusEvent.Stop -> stopTimer()
            is FocusEvent.UpdateTask -> TODO()
            is FocusEvent.UpdateTimer -> updateTime(focusEvent.time)
        }
    }

    private fun updateTime(time: Int) {
        _focusState.update { it.copy(time = time, counter = time) }
    }

    private lateinit var timeJob: Job

    private fun startTimer() {
        _focusState.update {
            it.copy(isStart = true)
        }
        timeJob = viewModelScope.launch {
            for (i in _focusState.value.time downTo 0) {
                if (isActive) {
                    _focusState.update { it.copy(counter = i) }
                    delay(1000)
                    if (i == 0) {
                        cancel()
                    }
                }
            }
        }
    }

    private fun stopTimer() {
        _focusState.update {
            it.copy(isStart = false, counter = _focusState.value.time)
        }
        timeJob.cancel()
    }
}