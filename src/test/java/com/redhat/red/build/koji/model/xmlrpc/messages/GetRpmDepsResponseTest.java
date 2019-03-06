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

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmDependencyInfo;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class GetRpmDepsResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetRpmDepsResponse response = parseCapturedMessage( GetRpmDepsResponse.class, "getRPMDeps-response.xml" );

        assertEquals( 2, response.getRpmDependencyInfos().size() );

        KojiRpmDependencyInfo info = response.getRpmDependencyInfos().get( 0 );

        assertEquals( Integer.valueOf( 0 ), info.getType() );
        assertEquals( "4.6.0-1", info.getVersion() );
        assertEquals( Integer.valueOf( 16777226 ), info.getFlags() );
        assertEquals( "rpmlib(FileDigests)", info.getName() );

        info = response.getRpmDependencyInfos().get( 1 );

        assertEquals( Integer.valueOf( 0 ), info.getType() );
        assertEquals( "3.0.4-1", info.getVersion() );
        assertEquals( Integer.valueOf( 16777226 ), info.getFlags() );
        assertEquals( "rpmlib(CompressedFileNames)", info.getName() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiRpmDependencyInfo info1 = new KojiRpmDependencyInfo();
        info1.setType ( 0 );
        info1.setVersion( "4.6.0-1" );
        info1.setFlags ( 16777226 );
        info1.setName( "rpmlib(FileDigests)" );

        KojiRpmDependencyInfo info2 = new KojiRpmDependencyInfo();
        info2.setType ( 0 );
        info2.setVersion( "3.0.4-1" );
        info2.setFlags ( 16777226 );
        info2.setName( "rpmlib(CompressedFileNames)" );

        List<KojiRpmDependencyInfo> infos = new ArrayList<>();
        infos.add( info1 );
        infos.add( info2 );

        GetRpmDepsResponse parsed = roundTrip( GetRpmDepsResponse.class, new GetRpmDepsResponse( infos ) );

        assertEquals( 2, parsed.getRpmDependencyInfos().size() );

        KojiRpmDependencyInfo parsed1 = parsed.getRpmDependencyInfos().get( 0 );
        KojiRpmDependencyInfo parsed2 = parsed.getRpmDependencyInfos().get( 1 );

        assertEquals( info1, parsed1 );
        assertEquals( info2, parsed2 );
    }
}
