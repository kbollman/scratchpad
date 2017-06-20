package com.kevjim.common.util;

import java.util.Date;

/**
 * A Utility class for operating on Strings.
 */
public class DateUtils {

    public static final int SECONDS_IN_A_DAY = 86400;
    public static final long MILLIS_IN_DAY = SECONDS_IN_A_DAY * 1000L;
    public static final String MAX_YYYYY_MM_DD = "9999-12-31";
    /**
     * Converts Epoch date to Java Date
     *
     * @param epoch Epoch to convert
     * @return Java Date
     */
    public static Date convertEpochToDate(long epoch) {
        return new Date(epoch * 1000);
    }

    /**
     * Converts date (as millis) to Epoch
     * @param date as millis
     * @return epoch
     */
    public static int convertDateToEpoch(long date) {
        return (int) (date / 1000);
    }

    /**
     * Converts date to Epoch
     * @param date
     * @return epoch
     */
    public static int convertDateToEpoch(Date date) {
        return convertDateToEpoch(date.getTime());
    }

    /**
     * Gets the current epoch as long datatype (for db insert where we use long, but only store seconds)
     *
     * @return Long epoch in seconds
     */
    public static long currentEpoch() {
        return convertDateToEpoch((new Date()).getTime());
    }

    /**
     * Converts number of days to millis
     * @param days the number of days to convert to millis
     * @return the number of days as millis
     */
    public static long daysAsMillis(int days) {
        return  days * 24 * 60 * 60 * 1000L;
    }
}
