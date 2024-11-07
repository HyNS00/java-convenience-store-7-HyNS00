package store.model;

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

    public static Product createProduct(String line) {
        String[] parts = line.split(",");

        return new Product(
                parts[0],
                Converter.toInt(parts[1]),
                Converter.toInt(parts[2]),
                findPromotion(parts[3])
        );
    }
    public static boolean isValidFormat(String line) {
        return line.split(",").length == 4;
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


}
