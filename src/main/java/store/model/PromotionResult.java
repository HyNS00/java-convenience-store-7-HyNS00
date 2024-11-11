package store.model;

public class PromotionResult {
    private final int promotionPurchase;
    private final int promotionBonus;
    private final int normalPurchaseFromPromo;
    private final int normalPurchaseFromNormal;

    public PromotionResult(int promotionPurchase, int promotionBonus, int normalPurchaseFromPromo, int normalPurchaseFromNormal) {
        this.promotionPurchase = promotionPurchase;
        this.promotionBonus = promotionBonus;
        this.normalPurchaseFromPromo = normalPurchaseFromPromo;
        this.normalPurchaseFromNormal = normalPurchaseFromNormal;
    }

    public static PromotionResult calculate(Products products, String productName, int totalQuantity) {
        Product promotionProduct = products.findPromotionProduct(productName);
        if (isInvalidPromotion(promotionProduct)) {
            return new PromotionResult(0, 0, 0, totalQuantity);
        }

        return calculateValidPromotion(promotionProduct, totalQuantity);
    }

    private static boolean isInvalidPromotion(Product promotionProduct) {
        return promotionProduct == null
                || !promotionProduct.getPromotion().isValidPromotion()
                || promotionProduct.getQuantity() == 0;
    }

    private static PromotionResult calculateValidPromotion(Product promotionProduct, int totalQuantity) {
        Promotion promotion = promotionProduct.getPromotion();
        int availablePromotionStock = promotionProduct.getQuantity();

        int set = promotion.getBuy() + promotion.getBonus();
        int availableSet = Math.min(availablePromotionStock / set, totalQuantity / set);

        int promotionPurchase = promotion.getBuy() * availableSet;
        int promotionBonus = promotion.getBonus() * availableSet;

        return calculateRemaining(promotionPurchase, promotionBonus, totalQuantity,
                availablePromotionStock, set, availableSet);
    }

    private static PromotionResult calculateRemaining(int promotionPurchase, int promotionBonus, int totalQuantity,
                                                      int availablePromotionStock, int set, int availableSet) {
        int remainingQuantity = totalQuantity - (promotionBonus + promotionPurchase);
        int remainingPromotionStock = availablePromotionStock - (availableSet * set);

        int normalPurchaseFromPromo = Math.min(remainingQuantity, remainingPromotionStock);
        int normalPurchaseFromNormal = remainingQuantity - normalPurchaseFromPromo;

        return new PromotionResult(promotionPurchase, promotionBonus, normalPurchaseFromPromo, normalPurchaseFromNormal);
    }

    public int getPartialPromo() {
        return normalPurchaseFromPromo + normalPurchaseFromNormal;
    }

    public int getTotal() {
        return promotionPurchase + promotionBonus + normalPurchaseFromPromo + normalPurchaseFromNormal;
    }

    public int getPromotionPurchase() {
        return promotionPurchase;
    }

    public int getNormalPurchaseFromPromo() {
        return normalPurchaseFromPromo;
    }

    public int getNormalPurchaseFromNormal() {
        return normalPurchaseFromNormal;
    }

    public int getPromotionBonus() {
        return promotionBonus;
    }
}
