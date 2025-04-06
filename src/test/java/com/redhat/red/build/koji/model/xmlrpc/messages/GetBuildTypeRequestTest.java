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
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;

public class GetBuildTypeRequestTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        verifyVsCapturedMessage( GetBuildTypeRequest.class, "getBuildType-request.xml" );
    }

    @Test
    public void roundTrip() throws Exception
    {
        GetBuildTypeRequest parsed =
                        roundTrip( GetBuildTypeRequest.class, new GetBuildTypeRequest( new KojiIdOrName( 506045 ) ) );
        assertNotNull( parsed );

        assertThat( parsed.getBuildIdOrName().getId(), equalTo( 506045 ) );
    }

    @Test
    public void renderXML() throws Exception
    {
        String xml = rwxMapper.render( new GetBuildTypeRequest( 506045 ) );
        System.out.println( xml );
    }
}
