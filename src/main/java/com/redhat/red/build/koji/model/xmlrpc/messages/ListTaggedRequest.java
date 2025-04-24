/*
 * Copyright (C) 2015 Red Hat, Inc.
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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiBooleanOrInteger;
import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.LIST_TAGGED;

/**
 * Created by jdcasey on 8/8/16.
 */
@Request( method = LIST_TAGGED )
public class ListTaggedRequest
{
    @DataIndex( 0 )
    private KojiIdOrName tag;

    @DataIndex( 1 )
    private Integer eventId;

    @DataIndex( 2 )
    private boolean inherit;

    @DataIndex( 3 )
    private String prefix; // filter results by build['package_name'].startswith(prefix)

    @DataIndex( 4 )
    private KojiBooleanOrInteger latest = KojiBooleanOrInteger.getFor( Boolean.FALSE );

    @DataIndex( 5 )
    private Integer packageId;

    @DataIndex( 6 )
    private Integer ownerId;

    @DataIndex( 7 )
    private String type;

    public ListTaggedRequest()
    {

    }

    public ListTaggedRequest( String tag )
    {
        this.tag = new KojiIdOrName( tag );
    }

    public KojiIdOrName getTag()
    {
        return tag;
    }

    public void setTag( KojiIdOrName tag )
    {
        this.tag = tag;
    }

    public Integer getEventId()
    {
        return eventId;
    }

    public void setEventId( Integer eventId )
    {
        this.eventId = eventId;
    }

    public boolean isInherit()
    {
        return inherit;
    }

    public boolean getInherit()
    {
        return inherit;
    }

    public void setInherit( boolean inherit )
    {
        this.inherit = inherit;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix( String prefix )
    {
        this.prefix = prefix;
    }

    public boolean isLatest()
    {
        return latest.getAsBoolean();
    }

    public KojiBooleanOrInteger getLatest()
    {
        return latest;
    }

    public void setLatest( KojiBooleanOrInteger latest )
    {
        this.latest = latest;
    }

    public void setLatest( boolean latest )
    {
        this.latest = KojiBooleanOrInteger.getFor( latest );
    }

    public void setLatest( int latest )
    {
        this.latest = KojiBooleanOrInteger.getFor( latest );
    }

    public Integer getPackageId()
    {
        return packageId;
    }

    public void setPackageId( Integer packageId )
    {
        this.packageId = packageId;
    }

    public Integer getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId( Integer ownerId )
    {
        this.ownerId = ownerId;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public ListTaggedRequest withTag( KojiIdOrName tag )
    {
        this.tag = tag;
        return this;
    }

    public ListTaggedRequest withTag( String tag )
    {
        this.tag = new KojiIdOrName( tag );
        return this;
    }

    public ListTaggedRequest withTag( int tag )
    {
        this.tag = new KojiIdOrName( tag );
        return this;
    }

    public ListTaggedRequest withEventId( Integer eventId )
    {
        this.eventId = eventId;
        return this;
    }

    public ListTaggedRequest withInherit( boolean inherit )
    {
        this.inherit = inherit;
        return this;
    }

    public ListTaggedRequest withPrefix( String prefix )
    {
      this.prefix = prefix;
      return this;
    }

    public ListTaggedRequest withLatest( KojiBooleanOrInteger latest )
    {
        this.latest = latest;
        return this;
    }

    public ListTaggedRequest withLatest( boolean latest )
    {
        this.latest = KojiBooleanOrInteger.getFor( latest );
        return this;
    }

    public ListTaggedRequest withLatest( int latest )
    {
        this.latest = KojiBooleanOrInteger.getFor( latest );
        return this;
    }

    public ListTaggedRequest withPackageId( Integer packageId )
    {
        this.packageId = packageId;
        return this;
    }

    public ListTaggedRequest withOwnerId( Integer ownerId )
    {
        this.ownerId = ownerId;
        return this;
    }

    public ListTaggedRequest withType( String type )
    {
        this.type = type;
        return this;
    }
}
