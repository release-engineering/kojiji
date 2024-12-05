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

import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by jdcasey on 12/3/15.
 */
public class LoginResponseTest
                extends AbstractKojiMessageTest
{

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        LoginResponse parsed = parseCapturedMessage( LoginResponse.class, "login-response.xml" );
        assertLoginResponse( parsed );
    }

    private void assertLoginResponse( LoginResponse parsed )
    {
        LoginResponse expected = getInstance();

        KojiSessionInfo expected_session = expected.getSessionInfo();
        KojiSessionInfo parsed_session = parsed.getSessionInfo();

        assertEquals( expected_session.getSessionId(), parsed_session.getSessionId() );
        assertEquals( expected_session.getSessionKey(), parsed_session.getSessionKey() );
    }

    private LoginResponse getInstance()
    {
        return new LoginResponse( new KojiSessionInfo( 12716309, "2982-CTP0Zv6YcYqRAF1uLKs" ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        LoginResponse parsed = roundTrip( LoginResponse.class, getInstance() );
        assertLoginResponse( parsed );
    }

    @Test
    public void shouldParse() throws Exception
    {
        String responseAsString = readResource( "login-response.xml" );
        LoginResponse response =
                        rwxMapper.parse( new ByteArrayInputStream( responseAsString.getBytes() ), LoginResponse.class );
        System.out.println( response );
    }
}
