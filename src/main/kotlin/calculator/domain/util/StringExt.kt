package calculator.domain.util

import calculator.domain.rule.Constants.CUSTOM_SEPARATOR_PREFIX
import calculator.domain.rule.Constants.CUSTOM_SEPARATOR_SUFFIX

fun String.splitByLineBreak(): List<String> = this.split("$CUSTOM_SEPARATOR_SUFFIX", limit = 2)

fun String.removeCustomPrefix() = this.removePrefix(prefix = "$CUSTOM_SEPARATOR_PREFIX")