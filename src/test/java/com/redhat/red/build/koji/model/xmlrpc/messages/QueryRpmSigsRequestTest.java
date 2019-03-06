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

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmSigsQuery;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class QueryRpmSigsRequestTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        verifyVsCapturedMessage( QueryRpmSigsRequest.class, "queryRPMSigs-request.xml" );
    }

    @Test
    public void roundTrip() throws Exception
    {
        Integer rpmId = Integer.valueOf( 6719757 );
        KojiRpmSigsQuery query = new KojiRpmSigsQuery();

        query.setRpmId( rpmId );

        QueryRpmSigsRequest req = new QueryRpmSigsRequest( query );

        QueryRpmSigsRequest parsed = roundTrip( QueryRpmSigsRequest.class, req );
        assertThat( parsed.getQuery().getRpmId(), equalTo( rpmId ) );
        assertThat( parsed.getQuery().getSigkey(), equalTo( null ) );
        assertThat( parsed.getQuery().getQueryOpts(), equalTo( null ) );
    }
}
