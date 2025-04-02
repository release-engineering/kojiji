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
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.commonjava.rwx.core.Registry;
import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallFault;

public class MultiCallFaultResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        MultiCallResponse parsed = parseCapturedMessage( MultiCallResponse.class, "multicall-fault-response.xml" );
        MultiCallResponse expected = getInstance();

        assertEquals( expected.getValueObjs().get( 0 ).getFault().getFaultCode(), parsed.getValueObjs().get( 0 ).getFault().getFaultCode() );
        assertEquals( expected.getValueObjs().get( 0 ).getFault().getFaultString(), parsed.getValueObjs().get( 0 ).getFault().getFaultString() );
        assertEquals( expected.getValueObjs().get( 0 ).getFault().getTraceback(), parsed.getValueObjs().get( 0 ).getFault().getTraceback() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        MultiCallResponse inst = getInstance();
        MultiCallResponse parsed = roundTrip( MultiCallResponse.class, inst );

        assertThat( parsed.getValueObjs().get( 0 ).getFault().getFaultCode(), equalTo( inst.getValueObjs().get( 0 ).getFault().getFaultCode() ) );
        assertThat( parsed.getValueObjs().get( 0 ).getFault().getFaultString(), equalTo( inst.getValueObjs().get( 0 ).getFault().getFaultString() ) );
        assertThat( parsed.getValueObjs().get( 0 ).getFault().getTraceback(), equalTo( inst.getValueObjs().get( 0 ).getFault().getTraceback() ) );
    }

    private MultiCallResponse getInstance()
    {
        final Integer faultCode = 1000;

        final String method = "test";

        final String  faultString  = "Invalid method: " + method;

        final List<String> traceback = Arrays.asList( "Traceback (most recent call last):\n",
                "  File \"/usr/share/koji-hub/kojixmlrpc.py\", line 325, in multiCall\n    result = self._dispatch(call['methodName'], call['params'])\n",
                "  File \"/usr/share/koji-hub/kojixmlrpc.py\", line 292, in _dispatch\n    func = self._get_handler(method)\n",
                "  File \"/usr/share/koji-hub/kojixmlrpc.py\", line 206, in _get_handler\n    return self.handlers.get(name)\n",
                "  File \"/usr/share/koji-hub/kojixmlrpc.py\", line 175, in get\n    raise koji.GenericError(\"Invalid method: %s\" % name)\n",
                "GenericError: Invalid method: test\n" );

        KojiMultiCallFault fault = new KojiMultiCallFault();

        fault.setFaultCode( faultCode );

        fault.setFaultString( faultString );

        fault.setTraceback( traceback );

        MultiCallResponse response = new MultiCallResponse();

        Registry registry = Registry.getInstance();

        response.setResponse( Collections.singletonList( registry.renderTo( fault ) ) );

        return response;
    }
}
