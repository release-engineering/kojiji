package com.redhat.red.build.koji.model.converter;

import com.redhat.red.build.koji.model.xmlrpc.KojiUserStatus;
import org.commonjava.rwx.core.Converter;

/**
 * Created by ruhan on 8/11/17.
 */
public class KojiUserStatusConverter
                implements Converter<KojiUserStatus>
{
    @Override
    public KojiUserStatus parse( Object object )
    {
        if ( object == null )
        {
            return null;
        }
        return KojiUserStatus.fromInteger( (Integer) object );
    }

    @Override
    public Object render( KojiUserStatus value )
    {
        if ( value == null )
        {
            return null;
        }

        Integer intUserStatus = value.getValue();
        if ( intUserStatus == null )
        {
            return null;
        }
        else
        {
            return intUserStatus;
        }
    }
}
