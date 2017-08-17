package com.redhat.red.build.koji.model.converter;

import org.commonjava.rwx.core.Converter;

import java.util.Date;

import static com.redhat.red.build.koji.model.util.DateUtils.toUTC;

/**
 * Created by ruhan on 8/11/17.
 */
public class TimestampIntConverter
                implements Converter<Date>
{
    @Override
    public Date parse( Object object )
    {
        if ( object == null )
        {
            return null;
        }
        long time = Long.parseLong( String.valueOf( object ) ) * 1000;
        return new Date( time );
    }

    @Override
    public Object render( Date value )
    {
        if ( value == null )
        {
            return null;
        }
        return toUTC( value ).getTime() / 1000;
    }
}
