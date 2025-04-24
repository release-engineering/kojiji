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
package com.redhat.red.build.koji.model.xmlrpc;

import java.util.Objects;

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

@StructPart
public class KojiRpmSignatureInfo
{
    @DataKey( "rpm_id" )
    private Integer rpmId;

    @DataKey( "sighash" )
    private String sighash;

    @DataKey( "sigkey" )
    private String sigkey;

    public KojiRpmSignatureInfo()
    {

    }

    public Integer getRpmId()
    {
        return rpmId;
    }

    public void setRpmId( Integer rpmId )
    {
        this.rpmId = rpmId;
    }

    public String getSighash()
    {
        return sighash;
    }

    public void setSighash( String sighash )
    {
        this.sighash = sighash;
    }

    public void setSigkey( String sigkey )
    {
        this.sigkey = sigkey;
    }

    public String getSigkey()
    {
        return sigkey;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( super.equals( obj ) )
        {
            return true;
        }

        if ( !( obj instanceof KojiRpmSignatureInfo ) )
        {
            return false;
        }

        KojiRpmSignatureInfo that = (KojiRpmSignatureInfo) obj;

        return Objects.equals( this.rpmId, that.rpmId ) && Objects.equals( this.sighash, that.sighash ) && Objects.equals( this.sigkey, that.sigkey );
    }

    @Override
    public String toString()
    {
        return "KojiRpmSignatureInfo{" +
               "rpmId=" + rpmId +
               ", sighash='" + sighash +
               "', sigkey='" + sigkey +
               "'}";
    }
}
