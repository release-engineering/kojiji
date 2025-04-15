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

import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallObj;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MultiCallGetBuildRequestTest
                extends AbstractKojiMessageTest
{

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        MultiCallRequest parsed = parseCapturedMessage( MultiCallRequest.class, "multicall-getBuild-request.xml" );
        List<KojiMultiCallObj> multiCallObjs = parsed.getMultiCallObjs();

        // check #1
        KojiMultiCallObj obj = multiCallObjs.get( 0 );
        assertEquals( "getBuild", obj.getMethodName() );
        assertEquals( 513598, obj.getParams().get( 0 ) );

        // check #2
        obj = multiCallObjs.get( 1 );
        assertEquals( "getBuild", obj.getMethodName() );
        assertEquals( 513599, obj.getParams().get( 0 ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        MultiCallRequest multiCallRequest = new MultiCallRequest();
        List<KojiMultiCallObj> multiCallObjs = new ArrayList<>();
        multiCallRequest.setMultiCallObjs( multiCallObjs );

        // add call 1
        KojiMultiCallObj obj = new KojiMultiCallObj();
        obj.setMethodName( "getBuild" );
        List<Object> params = new ArrayList<>();
        params.add( 513598 );
        obj.setParams( params );
        multiCallObjs.add( obj );

        // add call 2
        obj = new KojiMultiCallObj();
        obj.setMethodName( "getBuild" );
        params = new ArrayList<>();
        params.add( 513599 );
        obj.setParams( params );
        multiCallObjs.add( obj );

        // round trip
        MultiCallRequest parsed = roundTrip( MultiCallRequest.class, multiCallRequest );

        // asserts
        List<KojiMultiCallObj> parsedCallObjs = parsed.getMultiCallObjs();
        assertEquals( parsedCallObjs.size(), 2 );

        KojiMultiCallObj call = parsedCallObjs.get( 0 );
        assertEquals( call.getMethodName(), "getBuild" );
        assertEquals( call.getParams().get( 0 ), 513598 );

        call = parsedCallObjs.get( 1 );
        assertEquals( call.getParams().get( 0 ), 513599 );

    }

}
