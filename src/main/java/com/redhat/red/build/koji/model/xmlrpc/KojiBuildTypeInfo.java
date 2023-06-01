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

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.ArrayList;
import java.util.List;

@StructPart
public class KojiBuildTypeInfo
{
    private final static String MAVEN = "maven";

    private final static String WIN = "win";

    private final static String IMAGE = "image";

    private final static String RPM = "rpm";

    private final static String NPM = "npm";

    @DataKey ( MAVEN )
    private KojiMavenBuildInfo maven;

    @DataKey ( WIN )
    private KojiWinBuildInfo win;

    @DataKey ( IMAGE )
    private KojiImageBuildInfo image;

    @DataKey ( RPM )
    private KojiRpmBuildInfo rpm;

    @DataKey ( NPM )
    private KojiNpmBuildInfo npm;

    // a build may contain more than one types, e.g., maven and rpm
    private List<String> names = new ArrayList<>(  );

    private List<Object> buildInfoList = new ArrayList<>(  );

    public KojiNpmBuildInfo getNpm()
    {
        return npm;
    }

    public void setNpm( KojiNpmBuildInfo npm )
    {
        this.npm = npm;
        names.add( NPM );
        buildInfoList.add( npm );
    }

    public KojiRpmBuildInfo getRpm()
    {
        return rpm;
    }

    public void setRpm( KojiRpmBuildInfo rpm )
    {
        this.rpm = rpm;
        names.add( RPM );
        buildInfoList.add( rpm );
    }

    public KojiMavenBuildInfo getMaven()
    {
        return maven;
    }

    public void setMaven( KojiMavenBuildInfo maven )
    {
        this.maven = maven;
        names.add( MAVEN );
        buildInfoList.add( maven );
    }

    public KojiWinBuildInfo getWin()
    {
        return win;
    }

    public void setWin( KojiWinBuildInfo win )
    {
        this.win = win;
        names.add( WIN );
        buildInfoList.add( win );
    }

    public KojiImageBuildInfo getImage()
    {
        return image;
    }

    public void setImage( KojiImageBuildInfo image )
    {
        this.image = image;
        names.add( IMAGE );
        buildInfoList.add( image );
    }

    public List<String> getNames()
    {
        return names;
    }

    /**
     * Util method. Add buildTypeInfo to buildInfo.
     *
     * @param buildTypeInfo source
     * @param buildInfo target
     * @return buildInfo
     */
    public static KojiBuildInfo addBuildTypeInfo( KojiBuildTypeInfo buildTypeInfo, KojiBuildInfo buildInfo )
    {
        if ( buildInfo == null )
        {
            return null;
        }

        List<Object> inf = buildTypeInfo.getBuildInfo();

        if ( inf == null || inf.isEmpty() )
        {
            return buildInfo;
        }

        for ( Object obj : inf )
        {
            if ( obj instanceof KojiMavenBuildInfo )
            {
                KojiMavenBuildInfo maven = (KojiMavenBuildInfo) obj;
                buildInfo.setId( maven.getBuildId() );
                buildInfo.setMavenArtifactId( maven.getArtifactId() );
                buildInfo.setMavenGroupId( maven.getGroupId() );
                buildInfo.setMavenVersion( maven.getVersion() );
            }
            else if ( obj instanceof KojiWinBuildInfo )
            {
                KojiWinBuildInfo win = (KojiWinBuildInfo) obj;
                buildInfo.setId( win.getBuildId() );
                buildInfo.setPlatform( win.getPlatform() );
            }
            else if ( obj instanceof KojiImageBuildInfo )
            {
                KojiImageBuildInfo image = (KojiImageBuildInfo) obj;
                buildInfo.setId( image.getBuildId() );
            }
            else if ( obj instanceof KojiNpmBuildInfo )
            {
                // TODO Comment this until brew support to return the buildInfo for NPM builds
                // KojiNpmBuildInfo npm = (KojiNpmBuildInfo) obj;
                // buildInfo.setId( npm.getBuildId() );
                // buildInfo.setName( npm.getName() );
                // buildInfo.setVersion( npm.getVersion() );
            }
            else if ( obj instanceof KojiRpmBuildInfo )
            {
                KojiRpmBuildInfo rpm = (KojiRpmBuildInfo) obj; // nothing to set
            }
        }
        buildInfo.setTypeNames( buildTypeInfo.getNames() );

        return buildInfo;
    }

    public List<Object> getBuildInfo()
    {
        return buildInfoList;
    }

    @Override
    public int hashCode()
    {
        int result = 0;
        List<Object> inf = getBuildInfo();
        if ( inf != null )
        {
            for ( Object o : inf )
            {
                result = 31 * result + o.hashCode();
            }
        }
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

        KojiBuildTypeInfo other = (KojiBuildTypeInfo) o;

        return getBuildInfo().containsAll( other.getBuildInfo() );
    }

    @Override
    public String toString()
    {
        return "KojiBuildTypeInfo { names=" + names + ", buildInfo=" + getBuildInfo() + " }";
    }
}
