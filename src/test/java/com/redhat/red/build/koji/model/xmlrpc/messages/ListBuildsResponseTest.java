package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
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
public class ListBuildsResponseTest
    extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();

        KojiBuildInfo one = new KojiBuildInfo(422953, 12630, "commons-io-commons-io", "2.4.0.redhat_1", "1");
        KojiBuildInfo two = new KojiBuildInfo(447248, 12630, "commons-io-commons-io", "2.4.0.redhat_1", "2");

        bindery.render( eventParser, new ListBuildsResponse( Arrays.asList( one, two ) ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        eventParser.clearEvents();

        List<Event<?>> capturedEvents = parseEvents( "listBuilds-byGAV-response.xml" );

        assertEquals( objectEvents, capturedEvents );
    }

    @Test
    public void roundTrip()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();

        KojiBuildInfo one = new KojiBuildInfo(422953, 12630, "commons-io-commons-io", "2.4.0.redhat_1", "1");
        KojiBuildInfo two = new KojiBuildInfo(447248, 12630, "commons-io-commons-io", "2.4.0.redhat_1", "2");

        bindery.render( eventParser, new ListBuildsResponse( Arrays.asList( one, two ) ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( objectEvents );

        ListBuildsResponse parsed = bindery.parse( generator, ListBuildsResponse.class );
        assertNotNull( parsed );

        assertThat( parsed.getBuilds().contains( one ), equalTo( true ) );
        assertThat( parsed.getBuilds().contains( two ), equalTo( true ) );
    }

    @Test
    public void parseNilResult()
            throws Exception
    {
        List<Event<?>> capturedEvents = parseEvents( "listBuilds-byGAV-response.xml" );

        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( capturedEvents );

        ListBuildsResponse parsed = bindery.parse( generator, ListBuildsResponse.class );
        assertNotNull( parsed );

        assertThat( parsed.getBuilds() == null || parsed.getBuilds().isEmpty(), equalTo( true ) );
    }
}
