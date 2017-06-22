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

import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.StructPart;

@StructPart
public class KojiBuildTypeInfo
{
    @DataKey ("maven")
    private KojiMavenBuildInfo maven;

    @DataKey ("win")
    private KojiWinBuildInfo win;

    @DataKey ("image")
    private KojiImageBuildInfo image;

    private String name;

    public KojiBuildTypeInfo()
    {
    }

    public KojiMavenBuildInfo getMaven()
    {
        return maven;
    }

    public void setMaven( KojiMavenBuildInfo maven )
    {
        if ( this.win != null || this.image != null )
        {
            throw new IllegalArgumentException( "Type info already set" );
        }

        this.maven = maven;
        this.name = "maven";
    }

    public KojiWinBuildInfo getWin()
    {
        return win;
    }

    public void setWin( KojiWinBuildInfo win )
    {
        if ( this.maven != null || this.image != null )
        {
            throw new IllegalArgumentException( "Type info already set" );
        }

        this.win = win;
        this.name = "win";
    }

    public KojiImageBuildInfo getImage()
    {
        return image;
    }

    public void setImage( KojiImageBuildInfo image )
    {
        if ( this.maven != null || this.win != null )
        {
            throw new IllegalArgumentException( "Type info already set" );
        }
        this.image = image;
        this.name = "image";
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public KojiBuildInfo addBuildTypeInfo( KojiBuildInfo buildInfo )
    {
        if ( buildInfo == null )
        {
            return null;
        }

        Object buildTypeInfo = getBuildInfo();

        if ( buildTypeInfo == null )
        {
            return null;
        }

        if ( buildTypeInfo instanceof KojiMavenBuildInfo )
        {
            KojiMavenBuildInfo maven = (KojiMavenBuildInfo) buildTypeInfo;
            buildInfo.setId( maven.getBuildId() );
            buildInfo.setMavenArtifactId( maven.getArtifactId() );
            buildInfo.setMavenGroupId( maven.getGroupId() );
            buildInfo.setMavenVersion( maven.getVersion() );
        }
        else if ( buildTypeInfo instanceof KojiWinBuildInfo )
        {
            KojiWinBuildInfo win = (KojiWinBuildInfo) buildTypeInfo;
            buildInfo.setId( win.getBuildId() );
            buildInfo.setPlatform( win.getPlatform() );
        }
        else if ( buildTypeInfo instanceof KojiImageBuildInfo )
        {
            KojiImageBuildInfo image = (KojiImageBuildInfo) buildTypeInfo;
            buildInfo.setId( image.getBuildId() );
        }

        return buildInfo;
    }

    public void setBuildInfo(Object buildInfo)
    {
        if ( buildInfo == null )
        {
            return;
        }

        if ( buildInfo instanceof KojiMavenBuildInfo )
        {
            this.maven = (KojiMavenBuildInfo) buildInfo;
        }
        else if ( buildInfo instanceof KojiWinBuildInfo )
        {
            this.win = (KojiWinBuildInfo) buildInfo;
        }
        else if ( buildInfo instanceof KojiImageBuildInfo )
        {
            this.image = (KojiImageBuildInfo) buildInfo;
        }
    }

    public Object getBuildInfo()
    {
        return ( maven != null ? maven : ( win != null ? win : ( image != null ? image : null ) ) );
    }

    @Override
    public int hashCode()
    {
        int result = ( getBuildInfo() != null ? getBuildInfo().hashCode() : 0 );
        result = 31 * result + ( getName() != null ? getName().hashCode() : 0 );
        return result;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }

        if ( !( o instanceof KojiBuildTypeInfo ) )
        {
            return false;
        }

        KojiBuildTypeInfo that = (KojiBuildTypeInfo) o;

        if ( getBuildInfo() != null && !getBuildInfo().equals( getBuildInfo() ) )
        {
            return false;
        }

        return getName() != null ? getName().equals( that.getName() ) : that.getName() == null;
    }

    @Override
    public String toString() {
        return "KojiBuildTypeInfo{name=" + name + ", getBuildInfo()=" + getBuildInfo() + "}";
    }
}
