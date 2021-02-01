import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Utils {
    static long getNoOfDays(Date date1, Date date2)
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        var dt1 = Instant.ofEpochMilli(date1.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        var dt2 = Instant.ofEpochMilli(date2.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        long diffDays = ChronoUnit.DAYS.between(dt1, dt2) + 1;

        return diffDays;
    }

    static LocalDate getLocalDate(Date dt)
    {
        return Instant.ofEpochMilli(dt.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
