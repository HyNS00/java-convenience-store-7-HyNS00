package store.service;

import store.model.Product;
import store.model.Products;
import store.model.Promotion;

public class PromotionService {
    private final Products products;

    public PromotionService(Products products) {
        this.products = products;
    }

    public boolean requiresAdditionalProduct(String productName, int quantity){
        Product promotionProduct = products.findPromotionProduct(productName);
        if(promotionProduct == null || promotionProduct.getQuantity() < quantity){
            return false;
        }
        Promotion promotion = promotionProduct.getPromotion();
        if(!promotion.isValidPromotion()){
            return false;
        }

        int buy = promotion.getBuy();
        return (quantity + 1) % (buy+1) == 0;
    }

    public boolean canApplyPartialPromotion(String productName, int quantity){
        Product promotionProduct = products.findPromotionProduct(productName);
        if(promotionProduct == null || promotionProduct.getQuantity() == 0){
            return false;
        }
        Promotion promotion = promotionProduct.getPromotion();
        return promotion.isValidPromotion() && quantity > promotionProduct.getQuantity();
    }
}
