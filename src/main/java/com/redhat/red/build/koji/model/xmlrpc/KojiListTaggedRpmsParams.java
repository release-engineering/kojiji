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

@StructPart
public class KojiListTaggedRpmsParams
                extends KojiParams
{
    @DataKey( "event" )
    private Integer eventId;

    @DataKey( "inherit" )
    private Boolean inherit;

    @DataKey( "latest" )
    private Boolean latest;

    @DataKey( "package" )
    private Integer packageId;

    @DataKey( "arch" )
    private String arch;

    @DataKey( "rpmsigs" )
    private Boolean rpmsigs;

    @DataKey( "owner" )
    private Integer ownerId;

    @DataKey( "type" )
    private String type;

    public KojiListTaggedRpmsParams()
    {

    }

    public Integer getEventId()
    {
        return eventId;
    }

    public void setEventId( Integer eventId )
    {
        this.eventId = eventId;
    }

    public Boolean getInherit()
    {
        return inherit;
    }

    public void setInherit( Boolean inherit )
    {
        this.inherit = inherit;
    }

    public Boolean getLatest()
    {
        return latest;
    }

    public void setLatest( Boolean latest )
    {
        this.latest = latest;
    }

    public Integer getPackageId()
    {
        return packageId;
    }

    public void setPackageId( Integer packageId )
    {
        this.packageId = packageId;
    }

    public String getArch()
    {
        return arch;
    }

    public void setArch( String arch )
    {
        this.arch = arch;
    }

    public Boolean getRpmsigs()
    {
        return rpmsigs;
    }

    public void setRpmsigs( Boolean rpmsigs )
    {
        this.rpmsigs = rpmsigs;
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

    public KojiListTaggedRpmsParams withEventId( Integer eventId )
    {
        this.eventId = eventId;
        return this;
    }

    public KojiListTaggedRpmsParams withInherit( Boolean inherit )
    {
        this.inherit = inherit;
        return this;
    }

    public KojiListTaggedRpmsParams withLatest( Boolean latest )
    {
        this.latest = latest;
        return this;
    }

    public KojiListTaggedRpmsParams withPackageId( Integer packageId )
    {
        this.packageId = packageId;
        return this;
    }

    public KojiListTaggedRpmsParams withArch( String arch )
    {
        this.arch = arch;
        return this;
    }

    public KojiListTaggedRpmsParams withRpmSigs( Boolean rpmsigs )
    {
        this.rpmsigs = rpmsigs;
        return this;
    }

    public KojiListTaggedRpmsParams withOwnerId( Integer ownerId )
    {
        this.ownerId = ownerId;
        return this;
    }

    public KojiListTaggedRpmsParams withType( String type )
    {
        this.type = type;
        return this;
    }
}
