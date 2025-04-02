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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class KrbLoginRequestTest
    extends AbstractKojiMessageTest
{
    private static final String krbRequest = "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4KVGhlIHF1aWNrIGJy\n" +
            "b3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1w\n" +
            "cyBvdmVyIHRoZSBsYXp5IGRvZy4KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBs\n" +
            "YXp5IGRvZy4KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4KVGhl\n" +
            "IHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4KVGhlIHF1aWNrIGJyb3du\n" +
            "IGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBv\n" +
            "dmVyIHRoZSBsYXp5IGRvZy4KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5\n" +
            "IGRvZy4KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4KVGhlIHF1\n" +
            "aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4=\n";

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        KrbLoginRequest parsed = parseCapturedMessage( KrbLoginRequest.class, "krbLogin-request.xml" );
        assertThat( parsed.getKrbRequest(), equalTo( krbRequest ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KrbLoginRequest parsed = roundTrip( KrbLoginRequest.class, new KrbLoginRequest( krbRequest ) );
        assertThat( parsed.getKrbRequest(), equalTo( krbRequest ) );
    }
}
