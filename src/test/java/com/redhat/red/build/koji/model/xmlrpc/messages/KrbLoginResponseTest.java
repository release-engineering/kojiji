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

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiKrbAddressInfo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Before;

public class KrbLoginResponseTest
    extends AbstractKojiMessageTest
{
    private static final String encodedApResponse = "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4KVGhlIHF1aWNrIGJy\n" +
            "b3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4=\n";

    private static final String encodedEncryptedSessionInfo = "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4KVGhlIHF1aWNrIGJy\n" +
            "b3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1w\n" +
            "cyBvdmVyIHRoZSBsYXp5IGRvZy4=\n";

    private static final String serverAddress = "1.2.3.4";

    private static final int serverPort = 5;

    private static final String clientAddress = "5.6.7.8";

    private static final int clientPort = 9;

    private KrbLoginResponseInfo info;

    private KojiKrbAddressInfo addressInfo;

    @Before
    public void setupKrbLoginResponseInfo() throws UnknownHostException
    {
        addressInfo = new KojiKrbAddressInfo();
        addressInfo.setClientAddress( InetAddress.getByName( clientAddress ) );
        addressInfo.setClientPort( clientPort );
        addressInfo.setServerAddress( InetAddress.getByName( serverAddress ) );
        addressInfo.setServerPort( serverPort );
        info = new KrbLoginResponseInfo();
        info.setEncodedApResponse( encodedApResponse );
        info.setEncodedEncryptedSessionInfo( encodedEncryptedSessionInfo );
        info.setAddressInfo( addressInfo );
    }

    private void verifyParsed( KrbLoginResponse response )
    {
        assertThat( response.getInfo().getEncodedApResponse(), equalTo( encodedApResponse ) );
        assertThat( response.getInfo().getEncodedEncryptedSessionInfo(), equalTo( encodedEncryptedSessionInfo ) );
        assertThat( response.getInfo().getAddressInfo().getClientAddress().getHostAddress(), equalTo( clientAddress ) );
        assertThat( response.getInfo().getAddressInfo().getClientPort(), equalTo( clientPort ) );
        assertThat( response.getInfo().getAddressInfo().getServerAddress().getHostAddress(), equalTo( serverAddress ) );
        assertThat( response.getInfo().getAddressInfo().getServerPort(), equalTo( serverPort ) );
    }

    @Test
    public void verifyVsCapturedHttpResponse() throws Exception
    {
        KrbLoginResponse parsed = parseCapturedMessage( KrbLoginResponse.class, "krbLogin-response.xml" );
        verifyParsed( parsed );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KrbLoginResponse parsed = roundTrip( KrbLoginResponse.class, new KrbLoginResponse( info ) );
        verifyParsed( parsed );
    }
}
