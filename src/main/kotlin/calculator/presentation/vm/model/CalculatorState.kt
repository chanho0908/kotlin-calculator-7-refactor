package calculator.presentation.vm.model

import calculator.domain.rule.OutPut
import calculator.presentation.event.UiEvent

data class CalculatorState(
    val uiEvent: UiEvent
) {
    companion object {
        fun create(): CalculatorState {
            return CalculatorState(
                uiEvent = UiEvent.UserAccess("${OutPut.MESSAGE_INPUT_EMPTY}")
            )
        }
    }
}