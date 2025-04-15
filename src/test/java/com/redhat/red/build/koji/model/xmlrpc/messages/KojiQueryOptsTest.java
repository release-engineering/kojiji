/*
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
import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiChecksumType;
import com.redhat.red.build.koji.model.xmlrpc.KojiQueryOpts;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

public class KojiQueryOptsTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        ListArchivesRequest parsedRequest = parseCapturedMessage( ListArchivesRequest.class, "listArchives-request-1.xml" );

        assertListArchivesRequest( parsedRequest );

        ListArchivesResponse parsedResponse = parseCapturedMessage( ListArchivesResponse.class, "listArchives-response-1.xml" );

        assertListArchivesResponse( parsedResponse );

        KojiQueryCountOnlyResponse countOnlyResponse = parseCapturedMessage( KojiQueryCountOnlyResponse.class, "listArchives-queryOpts-countOnly-response.xml" );

        assertKojiQueryCountOnlyResponse( countOnlyResponse );

        KojiQueryAsListResponse asListResponse = parseCapturedMessage( KojiQueryAsListResponse.class, "getRPMDeps-queryOpts-asList-response.xml" );

        List<List<Object>> lists = asListResponse.getLists();

        assertNotNull( lists );

        assertThat( lists.size(), equalTo( 8 ) );

        for ( List<Object> list : lists )
        {
            assertThat( list.size(), equalTo( 4 ) );
        }
    }

    @Test
    public void roundTrip() throws Exception
    {
        // koji call listArchives --kwargs '{"checksum":"d41d8cd98f00b204e9800998ecf8427e", "queryOpts":{"countOnly":False,"order":"-build_id","offset":1,"limit":1,"asList ":False,"rowlock":False}}'
        KojiArchiveQuery query = new KojiArchiveQuery().withChecksum( "d41d8cd98f00b204e9800998ecf8427e" );
        KojiQueryOpts queryOpts = new KojiQueryOpts().withCountOnly( false ).withOrder( "-build_id" ).withOffset( 1 ).withLimit( 1 ).withAsList( false ).withRowlock( false );

        query.setQueryOpts( queryOpts );

        ListArchivesRequest request = new ListArchivesRequest();

        request.setQuery( query );

        ListArchivesRequest parsed = roundTrip( ListArchivesRequest.class, request );

        assertListArchivesRequest( parsed );
    }

    private void assertListArchivesRequest( ListArchivesRequest parsed )
    {
        KojiArchiveQuery query = parsed.getQuery();

        assertEquals( query.getChecksum(), "d41d8cd98f00b204e9800998ecf8427e" );

        KojiQueryOpts queryOpts = query.getQueryOpts();

        assertNotNull( queryOpts );
        assertEquals( false, queryOpts.getCountOnly() );
        assertEquals( "-build_id", queryOpts.getOrder() );
        assertEquals( Integer.valueOf( 1 ), queryOpts.getOffset() );
        assertEquals( false, queryOpts.getAsList() );
        assertEquals( false, queryOpts.getRowlock() );
    }

    private void assertListArchivesResponse( ListArchivesResponse parsed )
    {
        List<KojiArchiveInfo> archives = parsed.getArchives();

        assertNotNull( archives );

        assertThat( archives.isEmpty(), equalTo( false ) );

        KojiArchiveInfo archive = archives.get( 0 );

        assertNotNull( archive );
        assertThat( archive.getBuildTypeId(), equalTo( 2 ) );
        assertThat( archive.getBuildId(), equalTo( 672001 ) );
        assertNull( archive.getBuildrootId() );
        assertThat( archive.getChecksum(), equalTo( "d41d8cd98f00b204e9800998ecf8427e" ) );
        assertThat( archive.getChecksumType(), equalTo( KojiChecksumType.md5 ) );
        assertNull( archive.getExtra() );
        assertThat( archive.getFilename(), equalTo( "pax-web-extender-whiteboard-4.3.6.jar" ) );
        assertThat( archive.getArchiveId(), equalTo( 2418341 ) );
        assertThat( archive.getMetadataOnly(), equalTo( false ) );
        assertThat( archive.getSize(), equalTo( 0 ) );
        assertThat( archive.getTypeDescription(), equalTo( "Jar file" ) );
        assertThat( archive.getTypeExtensions(), equalTo( "jar war rar ear sar kar jdocbook jdocbook-style plugin" ) );
        assertThat( archive.getTypeId(), equalTo( 1 ) );
        assertThat( archive.getTypeName(), equalTo( "jar" ) );
    }

    private void assertKojiQueryCountOnlyResponse( KojiQueryCountOnlyResponse parsed )
    {
        int count = parsed.getCount();

        assertThat( count, equalTo( 1 ) );

    }
}
