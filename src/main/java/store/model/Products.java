package store.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Products {
    private final List<Product> products;

    public Products(final List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public Optional<Product> findProduct(String name) {
        return products.stream().filter(product -> product.matchesName(name))
                .findFirst();
    }

    public int getTotalQuantity(String name) {
        return products.stream().filter(product -> product.matchesName(name))
                .mapToInt(Product::getQuantity)
                .sum();
    }

    public Product findNonPromotionalProduct(String name) {
        return products.stream()
                .filter(product -> product.getPromotion() == null && product.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Product findPromotionProduct(String name) {
        return products.stream()
                .filter(product -> product.getPromotion() != null && product.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void updateInventory(OrderItem orderItem) {
        if (orderItem.getResult().getPromotionPurchase() > 0) {
            updatePromotionProduct(orderItem);
        }

        if (orderItem.getResult().getNormalPurchaseFromNormal() > 0) {
            updateNormalNormal(orderItem);
        }
    }

    private void updatePromotionProduct(OrderItem orderItem) {
        Product promotionProduct = findPromotionProduct(orderItem.getProductName());
        if (promotionProduct != null) {
            int deduction = calculatePromotionDeduction(orderItem.getResult());
            promotionProduct.setQuantity(promotionProduct.getQuantity() - deduction);
        }
    }

    private int calculatePromotionDeduction(PromotionResult result) {
        return result.getPromotionBonus() + result.getPromotionPurchase() + result.getNormalPurchaseFromPromo();
    }

    private void updateNormalNormal(OrderItem orderItem) {
        Product normalProduct = findNonPromotionalProduct(orderItem.getProductName());
        if (normalProduct != null) {
            int deduction = orderItem.getResult().getNormalPurchaseFromNormal();
            normalProduct.setQuantity(normalProduct.getQuantity() - deduction);
        }
    }
}
