package edu.wgu.d387_sample_code.internationalization;

import org.springframework.web.bind.annotation.CrossOrigin;
import java.time.*;
import java.time.format.DateTimeFormatter;

@CrossOrigin(origins = "http://localhost:4200")
public class TZConvert {

    /**
     * Converts the current time to Eastern Time (ET), Mountain Time (MT), and Coordinated Universal Time (UTC).
     *
     * @return A string containing the time in ET, MT, and UTC formatted as "HH:mm".
     */
    public static String getTime() {
        ZonedDateTime currentTime = ZonedDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        ZonedDateTime est = currentTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime mst = currentTime.withZoneSameInstant(ZoneId.of("America/Denver"));
        ZonedDateTime utc = currentTime.withZoneSameInstant(ZoneId.of("UTC"));

        return est.format(timeFormat) + " EST, " + mst.format(timeFormat) + " MST, " + utc.format(timeFormat) + " UTC";
    }
}
