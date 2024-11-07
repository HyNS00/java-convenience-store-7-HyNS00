package store.model;

import store.utils.Converter;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(final String name, final int buy, final int get, final LocalDate startDate, final LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
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

    private boolean isInvalidName(String name) {
        return name == null || name.isBlank() || name.equalsIgnoreCase("null");
    }

    public static boolean isValidFormat(String line) {
        return line.split(",").length == 5;
    }


    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
