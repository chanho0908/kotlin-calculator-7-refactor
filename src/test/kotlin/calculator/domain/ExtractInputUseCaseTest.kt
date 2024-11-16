package calculator.domain

import calculator.domain.rule.Exception
import calculator.domain.usecase.CheckValidationUseCase
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ExtractInputUseCaseTest {
    private lateinit var checkValidationUseCase: CheckValidationUseCase

    @BeforeEach
    fun setUp() {
        checkValidationUseCase = CheckValidationUseCase()
    }

    @Test
    fun `입력값이_비어있을_때_예외_테스트`() {
        Assertions.assertThatThrownBy {
            checkValidationUseCase("  ")
        }.isInstanceOf(IllegalArgumentException::class.java).hasMessage("${Exception.EMPTY_INPUT}")
    }

    @Test
    fun `커스텀_구분자가_개행문자일_때_예외_테스트`() {
        Assertions.assertThatThrownBy {
            checkValidationUseCase("//\\n\\n1;2;3")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("${Exception.INVALID_CUSTOM_DELIMITER}")
    }

    @Test
    fun `커스텀_구분자가_개행문자일_때_예외_테스트2`() {
        Assertions.assertThatThrownBy {
            checkValidationUseCase("//\\n\\n\\n\\n\\n\\n1;2;3")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("${Exception.INVALID_CUSTOM_DELIMITER}")
    }

    @Test
    fun `커스텀_구분자가_비어있을_때_예외_테스트`() {
        Assertions.assertThatThrownBy {
            checkValidationUseCase("//\\n1;2;3")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("${Exception.INVALID_CUSTOM_DELIMITER}")
    }

    @Test
    fun `커스텀_구분자가_입력시_숫자_구분자가_커스텀_구분자와_다를_때_예외_테스트`() {
        Assertions.assertThatThrownBy {
            checkValidationUseCase("//;\\n1-2-3")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.INVALID_INPUT.invalidInputFormat("1-2-3"))
    }

    @Test
    fun `커스텀_구분자_입력시_정수가_아닐_떄_예외_테스트`() {
        Assertions.assertThatThrownBy {
            checkValidationUseCase("//-\\n0.1-0.2-0.3")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.INVALID_INPUT.invalidInputFormat("0.1"))
    }

    @Test
    fun `커스텀_구분자시_음수일_떄_예외_테스트`() {
        Assertions.assertThatThrownBy {
            checkValidationUseCase("//;\\n-1;-2;-3")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.INVALID_INPUT.invalidInputFormat("-1"))
    }

    @Test
    fun `커스텀_구분자가_앞단에_잘못된_값이_있을_때_에러_테스트`() {
        Assertions.assertThatThrownBy {
            checkValidationUseCase("^//;\\n1;2;;3")
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("${Exception.INVALID_INPUT_FORMAT}")
    }

    @Test
    fun `커스텀_구분자가_있을_때_정상_케이스_테스트`() {
        val result = checkValidationUseCase("//;\\n1;2;3")
        assertThat(result).containsExactly("1", "2", "3")
    }

    @Test
    fun `커스텀_구분자가_빈값이_있을_때_정상_케이스_테스트`() {
        val result = checkValidationUseCase("//;\\n;;;2;;;;;3")
        assertThat(result).containsExactly("2", "3")
    }

    @Test
    fun `커스텀 구분자로 개행 문자가 아닌 문자로 분리할 때 정상 케이스 테스트`() {
        val result = checkValidationUseCase("//-\\n4-5-6")
        assertThat(result).containsExactly("4", "5", "6")
    }

    @Test
    fun `커스텀 구분자가 한 글자 이상일 때 정상 케이스 테스트`() {
        val result = checkValidationUseCase("//#$@\\n7#$@8#$@9")
        assertThat(result).containsExactly("7", "8", "9")
    }

    @Test
    fun `기본_구분자_입력_형식_에러_테스트`() {
        val result = checkValidationUseCase(",,2::2:3::,,,6")
        assertThat(result).containsExactly("2", "2", "3", "6")
    }

    @ParameterizedTest
    @MethodSource("기본_구분자_입력_형식_에러_테스트_데이터")
    fun `기본_구분자_입력시_공백_입력시에도_성공_테스트`(input: String) {
        Assertions.assertThatThrownBy {
            checkValidationUseCase(input)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("${Exception.INVALID_INPUT_FORMAT}")
    }


    companion object {
        @JvmStatic
        fun `기본_구분자_입력_형식_에러_테스트_데이터`() = Stream.of(
            Arguments.of("1,2-;3"),
            Arguments.of("4:5-6"),
            Arguments.of("7;8....9"),
            Arguments.of("7..5,2"),
        )
    }
}