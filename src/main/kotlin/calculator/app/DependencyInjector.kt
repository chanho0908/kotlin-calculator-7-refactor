package calculator.app

import calculator.domain.usecase.CheckValidationUseCase
import calculator.presentation.controller.ViewController
import calculator.presentation.view.InputView
import calculator.presentation.view.OutputView
import calculator.presentation.vm.ViewModel

class DependencyInjector {

    fun injectViewController(): ViewController {
        val inputView = injectInputView()
        val outputView = injectOutPutView()
        val viewModel = injectViewModel()
        return ViewController(viewModel, inputView, outputView)
    }

    private fun injectViewModel(): ViewModel {
        val checkValidationUseCase = CheckValidationUseCase()
        return ViewModel(checkValidationUseCase)
    }

    private fun injectInputView() = InputView()
    private fun injectOutPutView() = OutputView()
}