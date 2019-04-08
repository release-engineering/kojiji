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

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmInfo;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class ListRpmsResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        RpmListResponse response = parseCapturedMessage( RpmListResponse.class, "listRPMs-response.xml" );

        assertEquals( 2, response.getRpms().size() );

        KojiRpmInfo info = response.getRpms().get( 0 );

        assertEquals( Integer.valueOf( 6719758 ), info.getId() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiRpmInfo info1 = new KojiRpmInfo();
        info1.setId ( Integer.valueOf( 6719758 ) );

        List<KojiRpmInfo> infos = new ArrayList<>( 1 );
        infos.add( info1 );

        RpmListResponse parsed = roundTrip( RpmListResponse.class, new RpmListResponse( infos ) );

        assertEquals( 1, parsed.getRpms().size() );

        KojiRpmInfo parsed1 = parsed.getRpms().get( 0 );

        assertEquals( info1.getId(), parsed1.getId() );
    }
}
