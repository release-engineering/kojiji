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

import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiMavenRef;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jdcasey on 8/9/16.
 */
public class ListArchivesRequestTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        ListArchivesRequest parsed = parseCapturedMessage( ListArchivesRequest.class, "listArchives-request-0.xml" );
        assertListArchivesRequest( parsed );
    }

    @Test
    public void roundTrip() throws Exception
    {
        ListArchivesRequest parsed = roundTrip( ListArchivesRequest.class, new ListArchivesRequest( pvr ) );
        assertListArchivesRequest( parsed );
        assertEquals( parsed.getQuery().getFilename(), "bar-1.1.jar" );
    }

    private void assertListArchivesRequest( ListArchivesRequest parsed )
    {
        KojiArchiveQuery query = parsed.getQuery();
        assertEquals( query.getType(), "maven" );
        assertEquals( query.getMavenRef(), new KojiMavenRef( "org.foo", "bar", "1.1" ) );
    }

    private static SimpleProjectVersionRef pvr = new SimpleProjectVersionRef( "org.foo", "bar", "1.1" );

    public ListArchivesRequest getRequest()
    {
        KojiArchiveQuery query = new KojiArchiveQuery( pvr );

        ListArchivesRequest request = new ListArchivesRequest();
        request.setQuery( query );
        return request;
    }
}
