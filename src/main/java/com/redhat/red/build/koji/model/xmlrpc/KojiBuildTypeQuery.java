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

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

@StructPart
public class KojiBuildTypeQuery extends KojiQuery
{
    @DataKey( "query" )
    private KojiBuildTypeQueryQuery query;

    public KojiBuildTypeQuery()
    {
        query = new KojiBuildTypeQueryQuery();
    }

    public void setId( Integer id )
    {
        query.setId ( id );
    }

    public String getName()
    {
        return query.getName();
    }

    public void setName( String name )
    {
        query.setName( name );
    }

    public KojiBuildTypeQuery withName( String name )
    {
        query.setName( name );
        return this;
    }

    public Integer getId()
    {
        return query.getId();
    }

    public void setId( int id )
    {
        query.setId( id );
    }

    public KojiBuildTypeQuery withId( int id )
    {
        query.setId( id );
        return this;
    }

    public KojiBuildTypeQueryQuery getQuery()
    {
        return query;
    }

    public void setQuery( KojiBuildTypeQueryQuery query )
    {
        this.query = query;
    }

    @Override
    public String toString()
    {
        return "KojiBuildTypeQuery{query=" + query + "}";
    }
}
