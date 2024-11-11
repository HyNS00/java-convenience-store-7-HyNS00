package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.enums.InputMessage;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionResult;
import store.validator.InputValidator;

public class InputView {
    private final InputValidator inputValidator;

    public InputView(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public String requestOrder() {
        System.out.println(InputMessage.REQUEST_ORDER.getMessage());
        String input = Console.readLine();
        inputValidator.validateOrder(input);
        return input;
    }

    public String requestPlusOrder(Product product) {
        Promotion promotion = product.getPromotion();
        System.out.printf(InputMessage.REQUEST_ADDITIONAL_PRODUCT.getMessage(), product.getName());
        String input = Console.readLine();
        inputValidator.validateResponse(input);
        return input;
    }

    public String requestPartialPromotion(String name, PromotionResult result) {
        int partialPromo = result.getPartialPromo();
        System.out.printf(InputMessage.REQUEST_PARTIAL_PROMOTION.getMessage(), name, partialPromo);
        String input = Console.readLine();
        inputValidator.validateResponse(input);
        return input;
    }

    public String requestMembershipOption() {
        System.out.println(InputMessage.REQUEST_MEMBERSHIP_DISCOUNT.getMessage());
        String input = Console.readLine();
        inputValidator.validateResponse(input);
        return input;
    }

    public String requestAdditionalOrder() {
        System.out.println(InputMessage.REQUEST_START_AGAIN.getMessage());
        String input = Console.readLine();
        inputValidator.validateResponse(input);
        return input;
    }
}
