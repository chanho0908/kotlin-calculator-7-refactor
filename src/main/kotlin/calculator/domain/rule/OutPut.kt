package calculator.domain.rule

enum class OutPut(private val msg: String) {
    MESSAGE_INPUT_EMPTY("덧셈할 문자열을 입력해 주세요."),
    RESULT("결과 : %d");

    override fun toString(): String = msg

    companion object {
        fun resultFormat(result: Int): String {
            return RESULT.toString().format(result)
        }
    }
}