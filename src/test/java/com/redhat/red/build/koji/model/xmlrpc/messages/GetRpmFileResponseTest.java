/*
 * Copyright (C) 2015 Red Hat, Inc.
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

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmFileInfo;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

import java.util.Date;

public class GetRpmFileResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetRpmFileResponse response = parseCapturedMessage( GetRpmFileResponse.class, "getRPMFile-response.xml" );
        KojiRpmFileInfo info = response.getRpmFileInfo();

        assertEquals( "6ed04d16b115f4d4c9ea4e4d17d6354f524997520f10b84844cf71b95e0f0d7b", info.getDigest() );
        assertEquals( "sha256", info.getDigestAlgo() );
        assertEquals( Integer.valueOf( 0 ), info.getFlags() );
        assertEquals( "mockbuild", info.getGroup() );
        assertEquals( "6ed04d16b115f4d4c9ea4e4d17d6354f524997520f10b84844cf71b95e0f0d7b", info.getMd5() );
        assertEquals( Integer.valueOf( 0100644) , info.getMode() );
        assertEquals( new Date( 1548265249L * 1000L ), info.getMtime() );
        assertEquals( "4.3-core-fh-core-backend.json", info.getName() );
        assertEquals( Long.valueOf( 66004L ), info.getSize() );
        assertEquals( "mockbuild", info.getUser() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiRpmFileInfo info = new KojiRpmFileInfo();

        info.setDigest( "6ed04d16b115f4d4c9ea4e4d17d6354f524997520f10b84844cf71b95e0f0d7b" );
        info.setDigestAlgo( "sha256" );
        info.setFlags ( 0 );
        info.setGroup( "mockbuild" );
        info.setMd5( "6ed04d16b115f4d4c9ea4e4d17d6354f524997520f10b84844cf71b95e0f0d7b" );
        info.setMode( 0100644 );
        info.setMtime( new Date( 1548265249L * 1000L ) );
        info.setName( "4.3-core-fh-core-backend.json" );
        info.setSize( 66004L );
        info.setUser( "mockbuild" );

        GetRpmFileResponse parsed = roundTrip( GetRpmFileResponse.class, new GetRpmFileResponse( info ) );

        assertEquals( info, parsed.getRpmFileInfo() );
    }
}
