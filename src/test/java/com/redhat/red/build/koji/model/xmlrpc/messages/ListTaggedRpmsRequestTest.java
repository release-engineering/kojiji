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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ListTaggedRpmsRequestTest
                extends AbstractKojiMessageTest
{
    private static final String TAG = "rhmap-4.7-rhel-7-candidate";

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        verifyVsCapturedMessage( ListTaggedRpmsRequest.class, "listTaggedRPMS-request.xml" );
    }

    @Test
    public void roundTrip() throws Exception
    {
        ListTaggedRpmsRequest parsed = roundTrip( ListTaggedRpmsRequest.class, new ListTaggedRpmsRequest( TAG ) );

        assertThat( parsed.getTag().getName(), equalTo( TAG ) );
    }
}
