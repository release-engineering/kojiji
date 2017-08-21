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

    public KojiNVR( String name, String version, String release )
    {
        this.name = name;
        this.version = version;
        this.release = release;
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

    public String renderString()
    {
        return String.format( "%s-%s-%s", getName(), getVersion().replace( '-', '_' ), getRelease() );
    }
}
