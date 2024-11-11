package store.enums;

public enum DiscountType {
    BONUS("행사할인"),
    MEMBERSHIP("멤버십할인");

    private final String displayName;

    DiscountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
