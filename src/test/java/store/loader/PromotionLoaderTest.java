package store.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.model.Promotion;
import store.model.Promotions;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionLoaderTest {
    private PromotionLoader promotionLoader;
    private TestLoader testLoader;
    @BeforeEach
    void setup() {
        promotionLoader = new PromotionLoader();
        testLoader = new TestLoader("promotions2.md");
    }

    @DisplayName("프로모션 파일에서 성공적으로 프로모션을 불러온 경우")
    @Test
    void readPromotion_success() {
        List<String> testLines = testLoader.load();
        List<Promotion> promotions = testLines.stream()
                .skip(1)
                .filter(Promotion::isValidFormat)
                .map(Promotion::createPromotion)
                .toList();
        Promotions testPromotions = new Promotions(promotions);
        Promotion promotion = testPromotions.findPromotion("테스트-프로모션");

        assertThat(promotions).isNotNull();
        assertThat(promotion).extracting("name", "buy", "bonus", "startDate", "endDate")
                .containsExactly("테스트-프로모션", 1, 1, LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 12, 31));
    }

    @DisplayName("없는 프로며선 이름을 불러왔을 때")
    @ParameterizedTest
    @ValueSource(strings = {"존재하지 않는 프로모션", "", " ", "null"})
    void readPromotion_fail(String input) {
        Promotions promotions = promotionLoader.load();
        Promotion promotion = promotions.findPromotion(input);

        assertThat(promotion).isNull();
    }


}