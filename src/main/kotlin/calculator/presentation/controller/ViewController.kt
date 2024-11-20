package calculator.presentation.controller

import calculator.presentation.event.UiEvent
import calculator.presentation.view.InputView
import calculator.presentation.view.OutputView
import calculator.presentation.vm.ViewModel

class ViewController(
    private val viewModel: ViewModel,
    private val inputView: InputView,
    private val outputView: OutputView
) {
    init {
        checkUiState()
    }

    private fun checkUiState() {
        when (val event = viewModel.state.uiEvent) {
            is UiEvent.UserAccess -> onUiEventUserAccess(event.msg)
            is UiEvent.CalculateComplete -> onUiEventCalculateComplete(event.msg)
        }
    }

    private fun onUiEventUserAccess(msg: String) {
        outputView.printGuideMessage(msg)
        val input = inputView.readItem()
        viewModel.onCompleteUserInput(input)
        checkUiState()
    }

    private fun onUiEventCalculateComplete(msg: String) {
        outputView.printGuideMessage(msg)
    }
}