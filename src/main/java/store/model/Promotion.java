package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import store.enums.NumericValue;
import store.utils.Converter;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int buy;
    private final int bonus;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(final String name, final int buy, final int bonus, final LocalDate startDate, final LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.bonus = bonus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion createPromotion(String line) {
        String[] parts = line.split(",");

        return new Promotion(
                parts[0],
                Converter.toInt(parts[1]),
                Converter.toInt(parts[2]),
                Converter.toDate(parts[3]),
                Converter.toDate(parts[4])
        );
    }

    public boolean matchesName(String name) {
        if (isInvalidName(name)) {
            return false;
        }

        return this.name.equalsIgnoreCase(name);
    }

    public boolean isValidPromotion() {
        LocalDate today = DateTimes.now().toLocalDate();
        return today.isAfter(startDate) && today.isBefore(endDate);
    }

    private boolean isInvalidName(String name) {
        return name == null || name.isBlank() || name.equalsIgnoreCase("null");
    }

    public static boolean isValidFormat(String line) {
        return line.split(",").length == NumericValue.PROMOTION_INFO_LENGTH.getValue();
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getBonus() {
        return bonus;
    }
}
