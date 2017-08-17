package com.redhat.red.build.koji.model.converter;

import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import org.commonjava.rwx.core.Converter;

/**
 * Created by ruhan on 8/11/17.
 */
public class KojiIdOrNameConverter
                implements Converter<KojiIdOrName>
{
    @Override
    public KojiIdOrName parse( Object object )
    {
        if ( object == null )
        {
            return null;
        }
        return KojiIdOrName.getFor( object );
    }

    @Override
    public Object render( KojiIdOrName value )
    {
        if (value == null)
        {
            return null;
        }
        Integer id = value.getId();
        if ( id != null )
        {
            return id;
        }
        else
        {
            return value.getName();
        }
    }
}
