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

import com.redhat.red.build.koji.model.xmlrpc.KojiTagQuery;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jdcasey on 5/11/16.
 */
public class ListTagsRequestTest
                extends AbstractKojiMessageTest
{
    private static int buildId = 422953;

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        ListTagsRequest parsed = parseCapturedMessage( ListTagsRequest.class, "listTags-byBuildId-request.xml" );
        assertThat( parsed.getQuery().getBuildId().getId(), equalTo( buildId ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        ListTagsRequest parsed = roundTrip( ListTagsRequest.class, new ListTagsRequest( new KojiTagQuery( buildId ) ) );
        assertThat( parsed.getQuery().getBuildId().getId(), equalTo( buildId ) );
    }

    @Test
    public void renderXML() throws Exception
    {
        String xml = rwxMapper.render( new ListTagsRequest( new KojiTagQuery( buildId ) ) );
        System.out.println( xml );
    }
}
