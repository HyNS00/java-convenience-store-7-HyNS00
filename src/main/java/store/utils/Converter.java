package store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Converter {
    private Converter(){
    }

    public static int toInt(String part){
        return Integer.parseInt(part.trim());
    }

    public static LocalDate toDate(String part){
        return LocalDate.parse(part.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private static String removeBrackets(String input){
        return input.replace("[","").replace("]","").trim();
    }

    private static List<String> toProductInfo(String input){
        return Arrays.asList(input.split("-"));
    }

    public static List<List<String>> toOrderList(String input){
        return Arrays.stream(input.split(","))
                .map(Converter::removeBrackets)
                .map(Converter::toProductInfo)
                .toList();
    }
}

