package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.KeyRefs;
import org.commonjava.rwx.binding.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ARTIFACT_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.GROUP_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.VERSION;

/**
 * Created by jdcasey on 9/15/16.
 */
@StructPart
public class MavenExtraInfo
{
    @JsonProperty( GROUP_ID )
    @DataKey( GROUP_ID )
    private String groupId;

    @JsonProperty( ARTIFACT_ID )
    @DataKey( ARTIFACT_ID )
    private String artifactId;

    @JsonProperty( VERSION )
    @DataKey( VERSION )
    private String version;

    @JsonCreator
    @KeyRefs( { GROUP_ID, ARTIFACT_ID, VERSION } )
    public MavenExtraInfo( @JsonProperty( GROUP_ID ) String groupId, @JsonProperty( ARTIFACT_ID ) String artifactId,
                           @JsonProperty( VERSION ) String version )
    {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public MavenExtraInfo( ProjectVersionRef gav )
    {
        this.groupId = gav.getGroupId();
        this.artifactId = gav.getArtifactId();
        this.version = gav.getVersionString();
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof MavenExtraInfo ) )
        {
            return false;
        }

        MavenExtraInfo that = (MavenExtraInfo) o;

        if ( getGroupId() != null ? !getGroupId().equals( that.getGroupId() ) : that.getGroupId() != null )
        {
            return false;
        }
        if ( getArtifactId() != null ? !getArtifactId().equals( that.getArtifactId() ) : that.getArtifactId() != null )
        {
            return false;
        }
        return getVersion() != null ? getVersion().equals( that.getVersion() ) : that.getVersion() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getGroupId() != null ? getGroupId().hashCode() : 0;
        result = 31 * result + ( getArtifactId() != null ? getArtifactId().hashCode() : 0 );
        result = 31 * result + ( getVersion() != null ? getVersion().hashCode() : 0 );
        return result;
    }

    @Override
    public String toString()
    {
        return "MavenExtraInfo{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
