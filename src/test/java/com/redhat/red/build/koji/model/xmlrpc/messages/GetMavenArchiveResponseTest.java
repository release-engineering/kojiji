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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiMavenArchiveInfo;

public class GetMavenArchiveResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetMavenArchiveResponse parsed =
                        parseCapturedMessage( GetMavenArchiveResponse.class, "getMavenArchive-response.xml" );

        KojiMavenArchiveInfo info = parsed.getMavenArchiveInfo();

        assertThat( info.getArchiveId(), equalTo( 238496 ) );
        assertThat( info.getArtifactId(), equalTo( "commons-lang" ) );
        assertThat( info.getGroupId(), equalTo( "commons-lang" ) );
        assertThat( info.getVersion(), equalTo( "2.6-redhat-2" ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiMavenArchiveInfo info = new KojiMavenArchiveInfo();
        info.setArchiveId( 238496 );
        info.setArtifactId( "commons-lang" );
        info.setGroupId( "commons-lang" );
        info.setVersion( "2.6-redhat-2" );

        GetMavenArchiveResponse parsed = roundTrip( GetMavenArchiveResponse.class, new GetMavenArchiveResponse( info ) );

        assertThat( parsed.getMavenArchiveInfo().getArchiveId(), equalTo( info.getArchiveId() ) );
        assertThat( parsed.getMavenArchiveInfo().getArtifactId(), equalTo( info.getArtifactId() ) );
        assertThat( parsed.getMavenArchiveInfo().getGroupId(), equalTo( info.getGroupId() ) );
        assertThat( parsed.getMavenArchiveInfo().getVersion(), equalTo( info.getVersion() ) );
    }
}
