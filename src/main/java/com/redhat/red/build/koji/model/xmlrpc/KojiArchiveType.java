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

import com.redhat.red.build.koji.model.converter.StringListConverter;
import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.List;

/**
 * Created by jdcasey on 9/16/16.
 */
@StructPart
public class KojiArchiveType
{
    @DataKey( "description" )
    private String description;

    @DataKey( "extensions" )
    @Converter( StringListConverter.class )
    private List<String> extensions;

    @DataKey( "id" )
    private int id;

    @DataKey( "name" )
    private String name;

    public KojiArchiveType( String description, List<String> extensions, int id, String name )
    {
        this.description = description;
        this.extensions = extensions;
        this.id = id;
        this.name = name;
    }

    public KojiArchiveType()
    {
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public List<String> getExtensions()
    {
        return extensions;
    }

    public void setExtensions( List<String> extensions )
    {
        this.extensions = extensions;
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
        if ( !( o instanceof KojiArchiveType ) )
        {
            return false;
        }

        KojiArchiveType that = (KojiArchiveType) o;

        if ( getId() != that.getId() )
        {
            return false;
        }
        if ( getDescription() != null ?
                !getDescription().equals( that.getDescription() ) :
                that.getDescription() != null )
        {
            return false;
        }
        if ( getExtensions() != null ? !getExtensions().equals( that.getExtensions() ) : that.getExtensions() != null )
        {
            return false;
        }
        return getName() != null ? getName().equals( that.getName() ) : that.getName() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getDescription() != null ? getDescription().hashCode() : 0;
        result = 31 * result + ( getExtensions() != null ? getExtensions().hashCode() : 0 );
        result = 31 * result + getId();
        result = 31 * result + ( getName() != null ? getName().hashCode() : 0 );
        return result;
    }

    @Override
    public String toString()
    {
        return "KojiArchiveType{" +
                "description='" + description + '\'' +
                ", extensions=" + extensions +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
