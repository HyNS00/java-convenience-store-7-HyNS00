package store.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ConverterTest {
    @DisplayName("단일 주문 목록을 입력받았을 때, 주문 목록을 치환")
    @Test
    void toOrderListSingle_success() {
        String test1 = "[콜라-1]";

        List<List<String>> orderList = Converter.toOrderList(test1);

        assertThat(orderList).hasSize(1)
                .containsExactly(Arrays.asList("콜라", "1"));
        System.out.println(orderList);
    }

    @DisplayName("여러 주문 목록을 입력받았을 때, 주문 목록 치환")
    @Test
    void toOrderListMultiple_success(){
        String test2 = "[콜라-3],[사이다-4]";

        List<List<String>> orderList = Converter.toOrderList(test2);

        assertThat(orderList).hasSize(2);

        assertThat(orderList.get(0)).isEqualTo(Arrays.asList("콜라", "3"));
        assertThat(orderList.get(1)).isEqualTo(Arrays.asList("사이다", "4"));
    }
}