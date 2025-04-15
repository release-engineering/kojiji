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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.commonjava.rwx.anno.ArrayPart;
import org.commonjava.rwx.anno.DataIndex;

import com.redhat.red.build.koji.model.xmlrpc.KojiKrbAddressInfo;

@ArrayPart
public class KrbLoginResponseInfo
{
    @DataIndex( 0 )
    String encodedApResponse;

    @DataIndex( 1 )
    String encodedEncryptedSessionInfo;

    @DataIndex( 2 )
    KojiKrbAddressInfo addressInfo;

    public KrbLoginResponseInfo( String encodedApResponse, String encodedEncryptedSessionInfo, KojiKrbAddressInfo addressInfo )
    {
        this.encodedApResponse = encodedApResponse;
        this.encodedEncryptedSessionInfo = encodedEncryptedSessionInfo;
        this.addressInfo = addressInfo;
    }

    public KrbLoginResponseInfo()
    {
    }

    public void setEncodedApResponse( String encodedApResponse )
    {
        this.encodedApResponse = encodedApResponse;
    }

    public void setEncodedEncryptedSessionInfo( String encodedEncryptedSessionInfo )
    {
        this.encodedEncryptedSessionInfo = encodedEncryptedSessionInfo;
    }

    public void setAddressInfo( KojiKrbAddressInfo addressInfo )
    {
        this.addressInfo = addressInfo;
    }

    public String getEncodedApResponse()
    {
        return encodedApResponse;
    }

    public String getEncodedEncryptedSessionInfo()
    {
        return encodedEncryptedSessionInfo;
    }

    public KojiKrbAddressInfo getAddressInfo()
    {
        return addressInfo;
    }
}
