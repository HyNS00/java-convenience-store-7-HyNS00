package store.enums;

public enum OutputMessage {
    ERROR_PREFIX("[ERROR] "),
    PRODUCT_FORMAT("- %s %,d원 %s%s"),
    PRODUCT_QUANTITY("%d개"),
    EMPTY(""),
    WHITE_SPACE(" "),
    PURCHASE_ORDER_ITEM_FORMAT("%-8s      %2d         %,8d%n"),
    BONUS_ITEM_FORMAT("%-12s      %d%n"),
    TOTAL_AMOUNT_FORMAT("총구매액          %2d       %,8d%n"),
    FINAL_AMOUNT_FORMAT("내실돈                   %,8d\n"),
    DISCOUNT_FORMAT("%-8s                 %+,8d%n"),
    ZERO_DISCOUNT_FORMAT("%-8s                 %8s%n"),
    DISCOUNT_ZERO("-0"),
    OUT_OF_STOCK("재고 없음");

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
