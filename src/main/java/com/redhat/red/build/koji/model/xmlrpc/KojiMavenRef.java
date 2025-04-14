/*
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

import org.commonjava.atlas.maven.ident.ref.ProjectRef;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectRef;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ARTIFACT_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.GROUP_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.VERSION;

/**
 * Created by jdcasey on 11/1/16.
 */
@StructPart
public class KojiMavenRef
{
    @DataKey( GROUP_ID )
    private String groupId;

    @DataKey( ARTIFACT_ID )
    private String artifactId;

    @DataKey( VERSION )
    private String version;

    public KojiMavenRef()
    {
    }

    public KojiMavenRef( ProjectRef ref )
    {
        this.groupId = ref.getGroupId();
        this.artifactId = ref.getArtifactId();

        if ( ref instanceof ProjectVersionRef )
        {
            this.version = ( (ProjectVersionRef) ref ).getVersionString();
        }
    }

    public KojiMavenRef( String groupId, String artifactId, String version )
    {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public KojiMavenRef withGroupId( String groupId )
    {
        this.groupId = groupId;
        return this;
    }

    public KojiMavenRef withArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
        return this;
    }

    public KojiMavenRef withVersion( String version )
    {
        this.version = version;
        return this;
    }

    public ProjectVersionRef toGAV()
    {
        if ( groupId == null || artifactId == null || version == null )
        {
            return null;
        }

        return new SimpleProjectVersionRef( groupId, artifactId, version );
    }

    public ProjectRef toGA()
    {
        if ( groupId == null || artifactId == null )
        {
            return null;
        }

        if ( version == null )
        {
            return new SimpleProjectRef( groupId, artifactId );
        }

        return new SimpleProjectVersionRef( groupId, artifactId, version );
    }

    @Override
    public String toString()
    {
        return String.format( "%s:%s:%s", getGroupId(), getArtifactId(), getVersion() );
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof KojiMavenRef ) )
        {
            return false;
        }

        KojiMavenRef that = (KojiMavenRef) o;

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
}
