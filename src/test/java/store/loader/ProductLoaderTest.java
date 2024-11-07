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

import static org.assertj.core.api.Assertions.*;


class ProductLoaderTest {
    private ProductLoader productLoader;
    private Promotions promotions;
    private PromotionLoader promotionLoader;

    @BeforeEach
    void setup() {
        promotionLoader = new PromotionLoader();
        promotions = promotionLoader.load();
        productLoader = new ProductLoader(promotions);
    }

    @DisplayName("올바르게 파일 입출력을 통해 파일을 성공적으로 불러온 경우")
    @Test
    void readFile_success() {

        Products loaded = productLoader.load();

        assertThat(loaded).isNotNull();
        assertThat(loaded.getProducts().size()).isEqualTo(2);
    }

    @Test
    void readFile_success_exists_promotion() {
        Products loaded = productLoader.load();

        Product product = loaded.getProducts().get(0);
        assertThat(product).extracting("name", "price", "quantity")
                .containsExactly("오렌지주스", 1800, 9);

        assertThat(product.getPromotion()).extracting("name", "buy", "get", "startDate", "endDate")
                .containsExactly("테스트-프로모션", 1, 1,
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 12, 31));
    }

    @Test
    void readFile_success_nonExists_promotion() {
        Products loaded = productLoader.load();

        Product product = loaded.getProducts().get(1);

        assertThat(product).extracting("name", "price", "quantity", "promotion")
                .containsExactly("콜라", 1000, 10, null);
    }
}