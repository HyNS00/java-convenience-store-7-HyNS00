package store.view;

import camp.nextstep.edu.missionutils.Console;
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
        System.out.println("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = Console.readLine();
        inputValidator.validateOrder(input);
        return input;
    }

    public String requestPlusOrder(Product product) {
        Promotion promotion = product.getPromotion();
        System.out.printf("\n현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n", product.getName());
        String input = Console.readLine();
        inputValidator.validateResponse(input);
        return input;
    }

    public String requestPartialPromotion(String name, PromotionResult result) {
        int partialPromo = result.getPartialPromo();
        System.out.printf("\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n", name, partialPromo);
        String input = Console.readLine();
        inputValidator.validateResponse(input);
        return input;
    }

    public String requestMembershipOption() {
        System.out.println("\n멤버십 할인을 받으시겠습니까? (Y/N)");
        String input = Console.readLine();
        inputValidator.validateResponse(input);
        return input;
    }

    public String requestAdditionalOrder() {
        System.out.println("\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        String input = Console.readLine();
        inputValidator.validateResponse(input);
        return input;
    }
}
