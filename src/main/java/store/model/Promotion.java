package store.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
                extractNumber(parts[1]),
                extractNumber(parts[2]),
                extractDate(parts[3]),
                extractDate(parts[4])
        );
    }

    public static boolean isValidFormat(String line){
        return line.split(",").length == 5;
    }

    private static LocalDate extractDate(String dateSr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateSr.trim(), formatter);
    }

    private static int extractNumber(String value) {
        return Integer.parseInt(value);
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
