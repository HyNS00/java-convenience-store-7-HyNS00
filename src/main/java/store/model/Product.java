package store.model;

import static store.loader.PromotionLoader.findPromotion;

public class Product {
    private final String name;
    private final int price;
    private  int quantity;
    private final Promotion promotion;

    private Product(final String name, final int price, int quantity, final Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product createProduct(String line){
        String[] parts = line.split(",");
        String name = parts[0];
        int price = Integer.parseInt(parts[1]);
        int quantity = Integer.parseInt(parts[2]);
        Promotion promotion = findPromotion(parts[3]);

        return new Product(name,price,quantity,promotion);
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
