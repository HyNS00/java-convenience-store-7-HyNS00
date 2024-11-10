package store.service;

import store.model.OrderItem;
import store.model.Product;
import store.model.Products;
import store.model.Receipt;

import java.util.List;

public class PaymentService {
    private List<OrderItem> orderItems;
    private Products products;

    public Receipt createReceipt(boolean hasMembership) {
        long totalAmount = sum();
        long membershipDiscount = 0;
        if (hasMembership) {
            membershipDiscount = calculateMemberShipDiscount();
        }
        long bonusDiscount = calculateBonusDiscount();

        return new Receipt(totalAmount, membershipDiscount, bonusDiscount);
    }

    private long sum() {
        return calculateTotalSum();
    }

    private long calculateMemberShipDiscount() {
        return Math.min((long) (calculateTotalSum() * 0.3), 8000);
    }

    private long calculateTotalSum() {
        return orderItems.stream()
                .mapToLong(this::calculateItemTotal)
                .sum();
    }

    public long calculateBonusDiscount() {
        return orderItems.stream()
                .mapToLong(this::calculateItemBonusDiscount).sum();
    }

    private long calculateItemTotal(OrderItem orderItem) {
        Product product = products.findNonPromotionalProduct(orderItem.getProductName());
        long productPrice = product.getPrice();
        int quantity = orderItem.getTotal();

        return productPrice * quantity;
    }

    private long calculateItemBonusDiscount(OrderItem orderItem) {
        Product promotionProduct = products.findPromotionProduct(orderItem.getProductName());
        long productPrice = promotionProduct.getPrice();
        int promotionBonus = orderItem.getResult().getPromotionBonus();

        return (long) (productPrice * promotionBonus);
    }
}
