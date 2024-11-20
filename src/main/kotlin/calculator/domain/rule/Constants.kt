package calculator.domain.rule

enum class Constants(private val value: String) {
    DEFAULT_SEPARATOR_COLON(":"),
    DEFAULT_SEPARATOR_COMMA(","),
    CUSTOM_SEPARATOR_PREFIX("//"),
    CUSTOM_SEPARATOR_SUFFIX("\\n");

    override fun toString(): String = value
}