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

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmFileInfo;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class ListRpmFilesResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        ListRpmFilesResponse response = parseCapturedMessage( ListRpmFilesResponse.class, "listRPMFiles-response.xml" );

        assertEquals( 70, response.getRpmFileInfos().size() );

        KojiRpmFileInfo info = response.getRpmFileInfos().get( 0 );

        assertEquals( "6ed04d16b115f4d4c9ea4e4d17d6354f524997520f10b84844cf71b95e0f0d7b", info.getDigest() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiRpmFileInfo info1 = new KojiRpmFileInfo();
        info1.setDigest ( "6ed04d16b115f4d4c9ea4e4d17d6354f524997520f10b84844cf71b95e0f0d7b" );

        List<KojiRpmFileInfo> infos = new ArrayList<>(1);
        infos.add( info1 );

        ListRpmFilesResponse parsed = roundTrip( ListRpmFilesResponse.class, new ListRpmFilesResponse( infos ) );

        assertEquals( 1, parsed.getRpmFileInfos().size() );

        KojiRpmFileInfo parsed1 = parsed.getRpmFileInfos().get( 0 );

        assertEquals( info1.getDigest(), parsed1.getDigest() );
    }
}
