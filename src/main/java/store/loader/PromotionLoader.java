package store.loader;

import store.enums.ResourcePath;
import store.model.Promotion;
import store.model.Promotions;

import java.util.List;

public class PromotionLoader extends Loader<Promotions> {

    @Override
    public Promotions load() {
        List<String> lines = loadFromMd(ResourcePath.PROMOTIONS.getPath());
        return parsePromotions(lines);
    }

    private Promotions parsePromotions(List<String> lines) {
        List<Promotion> promotions = lines.stream().skip(1).filter(Promotion::isValidFormat)
                .map(Promotion::createPromotion)
                .toList();
        return new Promotions(promotions);
    }
}
