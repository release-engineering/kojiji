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
package com.redhat.red.build.koji.model.messages;

import org.commonjava.rwx.estream.model.Event;
import org.commonjava.rwx.impl.LoggingXmlRpcListener;
import org.commonjava.rwx.impl.estream.EventStreamGeneratorImpl;
import org.commonjava.rwx.impl.estream.EventStreamParserImpl;
import com.redhat.red.build.koji.model.KojiTagInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 12/3/15.
 */
public class TagResponseTest
        extends AbstractKojiMessageTest
{

    private static final String TAG = "test-tag";

    @Test
    public void verifyVsCapturedHttpRequest()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();
        LoggingXmlRpcListener parser = new LoggingXmlRpcListener( eventParser );

        List<String> arches = Arrays.asList( "x86_64", "i386" );
        bindery.render( parser, newResponse( arches ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        eventParser.clearEvents();

        List<Event<?>> capturedEvents = parseEvents( "getTag-response.xml" );

        assertEquals( objectEvents, capturedEvents );
    }

    @Test
    public void roundTrip()
            throws Exception
    {
        Logger logger = LoggerFactory.getLogger( getClass() );
        logger.info( "START RENDER" );
        EventStreamParserImpl eventParser = new EventStreamParserImpl();
        LoggingXmlRpcListener parser = new LoggingXmlRpcListener( eventParser );

        List<String> arches = Arrays.asList( "x86_64", "i386" );
        bindery.render( parser, newResponse( arches ) );

        logger.info( "END RENDER" );

        List<Event<?>> objectEvents = eventParser.getEvents();
        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( objectEvents );

        logger.info( "START PARSE" );
        TagResponse parsed = bindery.parse( generator, TagResponse.class );
        logger.info( "END PARSE" );
        assertNotNull( parsed );

        KojiTagInfo tagInfo = parsed.getTagInfo();
        assertNotNull( tagInfo );

        assertThat( tagInfo.getName(), equalTo( TAG ) );
        assertThat( tagInfo.getArches(), equalTo( arches ) );
    }

    @Test
    public void roundTrip_SingleArch()
            throws Exception
    {
        Logger logger = LoggerFactory.getLogger( getClass() );
        logger.info( "START RENDER" );
        EventStreamParserImpl eventParser = new EventStreamParserImpl();
        LoggingXmlRpcListener parser = new LoggingXmlRpcListener( eventParser );

        List<String> arches = Collections.singletonList( "x86_64" );
        bindery.render( parser, newResponse( arches ) );

        logger.info( "END RENDER" );

        List<Event<?>> objectEvents = eventParser.getEvents();
        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( objectEvents );

        logger.info( "START PARSE" );
        TagResponse parsed = bindery.parse( generator, TagResponse.class );
        logger.info( "END PARSE" );
        assertNotNull( parsed );

        KojiTagInfo tagInfo = parsed.getTagInfo();
        assertNotNull( tagInfo );

        assertThat( tagInfo.getName(), equalTo( TAG ) );
        assertThat( tagInfo.getArches(), equalTo( arches ) );
    }

    private TagResponse newResponse( List<String> arches )
    {
        return new TagResponse( new KojiTagInfo( 1001, "test-tag", "admin", 1, arches, true, true, true ) );
    }
}
