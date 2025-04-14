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

/**
 * Created by jdcasey on 8/5/16.
 */
@StructPart
public class KojiPackageInfo
{
    @DataKey( "owner_name" )
    private String owner;

    @DataKey( "owner_id" )
    private Integer ownerId;

    @DataKey( "package_name" )
    private String packageName;

    @DataKey( "package_id" )
    private Integer packageId;

    @DataKey( "tag_id" )
    private Integer tagId;

    @DataKey( "tag_name" )
    private String tagName;

    @DataKey( "blocked" )
    private boolean blocked;

    public KojiPackageInfo()
    {
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner( String owner )
    {
        this.owner = owner;
    }

    public Integer getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId( Integer ownerId )
    {
        this.ownerId = ownerId;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName( String packageName )
    {
        this.packageName = packageName;
    }

    public Integer getPackageId()
    {
        return packageId;
    }

    public void setPackageId( Integer packageId )
    {
        this.packageId = packageId;
    }

    public Integer getTagId()
    {
        return tagId;
    }

    public void setTagId( Integer tagId )
    {
        this.tagId = tagId;
    }

    public String getTagName()
    {
        return tagName;
    }

    public void setTagName( String tagName )
    {
        this.tagName = tagName;
    }

    public boolean isBlocked()
    {
        return blocked;
    }

    public boolean getBlocked()
    {
        return blocked;
    }

    public void setBlocked( boolean blocked )
    {
        this.blocked = blocked;
    }
}
