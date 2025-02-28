package ir.halion.peak.peresenter.focus

import android.app.Dialog
import androidx.compose.runtime.Immutable

@Immutable
data class FocusState(
    val time: Int = 60,
    var counter: Int = time,
    val isStart: Boolean = false,
)
