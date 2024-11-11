package store.model;

import store.enums.NumericValue;
import store.utils.Converter;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    private Product(final String name, final int price, int quantity, final Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product createProduct(String line, Promotions promotions) {
        String[] parts = line.split(",");

        return new Product(
                parts[0],
                Converter.toInt(parts[1]),
                Converter.toInt(parts[2]),
                promotions.findPromotion(parts[3])
        );
    }

    public static boolean isValidFormat(String line) {
        return line.split(",").length == NumericValue.PRODUCT_INFO_LENGTH.getValue();
    }

    public boolean matchesName(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
