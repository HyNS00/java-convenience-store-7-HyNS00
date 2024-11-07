package store.model;

import java.util.List;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(final List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Promotion findPromotion(String name){
        return promotions.stream()
                .filter(promotion -> promotion.matchesName(name))
                .findFirst()
                .orElse(null);
    }
}
