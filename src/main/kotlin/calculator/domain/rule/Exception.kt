package calculator.domain.rule

enum class Exception(private val message: String) {
    EMPTY_INPUT("빈 값을 입력하였습니다."),
    INVALID_INPUT("%s는 잘못된 입력입니다."),
    INVALID_INPUT_FORMAT("잘못된 입력 형식 입니다."),
    INVALID_CUSTOM_DELIMITER("커스텀 구분자를 잘못 입력했어요. \\n를 제외한 커스텀 구분자를 입력해주세요."),
    INVALID_TYPE("정수만 입력해주세요.");

    override fun toString(): String = "$ERROR $message"

    fun invalidInputFormat(input: String): String = String.format(message, input)

    companion object {
        private const val ERROR = "[ERROR]"
    }
}