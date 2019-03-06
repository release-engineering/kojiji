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

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class GetRpmHeadersResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetRpmHeadersResponse response = parseCapturedMessage( GetRpmHeadersResponse.class, "getRPMHeaders-response.xml" );

        assertEquals( 2, response.getHeaders().size() );

        assertEquals( response.getHeaders().get( "summary" ), "Set of OpenShift templates that can be used to setup the Core and the MBaaS of the Red Hat Mobile Application Platform On-Premise product" );
        assertEquals( response.getHeaders().get( "description" ), "Set of OpenShift templates that can be used to setup the Core and the MBaaS of the Red Hat Mobile Application Platform On-Premise product." );
    }

    @Test
    public void roundTrip() throws Exception
    {
        Map<String, Object> map = new HashMap<>( 2 );
        map.put( "summary", "Set of OpenShift templates that can be used to setup the Core and the MBaaS of the Red Hat Mobile Application Platform On-Premise product" );
        map.put( "description", "Set of OpenShift templates that can be used to setup the Core and the MBaaS of the Red Hat Mobile Application Platform On-Premise product." );

        GetRpmHeadersResponse parsed = roundTrip( GetRpmHeadersResponse.class, new GetRpmHeadersResponse( map ) );

        assertEquals( 2, parsed.getHeaders().size() );

        Object parsed1 = parsed.getHeaders().get( "summary" );
        Object parsed2 = parsed.getHeaders().get( "description" );

        assertEquals( map.get( "summary" ), parsed1 );
        assertEquals( map.get( "description" ), parsed2 );
    }
}
