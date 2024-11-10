package store.model;

public class Receipt {
    private final long totalAmount;
    private final long membershipDiscount;
    private final long bonusDiscount;
    private final long finalAmount;

    public Receipt(long totalAmount, long membershipDiscount, long bonusDiscount) {
        this.totalAmount = totalAmount;
        this.membershipDiscount = membershipDiscount;
        this.bonusDiscount = bonusDiscount;
        this.finalAmount = totalAmount - membershipDiscount - bonusDiscount;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public long getMembershipDiscount() {
        return membershipDiscount;
    }

    public long getBonusDiscount() {
        return bonusDiscount;
    }

    public long getFinalAmount() {
        return finalAmount;
    }
}
