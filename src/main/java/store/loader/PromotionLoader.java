package store.loader;

import store.ResourcePath;
import store.model.Promotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class PromotionLoader {
    private static List<Promotion> cachedPromotions = null;

    public static Promotion findPromotion(String name) {
        if (isInvalidName(name)) {
            return null;
        }

        return getPromotions().stream().filter(promotion -> promotion.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private static boolean isInvalidName(String name) {
        return name == null || name.isBlank() || name.equalsIgnoreCase("null");
    }

    private static List<Promotion> getPromotions() {
        if (cachedPromotions == null) {
            cachedPromotions = loadFromMd(ResourcePath.PROMOTIONS.getPath());
        }
        return cachedPromotions;
    }

    private static List<Promotion> loadFromMd(String resourcePath) {
        List<String> lines = readLines(resourcePath);
        if (lines.isEmpty()) {
            return Collections.emptyList();
        }

        return parsePromotions(lines);

    }

    private static List<Promotion> parsePromotions(List<String> lines) {
        return lines.stream().skip(1).filter(Promotion::isValidFormat)
                .map(Promotion::createPromotion).toList();
    }

    private static List<String> readLines(String resourcePath) {
        try (BufferedReader reader = createBufferedReader(resourcePath)) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 실패");
        }
    }

    private static BufferedReader createBufferedReader(String resourcePath) {
        return new BufferedReader(new InputStreamReader(getInputStream(resourcePath),
                StandardCharsets.UTF_8));
    }

    private static InputStream getInputStream(String resourcePath) {
        InputStream inputStream = PromotionLoader.class.getClassLoader()
                .getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다: " + resourcePath);
        }
        return inputStream;
    }


}
