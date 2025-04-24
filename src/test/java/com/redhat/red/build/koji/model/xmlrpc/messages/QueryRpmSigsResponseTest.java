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

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmSignatureInfo;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class QueryRpmSigsResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        QueryRpmSigsResponse response = parseCapturedMessage( QueryRpmSigsResponse.class, "queryRPMSigs-response.xml" );

        assertEquals( 2, response.getRpmSignatureInfos().size() );

        KojiRpmSignatureInfo info = response.getRpmSignatureInfos().get( 0 );

        assertEquals( Integer.valueOf( 6719757 ), info.getRpmId() );
        assertEquals( "4c85961843c8ca4eca14e0d6b3eefb80", info.getSighash() );
        assertEquals( null, info.getSigkey() );

        info = response.getRpmSignatureInfos().get( 1 );

        assertEquals( Integer.valueOf( 6719757 ), info.getRpmId() );
        assertEquals( "1832ee86cbd54a7a67cb82bf64faa4d9", info.getSighash() );
        assertEquals( "fd431d51", info.getSigkey() );

    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiRpmSignatureInfo info1 = new KojiRpmSignatureInfo();
        info1.setRpmId ( Integer.valueOf( 6719757 ) );
        info1.setSighash( "4c85961843c8ca4eca14e0d6b3eefb80" );
        info1.setSigkey ( null );

        KojiRpmSignatureInfo info2 = new KojiRpmSignatureInfo();
        info2.setRpmId ( Integer.valueOf( 6719757 ) );
        info2.setSighash( "1832ee86cbd54a7a67cb82bf64faa4d" );
        info2.setSigkey ( "fd431d51" );

        List<KojiRpmSignatureInfo> infos = new ArrayList<>( 2 );
        infos.add( info1 );
        infos.add( info2 );

        QueryRpmSigsResponse parsed = roundTrip( QueryRpmSigsResponse.class, new QueryRpmSigsResponse( infos ) );

        assertEquals( 2, parsed.getRpmSignatureInfos().size() );

        KojiRpmSignatureInfo parsed1 = parsed.getRpmSignatureInfos().get( 0 );
        KojiRpmSignatureInfo parsed2 = parsed.getRpmSignatureInfos().get( 1 );

        assertEquals( info1, parsed1 );
        assertEquals( info2, parsed2 );
    }
}
