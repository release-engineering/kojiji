/*
 * Copyright (C) 2015 Red Hat, Inc.
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
package com.redhat.red.build.koji.model.xmlrpc;

import com.redhat.red.build.koji.model.converter.KojiIdOrNameConverter;
import org.commonjava.rwx.anno.Converter;

/**
 * Created by jdcasey on 1/15/16.
 */
@Converter( KojiIdOrNameConverter.class )
public class KojiIdOrName
{
    private String name;

    private Integer id;

    public static KojiIdOrName getFor( Object value )
    {
        if ( value instanceof Integer )
        {
            return new KojiIdOrName( (Integer) value );
        }

        KojiIdOrName result;
        try
        {
            int i = Integer.parseInt( value.toString() );
            result = new KojiIdOrName( i );
        }
        catch ( NumberFormatException e )
        {
            result = new KojiIdOrName( value.toString() );
        }

        return result;
    }

    public KojiIdOrName()
    {
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public KojiIdOrName( String name )
    {
        this.name = name;
    }

    public KojiIdOrName( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public Integer getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "KojiIdOrName{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
