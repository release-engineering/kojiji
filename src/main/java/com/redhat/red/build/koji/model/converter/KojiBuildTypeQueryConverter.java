package com.redhat.red.build.koji.model.converter;

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildTypeQuery;
import org.commonjava.rwx.core.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruhan on 8/11/17.
 */
public class KojiBuildTypeQueryConverter
                implements Converter<KojiBuildTypeQuery>
{
    @Override
    public KojiBuildTypeQuery parse( Object object )
    {
        if ( object == null )
        {
            return null;
        }

        Map<?, ?> map = (Map) object;
        KojiBuildTypeQuery query = new KojiBuildTypeQuery();

        if ( map.containsKey( "name" ) )
        {
            Object name = map.get( "name" );
            if ( name instanceof String )
            {
                query.setName( (String) name );
            }
        }

        if ( map.containsKey( "id" ) )
        {
            Object id = map.get( "id" );
            if ( id instanceof Integer )
            {
                query.setId( (int) id );
            }
        }

        return query;
    }

    @Override
    public Object render( KojiBuildTypeQuery value )
    {
        if ( value == null )
        {
            return null;
        }

        Map<String, Object> map = new HashMap<>( 2 );

        String name = value.getName();
        if ( name != null )
        {
            map.put( "name", name );
        }

        Integer id = value.getId();
        if ( id != null )
        {
            map.put( "id", id );

        }
        return map;
    }
}
