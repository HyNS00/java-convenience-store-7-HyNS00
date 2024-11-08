package store.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class ValidateOrderTest {
    private InputValidator inputValidator;

    @BeforeEach
    void setup() {
        inputValidator = new InputValidator();
    }

    @DisplayName("빈 칸을 입력했을 때, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void validateNull_throwsException(String input) {
        assertThatThrownBy(() -> inputValidator.validateOrder(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 칸은 입력될 수 없습니다.");
    }

    @DisplayName("올바르지 않은 구매형식일 때, 대괄호 형식이 잘못된 경우")
    @ParameterizedTest
    @ValueSource(strings = {"{사이다-3}", "(사이다-3)", "[사이다-3", "사이다-3]"})
    void validateOrderFormat_invalidBracket_throwsException(String input) {
        assertThatThrownBy(() -> inputValidator.validateOrder(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 구매 형식입니다.");
    }

    @DisplayName("올바르지 않은 구매형식일 때, 숫자가 아닌 문자일 경우")
    @ParameterizedTest
    @ValueSource(strings = {"[사이다-ㅁ]", "[사이다-0]", "[사이다-aa]"})
    void validateOrderFormat_invalid_Number_throwsException(String input) {
        assertThatThrownBy(() -> inputValidator.validateOrder(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 구매 형식입니다.");
    }

    @DisplayName("올바르지 않은 구매형식일 때, 공백이 포함된 경우")
    @ParameterizedTest
    @ValueSource(strings = {"[사이다- 3]", "[ 사이다-3]", "[ 사이다-3 ]", "[사이다 - 3]"})
    void validateOrderFormat_containsWhiteSpace_throwsException(String input) {
        assertThatThrownBy(() -> inputValidator.validateOrder(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 구매 형식입니다.");
    }

    @DisplayName("올바르지 않은 연속된 구매형식일 때")
    @ParameterizedTest
    @ValueSource(strings = {"[사이다-3][콜라-4]", "[사이다-3], [콜라-3]"})
    void validateOrderFormat_whenInValidConsecutiveInput_throwsException(String input) {
        assertThatThrownBy(() -> inputValidator.validateOrder(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 구매 형식입니다.");
    }

    @DisplayName("올바른 구매형식 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"[빼빼로-3]", "[빼빼로-3],[콜라-3]"})
    void validateOrderFormat_success(String input) {
        assertThatCode(() -> inputValidator.validateOrder(input))
                .doesNotThrowAnyException();
    }
}