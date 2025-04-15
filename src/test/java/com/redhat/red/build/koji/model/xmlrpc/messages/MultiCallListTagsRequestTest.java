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

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MultiCallListTagsRequestTest
                extends AbstractKojiMessageTest
{

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        MultiCallRequest parsed = parseCapturedMessage( MultiCallRequest.class, "multicall-listTags-request.xml" );
        List<KojiMultiCallObj> multiCallObjs = parsed.getMultiCallObjs();

        KojiMultiCallObj obj = multiCallObjs.get( 0 );
        assertEquals( "listTags", obj.getMethodName() );
        assertEquals( 422953, obj.getParams().get( 0 ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        ;
    }

}
