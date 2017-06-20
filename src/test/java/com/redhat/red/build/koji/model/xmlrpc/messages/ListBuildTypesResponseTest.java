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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildType;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;
import org.commonjava.rwx.estream.model.Event;
import org.commonjava.rwx.impl.estream.EventStreamGeneratorImpl;
import org.commonjava.rwx.impl.estream.EventStreamParserImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ListBuildTypesResponseTest
    extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();

        List<KojiBuildType> list = new ArrayList<>(4);
        KojiBuildType one = new KojiBuildType(1, "rpm");
        KojiBuildType two = new KojiBuildType(2, "maven");
        KojiBuildType three = new KojiBuildType(3, "win");
        KojiBuildType four = new KojiBuildType(4, "image");
        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);

        ListBuildTypesResponse response = new ListBuildTypesResponse();
        response.setBuildTypes(list);

        bindery.render( eventParser, response );

        List<Event<?>> objectEvents = eventParser.getEvents();
        eventParser.clearEvents();

        List<Event<?>> capturedEvents = parseEvents( "listBuildTypes-response.xml" );

        assertEquals( objectEvents, capturedEvents );
    }

    @Test
    public void roundTrip()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();

        List<KojiBuildType> list = new ArrayList<>(4);
        KojiBuildType one = new KojiBuildType(1, "rpm");
        KojiBuildType two = new KojiBuildType(2, "maven");
        KojiBuildType three = new KojiBuildType(3, "win");
        KojiBuildType four = new KojiBuildType(4, "image");
        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);

        bindery.render( eventParser, new ListBuildTypesResponse( list ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( objectEvents );

        ListBuildTypesResponse parsed = bindery.parse( generator, ListBuildTypesResponse.class );
        assertNotNull( parsed );

        assertThat( parsed.getBuildTypes().size(), equalTo( 4 ) );
        assertThat( parsed.getBuildTypes().contains( one ), equalTo( true ) );
        assertThat( parsed.getBuildTypes().contains( two ), equalTo( true ) );
        assertThat( parsed.getBuildTypes().contains( three ), equalTo( true ) );
        assertThat( parsed.getBuildTypes().contains( four ), equalTo( true ) );
    }
}
