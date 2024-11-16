package calculator.domain.usecase

import calculator.domain.rule.Constants.CUSTOM_SEPARATOR_PREFIX
import calculator.domain.rule.Constants.CUSTOM_SEPARATOR_SUFFIX
import calculator.domain.rule.Exception
import calculator.domain.model.Separator

class CheckValidationUseCase {
    operator fun invoke(input: String): List<String> {
        checkEmpty(input)
        checkPrefixValid(input)
        return when (val separator = Separator.create(input)) {
            is Separator.CustomSeparator -> checkCustomValidation(separator)
            is Separator.DefaultSeparator -> checkDefaultValidation(separator)
        }
    }

    private fun checkCustomValidation(separator: Separator.CustomSeparator): List<String> {
        val separatedInput = separator.separatedInput
        checkCustomDelimiterIsEmpty(separator.separator)
        checkIsAllSeparatedByDelimiter(separatedInput)
        separatedInput.forEach { checkCustomDelimiterValidation(it) }
        return separatedInput
    }

    private fun checkDefaultValidation(separator: Separator.DefaultSeparator): List<String> {
        val separatedInput = separator.separatedInput
        checkDefaultDelimiterInput(separatedInput.joinToString(""))
        separatedInput.forEach { checkIsValidNumeric(it) }
        return separatedInput
    }

    private fun checkEmpty(input: String) {
        require(input.isNotBlank()) { Exception.EMPTY_INPUT }
    }

    private fun checkCustomDelimiterValidation(input: String) {
        require((input.contains("$CUSTOM_SEPARATOR_SUFFIX")).not()) {
            Exception.INVALID_CUSTOM_DELIMITER
        }
    }

    private fun checkCustomDelimiterIsEmpty(input: String) {
        require(input.isEmpty().not()) { Exception.INVALID_CUSTOM_DELIMITER }
    }

    private fun checkIsValidNumeric(input: String) {
        requireNotNull(input.toIntOrNull()) { Exception.INVALID_TYPE }
    }

    private fun checkDefaultDelimiterInput(input: String) {
        require(input.matches(REGEX_FOR_DEFAULT)) { Exception.INVALID_INPUT_FORMAT }
    }

    private fun checkIsAllSeparatedByDelimiter(input: List<String>) {
        input.forEach { str ->
            str.map { require(it.isDigit()) { Exception.INVALID_INPUT.invalidInputFormat(str) } }
        }
    }

    private fun checkPrefixValid(input: String) {
        if (input.contains("$CUSTOM_SEPARATOR_PREFIX") &&
            input.contains("$CUSTOM_SEPARATOR_SUFFIX")
        ) {
            require(input.startsWith("$CUSTOM_SEPARATOR_PREFIX")) {
                Exception.INVALID_INPUT_FORMAT
            }
        }
    }

    companion object {
        private val REGEX_FOR_DEFAULT = Regex("^[0-9;,]+")
    }
}