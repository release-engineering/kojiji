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

import com.redhat.red.build.koji.KojiClientException;

/**
 * Created by jdcasey on 1/29/16.
 */
@StructPart
public class KojiNVR
{
    @DataKey( "packageID" )
    private KojiIdOrName packageId;

    @DataKey( "userID" )
    private KojiIdOrName userId;

    @DataKey( "tagID" )
    private KojiIdOrName tagId;

    @DataKey( "name" )
    private String name;

    @DataKey( "version" )
    private String version;

    @DataKey( "release" )
    private String release;

    @DataKey( "epoch" )
    private Integer epoch;

    public KojiNVR( String name, String version, String release, Integer epoch )
    {
        this.name = name;
        this.version = version;
        this.release = release;
        this.epoch = epoch;
    }

    public KojiNVR( String name, String version, String release )
    {
        this( name, version, release, null );
    }

    public KojiNVR()
    {
    }

    public KojiIdOrName getPackageId()
    {
        return packageId;
    }

    public void setPackageId( KojiIdOrName packageId )
    {
        this.packageId = packageId;
    }

    public KojiIdOrName getUserId()
    {
        return userId;
    }

    public void setUserId( KojiIdOrName userId )
    {
        this.userId = userId;
    }

    public KojiIdOrName getTagId()
    {
        return tagId;
    }

    public void setTagId( KojiIdOrName tagId )
    {
        this.tagId = tagId;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public void setRelease( String release )
    {
        this.release = release;
    }

    public void setEpoch( Integer epoch )
    {
        this.epoch = epoch;
    }

    public String getName()
    {
        return name;
    }

    public String getVersion()
    {
        return version;
    }

    public String getRelease()
    {
        return release;
    }

    public Integer getEpoch()
    {
        return epoch;
    }

    public static KojiNVR parseNVR( String nvr )
            throws KojiClientException
    {
        int p2 = nvr.lastIndexOf( "-" );

        if ( p2 == -1 || p2 == nvr.length() - 1 )
        {
            throw new KojiClientException( "Invalid NVR: " + nvr );
        }

        int p1 = nvr.substring( 0, p2 ).lastIndexOf( "-" );

        if ( p1 == -1 || p1 == p2 - 1 )
        {
            throw new KojiClientException( "Invalid NVR: " + nvr );
        }

        KojiNVR ret = new KojiNVR();

        ret.setRelease( nvr.substring( p2 + 1, nvr.length() ) );
        ret.setVersion( nvr.substring( p1 + 1, p2 ) );
        ret.setName( nvr.substring( 0, p1 ) );

        int epochIndex = ret.getName().indexOf( ":" );

        if ( epochIndex != -1 )
        {
            ret.setEpoch( Integer.parseInt( ret.getName().substring( 0, epochIndex ) ) );
            ret.setName( ret.getName().substring( epochIndex + 1,  ret.getName().length() ) );
        }

        return ret;
    }

    public String renderString()
    {
        if ( getEpoch() == null )
        {
            return String.format( "%s-%s-%s", getName(), getVersion().replace( '-', '_' ), getRelease() );
        }
        else
        {
            return String.format( "%s:%s-%s-%s", getEpoch(), getName(), getVersion().replace( '-', '_' ), getRelease() );
        }
    }

    @Override
    public String toString()
    {
        return renderString();
    }
}
