package calculator.presentation.event

sealed class UiEvent {
    data class UserAccess(val msg: String) : UiEvent()
    data class CalculateComplete(val msg: String) : UiEvent()
}