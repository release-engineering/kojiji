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

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import com.redhat.red.build.koji.KojiClientException;

@StructPart
public class KojiNVRA
{
    @DataKey( "name" )
    private String name;

    @DataKey( "version" )
    private String version;

    @DataKey( "release" )
    private String release;

    @DataKey( "epoch" )
    private Integer epoch;

    @DataKey( "arch" )
    private String arch;

    @DataKey( "location" )
    private String location;

    @DataKey( "src" )
    private Boolean src = Boolean.FALSE;

    public KojiNVRA( String name, String version, String release, String arch, String location )
    {
        this.name = name;
        this.version = version;
        this.release = release;
        this.arch = arch;
        this.location = location;
    }

    public KojiNVRA( String name, String version, String release, String arch )
    {
        this( name, version, release, arch, null );
    }

    public KojiNVRA()
    {
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

    public Integer getEpoch()
    {
        return epoch;
    }

    public void setEpoch( Integer epoch )
    {
        this.epoch = epoch;
    }

    public String getArch()
    {
        return arch;
    }

    public void setArch( String arch )
    {
        this.arch = arch;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation( String location )
    {
        this.location = location;
    }

    public Boolean getSrc()
    {
        return src;
    }

    public void setSrc( Boolean src )
    {
        this.src = src;
    }

    public static KojiNVRA parseNVRA( String nvra )
            throws KojiClientException
    {
        String[] parts = nvra.split( "@", 2 );
        String location = null;

        if ( parts.length > 1 )
        {
            nvra = parts[0];
            location = parts[1];
        }

        if ( nvra.endsWith( ".rpm" ) )
        {
            nvra = nvra.substring( 0, nvra.length() - 4 );
        }

        int p3 = nvra.lastIndexOf( "." );

        if ( p3 == -1 || p3 == nvra.length() - 1 )
        {
            throw new KojiClientException( "Invalid NVRA: " + nvra);
        }

        String arch = nvra.substring( p3 + 1, nvra.length() );
        KojiNVR nvr = KojiNVR.parseNVR( nvra.substring( 0, p3 ) );
        KojiNVRA ret = new KojiNVRA();

        ret.setName( nvr.getName() );
        ret.setVersion( nvr.getVersion() );
        ret.setRelease( nvr.getRelease() );
        ret.setEpoch( nvr.getEpoch() );
        ret.setArch( arch );
        ret.setLocation( location );

        if ( arch.equals( "src" ) )
        {
            ret.setSrc( Boolean.TRUE );
        }

        return ret;
    }

    public String renderString()
    {
        if ( location == null )
        {
            if ( epoch == null )
            {
                return String.format( "%s-%s-%s.%s", name, version, release, arch );
            }
            else
            {
                return String.format( "%s:%s-%s-%s.%s", epoch, name, version, release, arch );
            }
        }
        else
        {
            if ( epoch == null )
            {
                return String.format( "%s-%s-%s.%s@%s", name, version, release, arch, location );
            }
            else
            {
                return String.format( "%s:%s-%s-%s.%s@%s", epoch, name, version, release, arch, location );
            }
        }
    }

    @Override
    public String toString()
    {
        return renderString();
    }
}
