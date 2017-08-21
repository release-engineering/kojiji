package com.redhat.red.build.koji.model.converter;

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildState;
import org.commonjava.rwx.core.Converter;

/**
 * Created by ruhan on 8/11/17.
 */
public class KojiBuildStateConverter
                implements Converter<KojiBuildState>
{
    @Override
    public KojiBuildState parse( Object object )
    {
        return KojiBuildState.fromInteger( (Integer) object ); // handles null already
    }

    @Override
    public Object render( KojiBuildState value )
    {
        if ( value == null )
        {
            return null;
        }
        Integer intState = value.getValue();
        if ( intState == null )
        {
            return null;
        }
        else
        {
            return intState;
        }
    }
}
