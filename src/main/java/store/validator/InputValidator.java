package store.validator;

import store.enums.ExceptionMessage;

import java.util.regex.Pattern;

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
        String regex = "^\\[([가-힣]+)\\-([1-9][0-9]*)\\](,\\[([가-힣]+)\\-([1-9]+)\\])*$";
        Pattern compile = Pattern.compile(regex);
        if (!compile.matcher(input).matches()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_FORMAT.getMessage());
        }
    }

    private void validateResponseForMat(String input) {
        String regex = "^[YN]$";
        Pattern compile = Pattern.compile(regex);
        if (!compile.matcher(input).matches()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_INPUT.getMessage());
        }
    }
}
