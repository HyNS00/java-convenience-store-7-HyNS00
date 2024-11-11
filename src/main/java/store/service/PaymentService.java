package store.service;

import store.enums.NumericValue;
import store.model.*;

import java.util.List;
import java.util.Optional;

public class PaymentService {
    private final Products products;

    public PaymentService(Products products) {
        this.products = products;
    }

    public Receipt createReceipt(List<OrderItem> orderItems, boolean hasMembership) {
//        long totalAmount = sum(orderItems);
//        long membershipDiscount = 0;
//        if (hasMembership) {
//            membershipDiscount = calculateMemberShipDiscount(orderItems);
//        }
//        long bonusDiscount = calculateBonusDiscount(orderItems);
//
//        return new Receipt(totalAmount, membershipDiscount, bonusDiscount);
        long totalAmount = sum(orderItems);
        // 보너스 할인을 먼저 계산
        long bonusDiscount = calculateBonusDiscount(orderItems);
        long membershipDiscount = 0;
        if (hasMembership) {
            // 실제 구매 금액에 대해서만 멤버십 할인 계산
            membershipDiscount = calculateMemberShipDiscount(orderItems);
        }
        return new Receipt(totalAmount, membershipDiscount, bonusDiscount);
    }

    private long sum(List<OrderItem> orderItems) {
        return calculateTotalSum(orderItems);
    }

    private long calculateMemberShipDiscount(List<OrderItem> orderItems) {
//        long totalSum = calculateTotalSum(orderItems);
//        long discountAmount = totalSum * NumericValue.MEMBERSHIP_DISCOUNT_RATE.getValue() / 100;
//        return Math.min(roundDownToThousand(discountAmount), NumericValue.MAX_MEMBERSHIP_DISCOUNT.getValue());
        long actualPurchaseAmount = orderItems.stream()
                .mapToLong(this::calculateActualPurchaseAmount)
                .sum();

        long discountAmount = actualPurchaseAmount *
                NumericValue.MEMBERSHIP_DISCOUNT_RATE.getValue() / 100;
        long roundedAmount = roundDownToThousand(discountAmount);

        return Math.min(roundedAmount, NumericValue.MAX_MEMBERSHIP_DISCOUNT.getValue());
    }

    private long calculateActualPurchaseAmount(OrderItem orderItem) {
        Product product = Optional.ofNullable(products.findPromotionProduct(orderItem.getProductName()))
                .orElseGet(() -> products.findNonPromotionalProduct(orderItem.getProductName()));

        long productPrice = product.getPrice();
        PromotionResult result = orderItem.getResult();

        // 실제 구매 수량만 계산 (보너스 제외)
        int actualPurchaseQuantity = result.getPromotionPurchase() +
                result.getNormalPurchaseFromPromo() +
                result.getNormalPurchaseFromNormal();

        return productPrice * actualPurchaseQuantity;
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
