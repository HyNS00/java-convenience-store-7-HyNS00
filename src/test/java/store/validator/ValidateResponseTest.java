package store.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.enums.ExceptionMessage;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class ValidateResponseTest {
    private InputValidator inputValidator;

    @BeforeEach
    void setup() {
        inputValidator = new InputValidator();
    }

    @DisplayName("빈 칸을 입력했을 때, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void validateNull_throwsException(String input) {
        assertThatThrownBy(() -> inputValidator.validateResponse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_INPUT.getMessage());
    }

    @DisplayName("Y와 N이 아닌 다른 문자를 입력했을 때, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "A", "a", "!", "y", "n"})
    void validateResponseFormat_throwsException(String input) {
        assertThatThrownBy(() -> inputValidator.validateResponse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_INPUT.getMessage());
    }

    @DisplayName("올바른 응답 형식의 경우")
    @ParameterizedTest
    @ValueSource(strings = {"Y", "N"})
    void validateResponseFormat_success(String input) {
        assertThatCode(() -> inputValidator.validateResponse(input))
                .doesNotThrowAnyException();
    }
}