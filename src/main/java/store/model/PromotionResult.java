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
        if (promotionProduct == null || !promotionProduct.getPromotion().isValidPromotion()) {
            return new PromotionResult(0, 0, 0, totalQuantity);
        }

        int availablePromotionStock = promotionProduct.getQuantity();

        if (availablePromotionStock == 0) {
            return new PromotionResult(0, 0, 0, totalQuantity);
        }

        Promotion promotion = promotionProduct.getPromotion();
        int buyUnit = promotion.getBuy();
        int getUnit = promotion.getGet();
        int set = buyUnit + getUnit;

        int availableSet = Math.min(availablePromotionStock / set, totalQuantity / set);

        int promotionPurchase = buyUnit * availableSet;
        int promotionBonus = getUnit * availableSet;

        int remainingQuantity = totalQuantity - (promotionBonus + promotionPurchase);
        int remainingPromotionStock = availablePromotionStock - (availableSet * set);

        int normalPurchaseFromPromo = Math.min(remainingQuantity, remainingPromotionStock);
        int normalPurchaseFromNormal = remainingQuantity - normalPurchaseFromPromo;
        return new PromotionResult(promotionPurchase, promotionBonus, normalPurchaseFromPromo, normalPurchaseFromNormal);
    }

    public int getPartialPromo(){
        return normalPurchaseFromPromo + normalPurchaseFromNormal;
    }

    public int getTotal(){
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

    public int getPromotionBonus(){
        return promotionBonus;
    }
}
