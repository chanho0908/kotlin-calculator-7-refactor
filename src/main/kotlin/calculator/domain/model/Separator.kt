package calculator.domain.model

import calculator.domain.rule.Constants.DEFAULT_SEPARATOR_COLON
import calculator.domain.rule.Constants.DEFAULT_SEPARATOR_COMMA
import calculator.domain.util.removeCustomPrefix
import calculator.domain.util.splitByLineBreak

sealed class Separator {
    data class CustomSeparator(
        val separatedInput: List<String>,
        val separator: String
    ) : Separator()

    data class DefaultSeparator(val separatedInput: List<String>) : Separator()

    companion object {
        fun create(input: String): Separator {
            val spliterators = input.splitByLineBreak()
            return if (spliterators.size == 2) {
                whenUserInputCustomSeparator(spliterators)
            } else {
                whenUserNotInputCustomSeparator(input)
            }
        }

        private fun whenUserInputCustomSeparator(spliterators: List<String>): CustomSeparator {
            val separator = spliterators[0].removeCustomPrefix()
            return CustomSeparator(removeEmptyValue(spliterators[1].split(separator)), separator)
        }

        private fun whenUserNotInputCustomSeparator(input: String): DefaultSeparator {
            val separator =
                input.split(*arrayOf("$DEFAULT_SEPARATOR_COLON", "$DEFAULT_SEPARATOR_COMMA"))
            val removed = removeEmptyValue(separator)
            return DefaultSeparator(removed)
        }

        private fun removeEmptyValue(input: List<String>): List<String> {
            return input.filter { it.isNotEmpty() }
        }
    }
}

