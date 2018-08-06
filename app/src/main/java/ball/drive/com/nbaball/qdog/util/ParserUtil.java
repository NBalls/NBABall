package ball.drive.com.nbaball.qdog.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aaron on 2018/8/6.
 */

public class ParserUtil {

    public static void printSplit(String title) {
        System.out.println();
        System.out.println("#####################" + title + "#####################");
        System.out.println();
    }

    public static String changePan(String pan) {
        float mlevel = 0f;
        if (pan.equals("平手")) {
            mlevel = 0f;
        } else if (pan.equals("让平手/半球")) {
            mlevel = 0.25f;
        } else if (pan.equals("让半球")) {
            mlevel = 0.5f;
        } else if (pan.equals("让半/一")) {
            mlevel = 0.75f;
        } else if (pan.equals("让一球")) {
            mlevel = 1.0f;
        } else if (pan.equals("让一球/球半")) {
            mlevel = 1.25f;
        } else if (pan.equals("让球半")) {
            mlevel = 1.5f;
        } else if (pan.equals("让球半/两球")) {
            mlevel = 1.75f;
        } else if (pan.equals("让两球")) {
            mlevel = 2.0f;
        } else if (pan.equals("让两球/两半")) {
            mlevel = 2.25f;
        } else if (pan.equals("让两半")) {
            mlevel = 2.5f;
        } else if (pan.equals("让两半/三球")) {
            mlevel = 2.75f;
        } else if (pan.equals("让三球")) {
            mlevel = 3.0f;
        } else if (pan.equals("受让平手/半球")) {
            mlevel = -0.25f;
        } else if (pan.equals("受让半球")) {
            mlevel = -0.5f;
        } else if (pan.equals("受让半/一")) {
            mlevel = -0.75f;
        } else if (pan.equals("受让一球")) {
            mlevel = -1.0f;
        } else if (pan.equals("受让一球/球半")) {
            mlevel = -1.25f;
        } else if (pan.equals("受让球半")) {
            mlevel = -1.5f;
        } else if (pan.equals("受让球半/两球")) {
            mlevel = -1.75f;
        } else if (pan.equals("受让两球")) {
            mlevel = -2.0f;
        } else if (pan.equals("受让两球/两半")) {
            mlevel = -2.25f;
        } else if (pan.equals("受让两半")) {
            mlevel = -2.5f;
        } else if (pan.equals("受让两半/三球")) {
            mlevel = -2.75f;
        } else if (pan.equals("受让三球")) {
            mlevel = -3.0f;
        }

        return String.valueOf(mlevel);
    }

    public static boolean isNearDate(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
            Date date1 = sdf.parse(strDate);
            Date date2 = Calendar.getInstance().getTime();
            if (date2.getTime() - date1.getTime() <= 100 * 24 * 60 * 60 * 1000) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
