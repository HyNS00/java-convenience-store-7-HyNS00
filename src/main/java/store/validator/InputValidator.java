package store.validator;

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
            throw new IllegalArgumentException("빈 칸은 입력될 수 없습니다.");
        }
    }

    private void validateOrderFormat(String input) {
        String regex = "^\\[([가-힣]+)\\-([1-9][0-9]*)\\](,\\[([가-힣]+)\\-([1-9]+)\\])*$";
        Pattern compile = Pattern.compile(regex);
        if (!compile.matcher(input).matches()) {
            throw new IllegalArgumentException("올바르지 않은 구매 형식입니다.");
        }
    }

    private void validateResponseForMat(String input) {
        String regex = "^[YN]$";
        Pattern compile = Pattern.compile(regex);
        if (!compile.matcher(input).matches()) {
            throw new IllegalArgumentException("Y 또는 N만 입력 가능합니다.");
        }
    }
}
