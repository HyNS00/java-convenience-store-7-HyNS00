package store.service;

import store.enums.NumericValue;
import store.model.OrderItem;
import store.model.Product;
import store.model.Products;
import store.model.Receipt;

import java.util.List;
import java.util.Optional;

public class PaymentService {
    private final Products products;

    public PaymentService(Products products) {
        this.products = products;
    }

    public Receipt createReceipt(List<OrderItem> orderItems, boolean hasMembership) {
        long totalAmount = sum(orderItems);
        long membershipDiscount = 0;
        if (hasMembership) {
            membershipDiscount = calculateMemberShipDiscount(orderItems);
        }
        long bonusDiscount = calculateBonusDiscount(orderItems);

        return new Receipt(totalAmount, membershipDiscount, bonusDiscount);
    }

    private long sum(List<OrderItem> orderItems) {
        return calculateTotalSum(orderItems);
    }

    private long calculateMemberShipDiscount(List<OrderItem> orderItems) {
        long totalSum = calculateTotalSum(orderItems);
        long discountAmount = totalSum * NumericValue.MEMBERSHIP_DISCOUNT_RATE.getValue() / 100;
        long roundedAmount = roundDownToThousand(discountAmount);
        if (roundedAmount < NumericValue.MIN_MEMBERSHIP_DISCOUNT.getValue()) {
            return 0;
        }
        if (roundedAmount > NumericValue.MAX_MEMBERSHIP_DISCOUNT.getValue()) {
            return NumericValue.MAX_MEMBERSHIP_DISCOUNT.getValue();
        }
        return Math.min(roundDownToThousand(discountAmount), NumericValue.MAX_MEMBERSHIP_DISCOUNT.getValue());
    }

    private long roundDownToThousand(long amount) {
        int unit = NumericValue.DISCOUNT_UNIT.getValue();
        return (amount / unit) * unit;
    }

    private long calculateTotalSum(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToLong(this::calculateItemTotal)
                .sum();
    }

    public long calculateBonusDiscount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToLong(this::calculateItemBonusDiscount).sum();
    }

    private long calculateItemTotal(OrderItem orderItem) {
        Product product = Optional.ofNullable(products.findPromotionProduct(orderItem.getProductName()))
                .orElseGet(() -> products.findNonPromotionalProduct(orderItem.getProductName()));
        long productPrice = product.getPrice();
        int quantity = orderItem.getTotal();

        return productPrice * quantity;
    }

    private long calculateItemBonusDiscount(OrderItem orderItem) {
        Product product = Optional.ofNullable(products.findPromotionProduct(orderItem.getProductName()))
                .orElseGet(() -> products.findNonPromotionalProduct(orderItem.getProductName()));
        long productPrice = product.getPrice();
        int promotionBonus = orderItem.getResult().getPromotionBonus();

        return (long) (productPrice * promotionBonus);
    }
}
