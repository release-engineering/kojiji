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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildState;
import com.redhat.red.build.koji.model.xmlrpc.KojiMavenRef;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jdcasey on 5/9/16.
 */
public class ListBuildsRequestTest
                extends AbstractKojiMessageTest
{

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        ListBuildsRequest parsed = parseCapturedMessage( ListBuildsRequest.class, "listBuilds-byGAV-request.xml" );
        ListBuildsRequest expected = new ListBuildsRequest(
                        new SimpleProjectVersionRef( "commons-io", "commons-io", "2.4.0.redhat-1" ),
                        KojiBuildState.ALL );
        assertEquals( expected.getQuery().toString(), parsed.getQuery().toString() );

    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiMavenRef gav = new KojiMavenRef( "commons-io", "commons-io", "2.4.0.redhat-1" );
        ListBuildsRequest parsed =
                        roundTrip( ListBuildsRequest.class, new ListBuildsRequest( gav, KojiBuildState.ALL ) );

        assertThat( parsed.getQuery().getMavenRef(), equalTo( gav ) );
    }

    @Test
    public void renderXML() throws Exception
    {
        ProjectVersionRef gav = new SimpleProjectVersionRef( "commons-io", "commons-io", "2.4.0.redhat-1" );
        String xml = rwxMapper.render( new ListBuildsRequest( gav, KojiBuildState.ALL ) );
        System.out.println( xml );
    }
}
