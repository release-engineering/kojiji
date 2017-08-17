package com.redhat.red.build.koji.model.converter;

import com.redhat.red.build.koji.model.xmlrpc.KojiChecksumType;
import org.commonjava.rwx.core.Converter;

/**
 * Created by ruhan on 8/11/17.
 */
public class KojiChecksumTypeConverter
                implements Converter<KojiChecksumType>
{
    @Override
    public KojiChecksumType parse( Object object )
    {
        return KojiChecksumType.fromInteger( (Integer) object );
    }

    @Override
    public Object render( KojiChecksumType value )
    {
        if ( value == null )
        {
            return null;
        }

        Integer intChecksumType = value.getValue();
        if ( intChecksumType == null )
        {
            return null;
        }
        else
        {
            return intChecksumType;
        }
    }
}
