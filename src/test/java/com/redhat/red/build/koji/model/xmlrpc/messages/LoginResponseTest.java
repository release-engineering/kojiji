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
import org.apache.commons.io.IOUtils;
import org.commonjava.rwx.estream.model.Event;
import org.commonjava.rwx.impl.estream.EventStreamGeneratorImpl;
import org.commonjava.rwx.impl.estream.EventStreamParserImpl;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by jdcasey on 12/3/15.
 */
public class LoginResponseTest
        extends AbstractKojiMessageTest
{

    @Test
    public void verifyVsCapturedHttpRequest()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();
        bindery.render( eventParser, new LoginResponse( new KojiSessionInfo( 12716309, "2982-CTP0Zv6YcYqRAF1uLKs" ) ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        eventParser.clearEvents();

        List<Event<?>> capturedEvents = parseEvents( "login-response.xml" );

        assertEquals( objectEvents, capturedEvents );
    }

    @Test
    public void roundTrip()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();
        bindery.render( eventParser, new LoginResponse( new KojiSessionInfo( 12716309, "2982-CTP0Zv6YcYqRAF1uLKs" ) ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( objectEvents );

        LoginResponse parsed = bindery.parse( generator, LoginResponse.class );
        assertNotNull( parsed );
    }

    @Test
    public void shouldParse()
            throws Exception
    {
        String responseAsString = readResource( "login-response.xml" );
//        String responseAsString = "<?xml version='1.0'?>\n" +
//                "<methodResponse>\n" +
//                "<params>\n" +
//                "<param>\n" +
//                "<value><struct>\n" +
//                "<member>\n" +
//                "<name>session-id</name>\n" +
//                "<value><int>15468078</int></value>\n" +
//                "</member>\n" +
//                "<member>\n" +
//                "<name>session-key</name>\n" +
//                "<value><string>3489-7wSOLpIaVL2CvWtCJuy</string></value>\n" +
//                "</member>\n" +
//                "</struct></value>\n" +
//                "</param>\n" +
//                "</params>\n" +
//                "</methodResponse>";
        LoginResponse response = bindery.parse( responseAsString, LoginResponse.class );

        System.out.println( response );
    }
}
