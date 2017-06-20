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
package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.KeyRefs;
import org.commonjava.rwx.binding.anno.StructPart;

@StructPart
public class KojiBuildType
{
    @DataKey( "id" )
    private int id;

    @DataKey( "name" )
    private String name;

    @KeyRefs( { "id", "name" } )
    public KojiBuildType( int id, String name )
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }

        if ( !( o instanceof KojiBuildType ) )
        {
            return false;
        }

        KojiBuildType that = (KojiBuildType) o;

        if ( getId() != that.getId() )
        {
            return false;
        }

        return getName() != null ? getName().equals( that.getName() ) : that.getName() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getId();
        result = 31 * result + ( getName() != null ? getName().hashCode() : 0 );
        return result;
    }

    @Override
    public String toString()
    {
        return "KojiBuildType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
