package store.loader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoaderTest {
    @DisplayName("성공적으로 파일을 불러왔을 경우")
    @Test
    void testLoadFromMd_success() {
        TestLoader testLoader = new TestLoader("test.md");

        List<String> loaded = testLoader.load();

        assertThat(loaded).isNotNull();
        assertThat(loaded.size()).isEqualTo(1);
        assertThat(loaded).containsExactly("test1,test2,test3,test4");
    }

    @DisplayName("내용이 비어있는 파일을 불러왔을 경우")
    @Test
    void testLoadFromMd_Empty() {
        TestLoader testLoader = new TestLoader("empty.md");

        List<String> loaded = testLoader.load();

        assertThat(loaded).isEmpty();
    }

    @DisplayName("잘못된 파일을 입력한 경우")
    @Test
    void testLoadFromMd_wrongName() {
        TestLoader testLoader = new TestLoader("test1.md");

        assertThatThrownBy(testLoader::load).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("없는 파일을 불러올 경우")
    @Test
    void testLoadFromMd_nonExist(){
        TestLoader testLoader = new TestLoader("없는 파일.md");

        assertThatThrownBy(testLoader::load).isInstanceOf(RuntimeException.class);
    }
}