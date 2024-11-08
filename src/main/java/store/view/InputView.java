package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.validator.InputValidator;

public class InputView {
    private final InputValidator inputValidator;

    public InputView(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public String requestOrder() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = Console.readLine();
        inputValidator.validateOrder(input);
        return input;
    }

    public String requestMembershipOption() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        String input = Console.readLine();
        inputValidator.validateResponse(input);
        return input;
    }

    public String requestAdditionalOrder() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        String input = Console.readLine();
        inputValidator.validateResponse(input);
        return input;
    }
}
