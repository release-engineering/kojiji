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

import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

@StructPart
public class KojiMavenBuildInfo
{
    @DataKey( "build_id" )
    private int buildId;

    @DataKey( "group_id" )
    private String groupId;

    @DataKey( "artifact_id" )
    private String artifactId;

    @DataKey( "version" )
    private String version;

    public KojiMavenBuildInfo()
    {
    }

    public KojiMavenBuildInfo(int buildId, String groupId, String artifactId, String version )
    {
        this.buildId = buildId;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public int getBuildId()
    {
        return buildId;
    }

    public void setBuildId( int buildId )
    {
        this.buildId = buildId;
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
        if ( !( o instanceof KojiMavenBuildInfo) )
        {
            return false;
        }

        KojiMavenBuildInfo that = (KojiMavenBuildInfo) o;

        return getBuildId() == that.getBuildId();

    }

    @Override
    public int hashCode()
    {
        return getBuildId();
    }

    public String toString()
    {
        return String.format( "KojiMavenBuildInfo[%s-%s-%s]", getGroupId(), getArtifactId(), getVersion().replace( '-', '_' ) );
    }
}
