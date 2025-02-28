package ir.halion.peak.peresenter.focus

sealed class FocusEvent {
    data object Start : FocusEvent()
    data object Stop : FocusEvent()
    data object UpdateTask : FocusEvent()
    data class UpdateTimer(var time: Int) : FocusEvent()
}