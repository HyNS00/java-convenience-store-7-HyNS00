package store.model;

public class OrderItem {
    private final String productName;
    private final PromotionResult result;

    public OrderItem(final String productName, final PromotionResult result) {
        this.productName = productName;
        this.result = result;
    }

    public int getTotal(){
        return result.getTotal();
    }

    public String getProductName() {
        return productName;
    }

    public PromotionResult getResult() {
        return result;
    }
}
