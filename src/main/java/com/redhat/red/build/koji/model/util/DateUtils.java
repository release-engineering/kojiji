package com.redhat.red.build.koji.model.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jdcasey on 5/23/16.
 */
public final class DateUtils
{

    private DateUtils(){}

    public static Date toUTC( Date completionTime )
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime( completionTime );
        cal.setTimeZone( TimeZone.getTimeZone( "UTC" ) );

        return cal.getTime();
    }

}
