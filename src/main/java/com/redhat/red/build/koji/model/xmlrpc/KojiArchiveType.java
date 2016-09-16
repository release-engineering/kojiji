package com.redhat.red.build.koji.model.xmlrpc;

import com.redhat.red.build.koji.model.util.StringListValueBinder;
import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.KeyRefs;
import org.commonjava.rwx.binding.anno.StructPart;

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
    @Converter( StringListValueBinder.class )
    private List<String> extensions;

    @DataKey( "id" )
    private int id;

    @DataKey( "name" )
    private String name;

    @KeyRefs( { "description", "extensions", "id", "name" } )
    public KojiArchiveType( String description, List<String> extensions, int id, String name )
    {
        this.description = description;
        this.extensions = extensions;
        this.id = id;
        this.name = name;
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
