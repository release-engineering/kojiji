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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildTypeQuery;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListBuildTypesRequestTest
                extends AbstractKojiMessageTest
{

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        verifyVsCapturedMessage( ListBuildTypesRequest.class, "listBuildTypes-request.xml" );

        verifyVsCapturedMessage( ListBuildTypesRequest.class, "listBuildTypes-with-query-request.xml" );
    }

    @Test
    public void roundTrip() throws Exception
    {
        ListBuildTypesRequest parsed = roundTrip( ListBuildTypesRequest.class, new ListBuildTypesRequest() );
        assertThat( parsed.getQuery(), equalTo( null ) );

        ListBuildTypesRequest req = new ListBuildTypesRequest();
        KojiBuildTypeQuery query = new KojiBuildTypeQuery().withName( "rpm" );
        req.setQuery( query );
        ListBuildTypesRequest parsed2 = roundTrip( ListBuildTypesRequest.class, req );
        assertThat( parsed2.getQuery().getName(), equalTo( "rpm" ) );
    }

    @Test
    public void renderXML() throws Exception
    {
        String xml = rwxMapper.render( new ListBuildTypesRequest() );
        System.out.println( xml );

        ListBuildTypesRequest req = new ListBuildTypesRequest();
        KojiBuildTypeQuery query = new KojiBuildTypeQuery().withName( "rpm" );
        req.setQuery( query );

        String xml2 = rwxMapper.render( req );
        System.out.println( xml2 );
    }
}
