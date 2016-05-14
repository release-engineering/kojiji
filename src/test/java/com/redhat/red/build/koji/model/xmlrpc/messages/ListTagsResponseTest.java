package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;
import org.commonjava.rwx.estream.model.Event;
import org.commonjava.rwx.impl.estream.EventStreamGeneratorImpl;
import org.commonjava.rwx.impl.estream.EventStreamParserImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 5/11/16.
 */
public class ListTagsResponseTest
    extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();

        KojiTagInfo tag = new KojiTagInfo( 6951, "test-tag", null, null, null, false, false, false );
        bindery.render( eventParser, new ListTagsResponse( Arrays.asList( tag ) ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        eventParser.clearEvents();

        List<Event<?>> capturedEvents = parseEvents( "listTags-byBuildId-response.xml" );

        assertEquals( objectEvents, capturedEvents );
    }

    @Test
    public void roundTrip()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();

        KojiTagInfo tag = new KojiTagInfo( 6951, "test-tag", null, null, null, false, false, false );
        bindery.render( eventParser, new ListTagsResponse( Arrays.asList( tag ) ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( objectEvents );

        ListTagsResponse parsed = bindery.parse( generator, ListTagsResponse.class );
        assertNotNull( parsed );

        assertThat( parsed.getTags().size(), equalTo( 1 ) );
        assertThat( parsed.getTags().contains( tag ), equalTo( true ) );
    }
}
