package store.validator;

import store.enums.ExceptionMessage;
import store.enums.RegexFormat;

public class InputValidator {

    public void validateOrder(String input) {
        validateNotBlank(input);
        validateOrderFormat(input);
    }

    public void validateResponse(String input) {
        validateNotBlank(input);
        validateResponseForMat(input);
    }

    private void validateNotBlank(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_INPUT.getMessage());
        }
    }

    private void validateOrderFormat(String input) {
        if (!RegexFormat.VALID_ORDER_FORMAT.getPattern().matcher(input).matches()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_FORMAT.getMessage());
        }
    }

    private void validateResponseForMat(String input) {
        if (!RegexFormat.VALID_RESPONSE_FORMAT.getPattern().matcher(input).matches()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_INPUT.getMessage());
        }
    }
}
