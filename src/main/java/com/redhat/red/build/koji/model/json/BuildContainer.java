package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ARCH;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.TYPE;

/**
 * Created by jdcasey on 2/15/16.
 */
public class BuildContainer
{
    @JsonProperty( TYPE )
    private String type;

    @JsonProperty( ARCH )
    private String arch;

    @JsonCreator
    public BuildContainer( @JsonProperty( TYPE ) String type, @JsonProperty( ARCH ) String arch )
    {
        this.type = type;
        this.arch = arch;
    }

    public String getArch()
    {
        return arch;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildContainer ) )
        {
            return false;
        }

        BuildContainer that = (BuildContainer) o;

        if ( getType() != null ? !getType().equals( that.getType() ) : that.getType() != null )
        {
            return false;
        }
        return !( getArch() != null ? !getArch().equals( that.getArch() ) : that.getArch() != null );

    }

    @Override
    public int hashCode()
    {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + ( getArch() != null ? getArch().hashCode() : 0 );
        return result;
    }
}
