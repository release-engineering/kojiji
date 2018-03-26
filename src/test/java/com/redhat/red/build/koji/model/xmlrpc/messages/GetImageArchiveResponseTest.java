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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiImageArchiveInfo;

public class GetImageArchiveResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetImageArchiveResponse parsed =
                        parseCapturedMessage( GetImageArchiveResponse.class, "getImageArchive-response.xml" );

        KojiImageArchiveInfo info = parsed.getImageArchiveInfo();

        assertThat( info.getArchiveId(), equalTo( 2372993 ) );
        assertThat( info.getRootId(), equalTo( true ) );
        assertThat( info.getArch(), equalTo( "x86_64" ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiImageArchiveInfo info = new KojiImageArchiveInfo();
        info.setArchiveId( 238496 );
        info.setRootId( true );
        info.setArch( "x86_64" );

        GetImageArchiveResponse parsed = roundTrip( GetImageArchiveResponse.class, new GetImageArchiveResponse( info ) );

        assertThat( parsed.getImageArchiveInfo().getArchiveId(), equalTo( info.getArchiveId() ) );
        assertThat( parsed.getImageArchiveInfo().getRootId(), equalTo( info.getRootId() ) );
        assertThat( parsed.getImageArchiveInfo().getArch(), equalTo( info.getArch() ) );
    }
}
