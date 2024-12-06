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

import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 5/11/16.
 */
public class ListTagsResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        ListTagsResponse parsed = parseCapturedMessage( ListTagsResponse.class, "listTags-byBuildId-response.xml" );
        assertListTagsResponse( parsed );
    }

    @Test
    public void roundTrip() throws Exception
    {
        ListTagsResponse parsed = roundTrip( ListTagsResponse.class, new ListTagsResponse( Arrays.asList( tag ) ) );
        assertListTagsResponse( parsed );
    }

    private static KojiTagInfo tag = new KojiTagInfo( 6951, "test-tag", null, null, null, false, false, false );

    private void assertListTagsResponse( ListTagsResponse parsed )
    {
        assertThat( parsed.getTags().size(), equalTo( 1 ) );
        assertThat( parsed.getTags().contains( tag ), equalTo( true ) );

    }

}
