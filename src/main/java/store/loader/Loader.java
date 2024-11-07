package store.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public abstract class Loader<T> {
    public abstract T load();

    protected static List<String> loadFromMd(String resourcePath) {
        List<String> lines = readLines(resourcePath);
        if (lines.isEmpty()) {
            return Collections.emptyList();
        }
        return lines;
    }

    private static List<String> readLines(String resourcePath) {
        try (BufferedReader reader = createBufferedReader(resourcePath)) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 실패");
        }
    }

    private static BufferedReader createBufferedReader(String resourcePath) {
        return new BufferedReader(
                new InputStreamReader(getInputStream(resourcePath), StandardCharsets.UTF_8)
        );
    }

    private static InputStream getInputStream(String resourcePath) {
        InputStream inputStream = Loader.class.getClassLoader()
                .getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다");
        }
        return inputStream;
    }
}
