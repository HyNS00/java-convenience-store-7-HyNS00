package store.enums;

public enum ExceptionMessage {
    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    EXCEED_STOCK_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),
    FILE_READ_ERROR("파일 읽는 것을 실패했습니다. 파일을 확인해주세요."),
    FILE_NOT_FOUND("파일을 찾을 수 없습니다. 파일 이름을 확인해주세요."),
    DUPLICATE_ORDER("주문 상품이 중복됩니다. 함께 주문해주세요.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
