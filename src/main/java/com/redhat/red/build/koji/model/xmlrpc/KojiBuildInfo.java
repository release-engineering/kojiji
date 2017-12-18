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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.redhat.red.build.koji.model.converter.KojiBuildStateConverter;
import com.redhat.red.build.koji.model.converter.TimestampConverter;
import org.apache.commons.lang.StringUtils;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.redhat.red.build.koji.model.util.DateUtils.toUTC;

/**
 * Created by jdcasey on 1/29/16.
 */
@StructPart
public class KojiBuildInfo
{
    @DataKey( "build_id" )
    @JsonProperty( "build_id" )
    private int id;

    @DataKey( "package_id" )
    @JsonProperty( "package_id" )
    private int packageId;

    @DataKey( "package_name" )
    @JsonProperty( "package_name" )
    private String name;

    @DataKey( "version" )
    private String version;

    @DataKey( "release" )
    private String release;

    @Converter( TimestampConverter.class )
    @DataKey( "completion_time" )
    @JsonProperty( "completion_time" )
    private Date completionTime;

    @Converter( TimestampConverter.class )
    @DataKey( "creation_time" )
    @JsonProperty( "creation_time" )
    private Date creationTime;

    @DataKey( "nvr" )
    private String nvr;

    @DataKey( "task_id" )
    @JsonProperty( "task_id" )
    private Integer taskId;

    @DataKey( "owner_id" )
    @JsonProperty( "owner_id" )
    private Integer ownerId;

    @DataKey( "owner_name" )
    @JsonProperty( "owner_name" )
    private String ownerName;

    @Converter( KojiBuildStateConverter.class )
    @DataKey( "state" )
    @JsonProperty( "state" )
    private KojiBuildState buildState;

    @DataKey( "creation_event_id" )
    @JsonProperty( "creation_event_id" )
    private Integer creationEventId;

    @DataKey( "maven_group_id" )
    @JsonProperty( "maven_group_id" )
    private String mavenGroupId;

    @DataKey( "maven_artifact_id" )
    @JsonProperty( "maven_artifact_id" )
    private String mavenArtifactId;

    @DataKey( "maven_version" )
    @JsonProperty( "maven_version" )
    private String mavenVersion;

    @DataKey( "platform" )
    private String platform;

    @DataKey( "extra" )
    private Map<String, Object> extra;

    @DataKey( "source" )
    @JsonProperty( "source" )
    private String source;

    @JsonIgnore
    private List<String> typeNames; // a build may contain more than one types, e.g., maven and rpm.

    /*
      TODO: Implement the following fields, once we care about them:
      epoch
      volume_id: ID of the storage volume
      volume_name: name of the storage volume
      creation_ts: time the build was created (epoch)
      completion_ts: time the build was completed (epoch, may be null)
     */

    public KojiBuildInfo()
    {
    }

    public KojiBuildInfo( int id, int packageId, String name, String version, String release )
    {
        this.id = id;
        this.packageId = packageId;
        this.name = name;
        this.version = version;
        this.release = release;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public int getPackageId()
    {
        return packageId;
    }

    public void setPackageId( int packageId )
    {
        this.packageId = packageId;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public String getRelease()
    {
        return release;
    }

    public void setRelease( String release )
    {
        this.release = release;
    }

    public Date getCompletionTime()
    {
        // make these sortable...never null
        return completionTime == null ? new Date() : completionTime;
    }

    public void setCompletionTime( Date completionTime )
    {
        this.completionTime = toUTC( completionTime );
    }

    public Date getCreationTime()
    {
        // make these sortable...never null
        return creationTime == null ? new Date() : creationTime;
    }

    public void setCreationTime( Date creationTime )
    {
        this.creationTime = toUTC( creationTime );
    }

    public String getNvr()
    {
        return nvr;
    }

    public void setNvr( String nvr )
    {
        this.nvr = nvr;
    }

    public Integer getTaskId()
    {
        return taskId;
    }

    public void setTaskId( Integer taskId )
    {
        this.taskId = taskId;
    }

    public Integer getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId( Integer ownerId )
    {
        this.ownerId = ownerId;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName( String ownerName )
    {
        this.ownerName = ownerName;
    }

    public KojiBuildState getBuildState()
    {
        return buildState;
    }

    public void setBuildState( KojiBuildState buildState )
    {
        this.buildState = buildState;
    }

    public Integer getCreationEventId()
    {
        return creationEventId;
    }

    public void setCreationEventId( Integer creationEventId )
    {
        this.creationEventId = creationEventId;
    }

    public String getMavenGroupId()
    {
        return mavenGroupId;
    }

    public void setMavenGroupId( String mavenGroupId )
    {
        this.mavenGroupId = mavenGroupId;
    }

    public String getMavenArtifactId()
    {
        return mavenArtifactId;
    }

    public void setMavenArtifactId( String mavenArtifactId )
    {
        this.mavenArtifactId = mavenArtifactId;
    }

    public String getMavenVersion()
    {
        return mavenVersion;
    }

    public void setMavenVersion( String mavenVersion )
    {
        this.mavenVersion = mavenVersion;
    }

    @JsonIgnore
    public ProjectVersionRef getGAV()
    {
        if ( StringUtils.isEmpty(mavenGroupId) || StringUtils.isEmpty(mavenArtifactId) )
        {
            return null;
        }
        return new SimpleProjectVersionRef( getMavenGroupId(), getMavenArtifactId(), getMavenVersion() );
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform( String platform )
    {
        this.platform = platform;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource( String source )
    {
        this.source = source;
    }

    public List<String> getTypeNames()
    {
        return typeNames;
    }

    public void setTypeNames( List<String> typeNames )
    {
        this.typeNames = typeNames;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof KojiBuildInfo ) )
        {
            return false;
        }

        KojiBuildInfo that = (KojiBuildInfo) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode()
    {
        return getId();
    }

    @Override
    public String toString()
    {
        return String.format( "KojiBuildInfo[%s-%s-%s]", getName(), getVersion().replace( '-', '_' ), getRelease() );
    }

    /**
     * Returns extra metadata of the build, mapped as a XML-RPC struct.
     * @return Map of XML-RPC types of extra data.
     * @see <a href="https://ws.apache.org/xmlrpc/types.html">https://ws.apache.org/xmlrpc/types.html</a>
     */
    public Map<String, Object> getExtra() {
        return extra;
    }

    /**
     * Sets the extra metadata for the build.
     * @param extra The extra metadata, mapped to XML-RPC types.
     * @see #getExtra()
     */
    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
