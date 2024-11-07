package store.loader;

import store.enums.ResourcePath;
import store.model.Product;
import store.model.Products;
import store.model.Promotions;

import java.util.List;

public class ProductLoader extends Loader<Products> {
    private final Promotions promotions;

    public ProductLoader(Promotions promotions) {
        this.promotions = promotions;
    }

    @Override
    public Products load() {
        List<String> lines = loadFromMd(ResourcePath.PRODUCTS.getPath());
        return parseProducts(lines);
    }

    private Products parseProducts(List<String> lines) {
        List<Product> products = lines.stream().skip(1).filter(Product::isValidFormat)
                .map(line -> Product.createProduct(line, promotions))
                .toList();
        return new Products(products);
    }
}
