import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Set;

/**
 * Created by rocky on 18/3/11.
 */
public class DateTest {

    @Test
    public void timeZoneTest(){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        String s = formatter.withZone(DateTimeZone.forID("EST")).print(DateTime.now());
        System.out.println(s);
    }

    @Test
    public void timeZoneIdTest(){
        Set<String> ids = DateTimeZone.getAvailableIDs();
        System.out.println(ids);
    }
}
