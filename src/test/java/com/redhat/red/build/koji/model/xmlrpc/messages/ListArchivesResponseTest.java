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

import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveInfo;
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
 * Created by jdcasey on 8/9/16.
 */
public class ListArchivesResponseTest
        extends AbstractKojiMessageTest
{
    private static final int BUILD_ID=101010;
    private static final String G="org.foo";
    private static final String A="bar";
    private static final String V="1.1";
    private static final String T="pom";
    private static final String FNAME=A + "-" + V + "." + T;
    private static final int TYPE_ID=3;
    private static final String CHECKSUM="f18c45047648e5d6d3ad71319488604e";
    private static final String TYPE_DESC="Maven Project Object Management file";
    private static final String TYPE_EXT="pom";
    private static final int CHECKSUM_TYPE=0;
    private static final int ID=12345678;
    private static final int SIZE=1177;

    @Test
    public void verifyVsCapturedHttpRequest()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();
        bindery.render( eventParser, basicResponse() );

        List<Event<?>> objectEvents = eventParser.getEvents();
        eventParser.clearEvents();

        List<Event<?>> capturedEvents = parseEvents( "listArchives-response-0.xml" );

        assertEquals( objectEvents, capturedEvents );
    }

    private ListArchivesResponse basicResponse()
    {
        KojiArchiveInfo archive = new KojiArchiveInfo();
        archive.setGroupId( G );
        archive.setArtifactId( A );
        archive.setArchiveId( ID );
        archive.setBuildId( BUILD_ID );
        archive.setChecksum( CHECKSUM );
        archive.setChecksumType( CHECKSUM_TYPE );
        archive.setFilename( FNAME );
        archive.setExtension( TYPE_EXT );
        archive.setMetadataOnly( false );
        archive.setSize( SIZE );
        archive.setTypeDescription( TYPE_DESC );
        archive.setVersion( V );
        archive.setTypeId( TYPE_ID );

        List<KojiArchiveInfo> archives = Arrays.asList( archive );
        return new ListArchivesResponse( archives );
    }

    @Test
    public void roundTrip()
            throws Exception
    {
        EventStreamParserImpl eventParser = new EventStreamParserImpl();

        bindery.render( eventParser, basicResponse() );

        List<Event<?>> objectEvents = eventParser.getEvents();
        EventStreamGeneratorImpl generator = new EventStreamGeneratorImpl( objectEvents );

        ListArchivesResponse parsed = bindery.parse( generator, ListArchivesResponse.class );
        assertNotNull( parsed );

        List<KojiArchiveInfo> archives = parsed.getArchives();
        assertNotNull( archives );
        assertThat( archives.isEmpty(), equalTo( false ) );

        KojiArchiveInfo archive = archives.get( 0 );
        assertNotNull( archive );
        assertThat( archive.getGroupId(), equalTo( G ) );
        assertThat( archive.getArtifactId(), equalTo( A ) );
        assertThat( archive.getVersion(), equalTo( V ) );
        assertThat( archive.getBuildId(), equalTo( BUILD_ID ) );
        assertThat( archive.getChecksum(), equalTo( CHECKSUM ) );
        assertThat( archive.getChecksumType(), equalTo( CHECKSUM_TYPE ) );
        assertThat( archive.getFilename(), equalTo( FNAME ) );
        assertThat( archive.getExtension(), equalTo( TYPE_EXT ) );
        assertThat( archive.getMetadataOnly(), equalTo( false ) );
        assertThat( archive.getSize(), equalTo( SIZE ) );
        assertThat( archive.getTypeDescription(), equalTo( TYPE_DESC ) );
        assertThat( archive.getTypeId(), equalTo( TYPE_ID ) );
    }

    @Test
    public void renderXML()
            throws Exception
    {
        String xml = bindery.renderString( basicResponse() );
        System.out.println( xml );
    }
}
