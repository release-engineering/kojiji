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

import com.redhat.red.build.koji.model.xmlrpc.KojiWinArchiveInfo;

import java.util.Arrays;

public class GetWinArchiveResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetWinArchiveResponse parsed =
                        parseCapturedMessage( GetWinArchiveResponse.class, "getWinArchive-response.xml" );

        KojiWinArchiveInfo info = parsed.getWinArchiveInfo();

        assertThat( info.getArchiveId(), equalTo( 1912682 ) );
        assertThat( info.getFlags(), equalTo( Arrays.asList( "fre" ) ) );
        assertThat( info.getPlatforms(), equalTo( Arrays.asList( "i686" ) ) );
        assertThat( info.getRelPath(), equalTo( null ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiWinArchiveInfo info = new KojiWinArchiveInfo();
        info.setArchiveId( 1912682 );
        info.setFlags( Arrays.asList( "fre" ) );
        info.setPlatforms( Arrays.asList( "i686" ) );
        info.setRelPath( null );

        GetWinArchiveResponse parsed = roundTrip( GetWinArchiveResponse.class, new GetWinArchiveResponse( info ) );

        assertThat( parsed.getWinArchiveInfo().getArchiveId(), equalTo( info.getArchiveId() ) );
        assertThat( parsed.getWinArchiveInfo().getFlags(), equalTo( info.getFlags() ) );
        assertThat( parsed.getWinArchiveInfo().getPlatforms(), equalTo( info.getPlatforms() ) );
        assertThat( parsed.getWinArchiveInfo().getRelPath(), equalTo( info.getRelPath() ) );
    }
}
