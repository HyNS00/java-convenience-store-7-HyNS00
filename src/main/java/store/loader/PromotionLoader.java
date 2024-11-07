package store.loader;

import store.ResourcePath;
import store.model.Promotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PromotionLoader {
    private static List<Promotion> cachedPromotions = null;

    public static Promotion findPromotion(String name) {
        if (name == null || name.isBlank() || name.equalsIgnoreCase("null")) {
            return null;
        }
        List<Promotion> promotions = getPromotions();

        return promotions.stream().filter(promotion -> promotion.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private static List<Promotion> getPromotions() {
        if (cachedPromotions == null) {
            cachedPromotions = loadFromMd(ResourcePath.PROMOTIONS.getPath());
        }
        return cachedPromotions;
    }

    private static List<Promotion> loadFromMd(String resourcePath) {
        List<Promotion> promotions = new ArrayList<>();

        try (InputStream inputStream = PromotionLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("파일을 찾을 수 없습니다: " + resourcePath);
            }

            List<String> lines = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .toList();

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0];
                    int buy = Integer.parseInt(parts[1]);
                    int get = Integer.parseInt(parts[2]);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate startDate = LocalDate.parse(parts[3].trim(), formatter);
                    LocalDate endDate = LocalDate.parse(parts[4].trim(), formatter);

                    promotions.add(new Promotion(name, buy, get, startDate, endDate));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 실패: " + resourcePath, e);
        }

        return promotions;
    }

}
