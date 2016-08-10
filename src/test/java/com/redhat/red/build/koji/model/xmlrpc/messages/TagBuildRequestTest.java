package com.redhat.red.build.koji.model.xmlrpc.messages;

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
 * Created by jdcasey on 8/9/16.
 */
public class TagBuildRequestTest
        extends AbstractKojiMessageTest
{

    private static final String TAG = "test-tag";
    private static final String NVR = "org.foo-bar-1.1-2";

    @Test
    public void verifyVsCapturedHttpRequest()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();
        bindery.render( eventParser, new TagBuildRequest( TAG, NVR ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        eventParser.clearEvents();

        List<Event<?>> capturedEvents = parseEvents( "tagBuild-request-0.xml" );

        assertEquals( objectEvents, capturedEvents );
    }

    @Test
    public void roundTrip()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();
        bindery.render( eventParser, new TagBuildRequest( TAG, NVR ) );

        List<Event<?>> objectEvents = eventParser.getEvents();
        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( objectEvents );

        TagBuildRequest parsed = bindery.parse( generator, TagBuildRequest.class );
        assertNotNull( parsed );

        assertThat( parsed.getTag().getName(), equalTo( TAG ) );
        assertThat( parsed.getBuild().getName(), equalTo( NVR ) );
    }
}
