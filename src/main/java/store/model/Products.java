package store.model;

import java.util.Collections;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products(final List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product findProduct(String name){
        return products.stream().filter(product -> product.matchesName(name))
                .findFirst().orElse(null);
    }

    public int getTotalQuantity(String name){
        return products.stream().filter(product -> product.matchesName(name))
                .mapToInt(Product::getQuantity)
                .sum();
    }
}
