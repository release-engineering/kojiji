package com.redhat.red.build.koji.model.converter;

import com.redhat.red.build.koji.model.xmlrpc.KojiUserType;
import org.commonjava.rwx.core.Converter;
import org.commonjava.rwx.vocab.ValueType;

/**
 * Created by ruhan on 8/11/17.
 */
public class KojiUserTypeConverter
                implements Converter<KojiUserType>
{
    @Override
    public KojiUserType parse( Object object )
    {
        return KojiUserType.fromInteger( (Integer) object );
    }

    @Override
    public Object render( KojiUserType value )
    {
        if ( value == null )
        {
            return null;
        }
        Integer intUserType = value.getValue();
        if ( intUserType == null )
        {
            return null;
        }
        else
        {
            return intUserType;
        }
    }
}
