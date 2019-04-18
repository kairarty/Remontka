package programm.methods;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SupportMethods {
    public static String getTodayDate() {
        DateTimeFormatter dateMask = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        return LocalDate.now().format(dateMask);
    }
}
