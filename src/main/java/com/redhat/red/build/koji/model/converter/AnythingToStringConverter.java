package com.redhat.red.build.koji.model.converter;

import org.commonjava.rwx.core.Converter;

/**
 * Created by ruhan on 8/11/17.
 */
public class AnythingToStringConverter
                implements Converter<String>
{
    @Override
    public String parse( Object object )
    {
        if ( object == null )
        {
            return null;
        }
        return String.valueOf( object );
    }

    @Override
    public Object render( String value )
    {
        if ( value == null )
        {
            return null;
        }
        return String.valueOf( value );
    }
}
