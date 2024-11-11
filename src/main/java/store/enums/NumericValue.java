package store.enums;

public enum NumericValue {
    PRODUCT_INFO_LENGTH(4),
    PROMOTION_INFO_LENGTH(5),
    MAX_MEMBERSHIP_DISCOUNT(8000),
    DISCOUNT_UNIT(1000);
    private final int value;

    NumericValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
