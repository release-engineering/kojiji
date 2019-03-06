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

import com.redhat.red.build.koji.model.xmlrpc.KojiGetRpmHeadersParams;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

public class GetRpmHeadersRequestTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        verifyVsCapturedMessage( GetRpmHeadersRequest.class, "getRPMHeaders-request.xml" );
    }

    @Test
    public void roundTrip() throws Exception
    {
        Integer rpmId = Integer.valueOf( 6719757 );
        Integer taskId = null;
        String filepath = null;
        List<String> headers = Arrays.asList( "summary", "description" );

        KojiGetRpmHeadersParams params = new KojiGetRpmHeadersParams().withRpmId( rpmId ).withTaskId( taskId ).withFilepath( filepath ).withHeaders( headers );

        GetRpmHeadersRequest req = new GetRpmHeadersRequest( params );

        GetRpmHeadersRequest parsed = roundTrip( GetRpmHeadersRequest.class, req );
        KojiGetRpmHeadersParams parsedParams = parsed.getParams();

        assertNotNull( parsedParams );

        assertThat( parsedParams.getRpmId(), equalTo( rpmId ) );
        assertThat( parsedParams.getTaskId(), equalTo( null ) );
        assertThat( parsedParams.getFilepath(), equalTo( null ) );
        assertThat( parsedParams.getHeaders().size(), equalTo( 2 ) );
        assertThat( parsedParams.getHeaders().get( 0 ), equalTo( "summary" ) );
        assertThat( parsedParams.getHeaders().get( 1 ), equalTo( "description" ) );
    }
}
