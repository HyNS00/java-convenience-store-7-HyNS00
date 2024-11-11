package store.loader;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Product;
import store.model.Products;
import store.model.Promotion;
import store.model.Promotions;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


class ProductLoaderTest {
    private Promotions promotions;
    private Products testProducts;
    private TestLoader productTestLoader;
    private TestLoader promotionTestLoader;

    @BeforeEach
    void setup() {
        promotions = loadPromotions();
        testProducts = loadProducts(promotions);

    }

    private Promotions loadPromotions() {
        promotionTestLoader = new TestLoader("promotions2.md");
        List<String> promotionLines = promotionTestLoader.load();
        List<Promotion> promotionList = promotionLines.stream()
                .skip(1)
                .filter(Promotion::isValidFormat)
                .map(Promotion::createPromotion)
                .toList();
        return new Promotions(promotionList);
    }

    private Products loadProducts(Promotions promotions) {
        productTestLoader = new TestLoader("products2.md");
        List<String> productLines = productTestLoader.load();
        List<Product> productList = productLines.stream()
                .skip(1)
                .filter(Product::isValidFormat)
                .map(line -> Product.createProduct(line, promotions))
                .toList();
        return new Products(productList);
    }

    @DisplayName("올바르게 파일 입출력을 통해 파일을 성공적으로 불러온 경우")
    @Test
    void readFile_success() {
        assertThat(testProducts).isNotNull();
        assertThat(testProducts.getProducts()).hasSize(2);
    }

    @Test
    void readFile_success_exists_promotion() {

        Product product = testProducts.getProducts().get(0);
        assertThat(product).extracting("name", "price", "quantity")
                .containsExactly("오렌지주스", 1800, 9);

        assertThat(product.getPromotion()).extracting("name", "buy", "get", "startDate", "endDate")
                .containsExactly("테스트-프로모션", 1, 1,
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 12, 31));
    }

    @Test
    void readFile_success_nonExists_promotion() {
        Product product = testProducts.getProducts().get(1);

        assertThat(product).extracting("name", "price", "quantity", "promotion")
                .containsExactly("콜라", 1000, 10, null);
    }
}