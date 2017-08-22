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
 * Created by jdcasey on 8/5/16.
 */
@StructPart
public class KojiPackageQuery
        extends KojiQuery
{
    @DataKey( "tagID" )
    private Integer tagId;

    @DataKey( "userID" )
    private Integer userId;

    @DataKey( "pkgID" )
    private Integer pkgId;

    @DataKey( "prefix" )
    private String prefix;

    @DataKey( "inherited" )
    private Boolean inherited;

//    @DataKey( "with_dupes" )
//    private boolean withDuplicates;

    @DataKey( "event" )
    private Integer eventId;

    public KojiPackageQuery()
    {
    }

    public void setTagId( Integer tagId )
    {
        this.tagId = tagId;
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    public void setPkgId( Integer pkgId )
    {
        this.pkgId = pkgId;
    }

    public Boolean getInherited()
    {
        return inherited;
    }

    public void setInherited( Boolean inherited )
    {
        this.inherited = inherited;
    }

    public void setEventId( Integer eventId )
    {
        this.eventId = eventId;
    }

    public Integer getTagId()
    {
        return tagId;
    }

    public void setTagId( int tagId )
    {
        this.tagId = tagId;
    }

    public KojiPackageQuery withTagId( int tagId )
    {
        this.tagId = tagId;
        return this;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( int userId )
    {
        this.userId = userId;
    }

    public KojiPackageQuery withUserId( int userId )
    {
        this.userId = userId;
        return this;
    }

    public Integer getPkgId()
    {
        return pkgId;
    }

    public void setPkgId( int pkgId )
    {
        this.pkgId = pkgId;
    }

    public KojiPackageQuery withPkgId( int pkgId )
    {
        this.pkgId = pkgId;
        return this;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix( String prefix )
    {
        this.prefix = prefix;
    }

    public KojiPackageQuery withPrefix( String prefix )
    {
        this.prefix = prefix;
        return this;
    }

    public boolean isInherited()
    {
        return inherited;
    }

    public void setInherited( boolean inherited )
    {
        this.inherited = inherited;
    }

    public KojiPackageQuery withInherited( boolean inherited )
    {
        this.inherited = inherited;
        return this;
    }

//    public boolean isWithDuplicates()
//    {
//        return withDuplicates;
//    }
//
//    public void setWithDuplicates( boolean withDuplicates )
//    {
//        this.withDuplicates = withDuplicates;
//    }
//
//    public KojiPackageQuery withDuplicates( boolean withDuplicates )
//    {
//        this.withDuplicates = withDuplicates;
//        return this;
//    }

    public Integer getEventId()
    {
        return eventId;
    }

    public void setEventId( int eventId )
    {
        this.eventId = eventId;
    }

    public KojiPackageQuery withEventId( int eventId )
    {
        this.eventId = eventId;
        return this;
    }

}
