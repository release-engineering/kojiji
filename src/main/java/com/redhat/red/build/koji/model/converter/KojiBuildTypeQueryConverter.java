/**
 * Copyright (C) 2015 Red Hat, Inc. (jcasey@redhat.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.red.build.koji.model.converter;

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildTypeQuery;
import org.commonjava.rwx.core.Converter;

import java.util.HashMap;
import java.util.Map;

import static com.redhat.red.build.koji.model.xmlrpc.KojiQuery.__STARSTAR;

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

        if ( map.containsKey( __STARSTAR ) )
        {
            Object __starstar = map.get( __STARSTAR );
            if ( __starstar instanceof Boolean )
            {
                query.setEnabled( (Boolean) __starstar );
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

        Map<String, Object> map = new HashMap<>();

        boolean __starstar = value.getEnabled();
        map.put( __STARSTAR, __starstar );

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
