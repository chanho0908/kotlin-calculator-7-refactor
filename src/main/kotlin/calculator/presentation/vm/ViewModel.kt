package calculator.presentation.vm

import calculator.domain.rule.OutPut
import calculator.domain.usecase.CheckValidationUseCase
import calculator.presentation.event.UiEvent
import calculator.presentation.vm.model.CalculatorState

class ViewModel(
    private val checkValidationUseCase: CheckValidationUseCase
) {
    private var _state = CalculatorState.create()
    val state: CalculatorState
        get() = _state

    fun onCompleteUserInput(input: String) {
        val result = checkValidationUseCase(input)
        val sum = result.sumOf { it.toInt() }
        val newEvent = UiEvent.CalculateComplete(OutPut.resultFormat(sum))
        _state = _state.copy(uiEvent = newEvent)
    }
}