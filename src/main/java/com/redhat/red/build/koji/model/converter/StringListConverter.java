package com.redhat.red.build.koji.model.converter;

import org.apache.commons.lang.StringUtils;
import org.commonjava.rwx.core.Converter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ruhan on 8/11/17.
 */
public class StringListConverter
                implements Converter<List<String>>
{

    @Override
    public List<String> parse( Object object )
    {
        if ( object == null )
        {
            return null;
        }
        return Arrays.asList( String.valueOf( object ).split( "\\s+" ) );
    }

    @Override
    public Object render( List<String> value )
    {
        if ( value == null )
        {
            return null;
        }
        return StringUtils.join( value, " " );
    }
}
