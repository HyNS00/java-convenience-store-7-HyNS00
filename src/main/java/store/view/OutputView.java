package store.view;

import store.enums.DiscountType;
import store.enums.DisplayMessage;
import store.enums.OutputMessage;
import store.model.*;

import java.util.List;
import java.util.Optional;

public class OutputView {

    public void welcomeMessage(Products products) {
        System.out.println(DisplayMessage.WELCOME.getMessage());
        System.out.println(DisplayMessage.CURRENT_STOCK.getMessage());
        products.getProducts().forEach(this::printProduct);
    }

    private void printProduct(Product product) {
        System.out.println(formatProduct(product));
    }

    private String formatProduct(Product product) {
        return String.format(OutputMessage.PRODUCT_FORMAT.getMessage(),
                product.getName(),
                product.getPrice(),
                formatQuantity(product.getQuantity()),
                formatPromotion(product.getPromotion())
        );
    }

    private String formatQuantity(int quantity) {
        if (quantity == 0) {
            return OutputMessage.OUT_OF_STOCK.getMessage();
        }
        return String.format(OutputMessage.PRODUCT_QUANTITY.getMessage(), quantity);
    }

    private String formatPromotion(Promotion promotion) {
        if (promotion == null) {
            return OutputMessage.EMPTY.getMessage();
        }
        return OutputMessage.WHITE_SPACE.getMessage() + promotion.getName();
    }

    public void printReceipt(Receipt receipt, List<OrderItem> orderItems, Products products) {
        generateStoreHeader();
        printPurchaseItems(orderItems, products);
        printBonusItem(orderItems);
        System.out.println(DisplayMessage.SEPARATOR_LINE.getMessage());
        printTotalAmount(receipt, orderItems);

    }

    private void generateStoreHeader() {
        System.out.println(DisplayMessage.STORE_HEADER.getMessage());
        System.out.println(DisplayMessage.ITEM_HEADER.getMessage());

    }

    private void printPurchaseItems(List<OrderItem> orderItems, Products products) {
        for (OrderItem item : orderItems) {
            int purchaseQuantity = item.getTotal();
            Product product = Optional.ofNullable(products.findPromotionProduct(item.getProductName()))
                    .orElseGet(() -> products.findNonPromotionalProduct(item.getProductName()));
            long totalAmount = (long) product.getPrice() * purchaseQuantity;
            if (purchaseQuantity > 0) {
                System.out.printf(OutputMessage.PURCHASE_ORDER_ITEM_FORMAT.getMessage(),
                        item.getProductName(),
                        purchaseQuantity,
                        totalAmount);
            }
        }
    }

    private void printBonusItem(List<OrderItem> orderItems) {
        boolean hasBonusItem = orderItems.stream()
                .anyMatch(item -> item.getResult().getPromotionBonus() > 0);
        if (hasBonusItem) {
            System.out.printf(DisplayMessage.BONUS_HEADER.getMessage());
            orderItems.stream()
                    .filter(item -> item.getResult().getPromotionBonus() > 0)
                    .forEach(item -> System.out.printf(OutputMessage.BONUS_ITEM_FORMAT.getMessage(),
                            item.getProductName(),
                            item.getResult().getPromotionBonus()));
        }

    }

    private void printTotalAmount(Receipt receipt, List<OrderItem> orderItems) {
        int totalItems = orderItems.stream()
                .mapToInt(OrderItem::getTotal)
                .sum();

        System.out.printf(OutputMessage.TOTAL_AMOUNT_FORMAT.getMessage(),
                totalItems,
                receipt.getTotalAmount());

        printDiscounts(receipt);

        System.out.printf(OutputMessage.FINAL_AMOUNT_FORMAT.getMessage(),
                receipt.getFinalAmount());
    }

    private void printDiscounts(Receipt receipt) {
        printDiscount(DiscountType.BONUS.getDisplayName(), receipt.getBonusDiscount());
        printDiscount(DiscountType.MEMBERSHIP.getDisplayName(), receipt.getMembershipDiscount());
    }

    private void printDiscount(String discountName, long amount) {
        if (amount > 0) {
            System.out.printf(OutputMessage.DISCOUNT_FORMAT.getMessage(), discountName, -amount);
            return;
        }
        System.out.printf(OutputMessage.ZERO_DISCOUNT_FORMAT.getMessage(),
                discountName, OutputMessage.DISCOUNT_ZERO.getMessage());
    }

    public void printErrorMessage(IllegalArgumentException exception) {
        System.out.println(OutputMessage.ERROR_PREFIX.getMessage() + exception.getMessage());
    }

}
