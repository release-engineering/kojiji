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

import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

@StructPart
public class KojiMavenArchiveInfo
{
    @DataKey( "archive_id" )
    private Integer archiveId;

    @DataKey( "group_id" )
    private String groupId;

    @DataKey( "artifact_id" )
    private String artifactId;

    @DataKey( "version" )
    private String version;

    public KojiMavenArchiveInfo()
    {
    }

    public KojiMavenArchiveInfo(Integer archiveId, String groupId, String artifactId, String version )
    {
        this.archiveId = archiveId;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public Integer getArchiveId()
    {
        return archiveId;
    }

    public void setArchiveId( Integer archiveId )
    {
        this.archiveId = archiveId;
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

    public ProjectVersionRef getGAV()
    {
        return new SimpleProjectVersionRef( groupId, artifactId, version );
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof KojiMavenArchiveInfo) )
        {
            return false;
        }

        KojiMavenArchiveInfo that = (KojiMavenArchiveInfo) o;

        return getArchiveId() == that.getArchiveId();

    }

    @Override
    public int hashCode()
    {
        return getArchiveId();
    }

    public String toString()
    {
        return String.format( "KojiMavenArchiveInfo[%s:%s:%s]", getGroupId(), getArtifactId(), getVersion() );
    }
}
