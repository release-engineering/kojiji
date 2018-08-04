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
package com.redhat.red.build.koji;

import com.redhat.red.build.koji.config.KojiConfig;
import com.redhat.red.build.koji.config.SimpleKojiConfigBuilder;
import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallFault;
import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallObj;
import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallValueObj;
import com.redhat.red.build.koji.model.xmlrpc.messages.MultiCallRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.MultiCallResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Before running these tests, you need to set the VM argument -Dkoji.hubUrl.
 * These tests are ignored by default. They depend on the external Koji server content.
 */
public class ExternalKojiMultiCallFaultTest
{
    private KojiClient client;

    @Before
    public void setUp() throws KojiClientException
    {
        String hubUrl = System.getProperty( "koji.hubUrl" );
        assumeNotNull( hubUrl );
        KojiConfig config = new SimpleKojiConfigBuilder().withKojiURL( hubUrl ).build();
        client = new KojiClient( config, null, Executors.newFixedThreadPool( 5 ) );
    }

    @Test
    public void testKojiMultiCallFault()
    {
        String method = "test";

        MultiCallRequest multiCallRequest = new MultiCallRequest();

        List<KojiMultiCallObj> multiCallObjs = new ArrayList<>();

        KojiMultiCallObj multiCallObj = new KojiMultiCallObj();

        multiCallObj.setMethodName( method );
        multiCallObj.setParams( Collections.emptyList() );

        multiCallObjs.add( multiCallObj );

        multiCallRequest.setMultiCallObjs( multiCallObjs );

        MultiCallResponse response = client.multiCall( multiCallRequest, null );

        List<KojiMultiCallValueObj> valueObjs = response.getValueObjs();

        assertEquals( 1, valueObjs.size() );

        KojiMultiCallValueObj obj = valueObjs.get( 0 );

        KojiMultiCallFault fault = obj.getFault();

        assertNotNull( fault );

        assertEquals( Integer.valueOf( 1000 ), fault.getFaultCode() );

        assertEquals( "Invalid method: " + method, fault.getFaultString() );

        assertEquals( 6, fault.getTraceback().size() );

        List<String> traceback = Arrays.asList( "Traceback (most recent call last):\n",
                "  File \"/usr/share/koji-hub/kojixmlrpc.py\", line 325, in multiCall\n    result = self._dispatch(call['methodName'], call['params'])\n",
                "  File \"/usr/share/koji-hub/kojixmlrpc.py\", line 292, in _dispatch\n    func = self._get_handler(method)\n",
                "  File \"/usr/share/koji-hub/kojixmlrpc.py\", line 206, in _get_handler\n    return self.handlers.get(name)\n",
                "  File \"/usr/share/koji-hub/kojixmlrpc.py\", line 175, in get\n    raise koji.GenericError(\"Invalid method: %s\" % name)\n",
                "GenericError: Invalid method: test\n" );

        assertEquals( traceback, fault.getTraceback() );
    }
}
