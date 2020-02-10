package com.wasion.meterreading.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmm");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

    public static Date parseDataYYMMDDHHMM(String dateStr) throws ParseException {
        return sdf1.parse(dateStr);
    }

    public static Date parseDateYyyyMmDdHhMmSs(String dateStr) throws ParseException {
        return sdf2.parse(dateStr);
    }

    public static Date parseDateyyyyMMddHHmmssSSS(String dateStr) throws ParseException {
        return sdf4.parse(dateStr);
    }

    public static String formatDateYYYYMMTDDHHMMSS(Date date) {
        return sdf3.format(date);
    }

    public static Date parseDateyyyyMMddTHHmmssZ(String dateStr) throws ParseException {
        return sdf5.parse(dateStr);
    }

    public static String formatDateYYYYMMTDDHHMMSSZ(Date date) {
        return sdf5.format(date);
    }

}
