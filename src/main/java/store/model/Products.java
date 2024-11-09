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

    public Optional<Product> findProduct(String name){
        return products.stream().filter(product -> product.matchesName(name))
                .findFirst();
    }

    public int getTotalQuantity(String name){
        return products.stream().filter(product -> product.matchesName(name))
                .mapToInt(Product::getQuantity)
                .sum();
    }

    public Product findNonPromotionalProduct(String name){
        return findProduct(name).filter(product -> product.getPromotion() == null).orElse(null);

    }

    public Product findPromotionProduct(String name){
        return findProduct(name).filter(product -> product.getPromotion() != null).orElse(null);
    }
}
