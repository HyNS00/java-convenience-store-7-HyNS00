package store.enums;

public enum DisplayMessage {
    WELCOME("\n안녕하세요. W편의점입니다."),
    CURRENT_STOCK("현재 보유하고 있는 상품입니다.\n"),
    STORE_HEADER("\n==============W 편의점================"),
    ITEM_HEADER("상품명            수량         금액"),
    BONUS_HEADER("=============증\t정==================\n"),
    SEPARATOR_LINE("====================================");

    private final String message;

    DisplayMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
