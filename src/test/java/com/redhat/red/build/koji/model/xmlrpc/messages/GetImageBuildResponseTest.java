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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiImageBuildInfo;

public class GetImageBuildResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        verifyVsCapturedMessage( GetImageBuildResponse.class, "getImageBuild-response.xml" );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiImageBuildInfo info = new KojiImageBuildInfo();
        info.setBuildId( 564910 );

        GetImageBuildResponse parsed = roundTrip( GetImageBuildResponse.class, new GetImageBuildResponse( info ) );

        assertThat( parsed.getImageBuildInfo(), equalTo( info ) );
    }
}
