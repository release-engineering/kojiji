package com.redhat.red.build.koji.model.json;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jdcasey on 2/15/16.
 */
public class BuildTool
{
    @JsonProperty(NAME)
    private String name;

    @JsonProperty(VERSION)
    private String version;

    @JsonCreator
    public BuildTool( @JsonProperty(NAME) String name, @JsonProperty(VERSION) String version )
    {
        this.name = name;
        this.version = version;
    }

    public String getName()
    {
        return name;
    }

    public String getVersion()
    {
        return version;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildTool ) )
        {
            return false;
        }

        BuildTool that = (BuildTool) o;

        if ( getName() != null ? !getName().equals( that.getName() ) : that.getName() != null )
        {
            return false;
        }
        return !( getVersion() != null ? !getVersion().equals( that.getVersion() ) : that.getVersion() != null );

    }

    @Override
    public int hashCode()
    {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + ( getVersion() != null ? getVersion().hashCode() : 0 );
        return result;
    }
}
