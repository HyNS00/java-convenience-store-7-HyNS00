package store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converter {
    private Converter(){
    }

    public static int toInt(String part){
        return Integer.parseInt(part.trim());
    }

    public static LocalDate toDate(String part){
        return LocalDate.parse(part.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}

