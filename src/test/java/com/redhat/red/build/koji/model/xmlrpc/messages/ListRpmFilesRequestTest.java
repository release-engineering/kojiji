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

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmFilesQuery;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListRpmFilesRequestTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        verifyVsCapturedMessage( ListRpmFilesRequest.class, "listRPMFiles-request.xml" );
    }

    @Test
    public void roundTrip() throws Exception
    {
        int id = 6719757;
        KojiRpmFilesQuery query = null;
        ListRpmFilesRequest req = new ListRpmFilesRequest( id, query );

        ListRpmFilesRequest parsed = roundTrip( ListRpmFilesRequest.class, req );
        assertThat( parsed.getRpmId(), equalTo( id ) );
        assertThat( parsed.getQuery(), equalTo( null ) );
    }
}
