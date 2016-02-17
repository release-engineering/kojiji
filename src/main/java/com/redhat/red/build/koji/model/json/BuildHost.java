package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ARCH;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.OS;

/**
 * Created by jdcasey on 2/15/16.
 */
public class BuildHost
{
    @JsonProperty( OS )
    private String os;

    @JsonProperty( ARCH )
    private String arch;

    @JsonCreator
    public BuildHost( @JsonProperty( OS ) String os, @JsonProperty( ARCH ) String arch )
    {
        this.os = os;
        this.arch = arch;
    }

    public String getOs()
    {
        return os;
    }

    public String getArch()
    {
        return arch;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildHost ) )
        {
            return false;
        }

        BuildHost that = (BuildHost) o;

        if ( getOs() != null ? !getOs().equals( that.getOs() ) : that.getOs() != null )
        {
            return false;
        }
        return !( getArch() != null ? !getArch().equals( that.getArch() ) : that.getArch() != null );

    }

    @Override
    public int hashCode()
    {
        int result = getOs() != null ? getOs().hashCode() : 0;
        result = 31 * result + ( getArch() != null ? getArch().hashCode() : 0 );
        return result;
    }
}
