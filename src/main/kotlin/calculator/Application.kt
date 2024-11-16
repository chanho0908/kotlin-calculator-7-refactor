package calculator

import calculator.app.DependencyInjector

fun main() {
    val di = DependencyInjector()
    di.injectViewController()
}