package store.view;

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
        return String.format("- %s %,d원 %s%s",
                product.getName(),
                product.getPrice(),
                formatQuantity(product.getQuantity()),
                formatPromotion(product.getPromotion())
        );
    }

    private String formatQuantity(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return String.format("%d개", quantity);
    }

    private String formatPromotion(Promotion promotion) {
        if (promotion == null) {
            return "";
        }
        return " " + promotion.getName();
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
                System.out.printf("%-8s      %2d     %,8d%n",
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
                    .forEach(item -> System.out.printf("%-12s      %d%n",
                            item.getProductName(),
                            item.getResult().getPromotionBonus()));
        }

    }

    private void printTotalAmount(Receipt receipt, List<OrderItem> orderItems) {
        int totalItems = orderItems.stream()
                .mapToInt(OrderItem::getTotal)
                .sum();

        System.out.printf("총구매액          %2d     %,8d%n",
                totalItems,
                receipt.getTotalAmount());

        if (receipt.getBonusDiscount() > 0) {
            System.out.printf("행사할인                 %+,8d%n",
                    -receipt.getBonusDiscount());
        }

        if (receipt.getBonusDiscount() == 0) {
            System.out.printf("행사할인                 %8s%n", "-0");
        }

        if (receipt.getMembershipDiscount() > 0) {
            System.out.printf("멤버십할인               %+,8d%n",
                    -receipt.getMembershipDiscount());
        }

        if (receipt.getMembershipDiscount() == 0) {
            System.out.printf("멤버십할인                 %8s%n", "-0");
        }
        System.out.printf("내실돈                   %,8d\n",
                receipt.getFinalAmount());
    }

    public void printErrorMessage(IllegalArgumentException exception) {
        System.out.println(OutputMessage.ERROR_PREFIX.getMessage() + exception.getMessage());
    }

}
