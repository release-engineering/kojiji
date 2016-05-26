package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiTagQuery;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.estream.model.Event;
import org.commonjava.rwx.impl.estream.EventStreamGeneratorImpl;
import org.commonjava.rwx.impl.estream.EventStreamParserImpl;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 5/11/16.
 */
public class ListTagsRequestTest
    extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttpRequest()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();
        bindery.render( eventParser, new ListTagsRequest( new KojiTagQuery( 422953 ) ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        eventParser.clearEvents();

        List<Event<?>> capturedEvents = parseEvents( "listTags-byBuildId-request.xml" );

        assertEquals( objectEvents, capturedEvents );
    }

    @Test
    public void roundTrip()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();

        int buildId = 422953;
        bindery.render( eventParser, new ListTagsRequest( new KojiTagQuery( buildId ) ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( objectEvents );

        ListTagsRequest parsed = bindery.parse( generator, ListTagsRequest.class );
        assertNotNull( parsed );

        assertThat( parsed.getQuery().getBuildId().getId(), equalTo( buildId ) );
    }

    @Test
    public void renderXML()
            throws Exception
    {
        int buildId = 422953;
        String xml = bindery.renderString( new ListTagsRequest( new KojiTagQuery( buildId ) ) );
        System.out.println( xml );
    }
}
