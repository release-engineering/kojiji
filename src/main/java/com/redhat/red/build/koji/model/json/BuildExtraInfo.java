package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.KeyRefs;
import org.commonjava.rwx.binding.anno.SkipNull;
import org.commonjava.rwx.binding.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.MAVEN_INFO;

/**
 * Created by jdcasey on 9/15/16.
 */
@StructPart
@SkipNull
public class BuildExtraInfo
{
    @JsonProperty( MAVEN_INFO )
    @DataKey( MAVEN_INFO )
    private MavenExtraInfo mavenExtraInfo;

    @KeyRefs( MAVEN_INFO )
    public BuildExtraInfo( @JsonProperty( MAVEN_INFO ) MavenExtraInfo mavenExtraInfo )
    {
        this.mavenExtraInfo = mavenExtraInfo;
    }

    public BuildExtraInfo( ProjectVersionRef gav )
    {
        this.mavenExtraInfo = new MavenExtraInfo( gav );
    }

    public MavenExtraInfo getMavenExtraInfo()
    {
        return mavenExtraInfo;
    }

    public void setMavenExtraInfo( MavenExtraInfo mavenExtraInfo )
    {
        this.mavenExtraInfo = mavenExtraInfo;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildExtraInfo ) )
        {
            return false;
        }

        BuildExtraInfo that = (BuildExtraInfo) o;

        return getMavenExtraInfo() != null ?
                getMavenExtraInfo().equals( that.getMavenExtraInfo() ) :
                that.getMavenExtraInfo() == null;

    }

    @Override
    public int hashCode()
    {
        return getMavenExtraInfo() != null ? getMavenExtraInfo().hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return "BuildMavenInfo{" +
                "mavenExtraInfo=" + mavenExtraInfo +
                '}';
    }
}
