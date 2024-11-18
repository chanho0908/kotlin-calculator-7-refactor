package calculator.presentation.event

sealed interface UiEvent {
    val msg: String

    data class UserAccess(override val msg: String) : UiEvent
    data class CalculateComplete(override val msg: String) : UiEvent
}