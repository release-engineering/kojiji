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

import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetBuildRequestTest
                extends AbstractKojiMessageTest
{

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        verifyVsCapturedMessage( GetBuildByIdOrNameRequest.class, "getBuild-request.xml" );
    }

    @Test
    public void roundTrip() throws Exception
    {
        String buildName = "org.dashbuilder-dashbuilder-parent-metadata-0.4.0.Final";
        GetBuildByIdOrNameRequest parsed = roundTrip( GetBuildByIdOrNameRequest.class,
                                                      new GetBuildByIdOrNameRequest( new KojiIdOrName( buildName ) ) );
        assertThat( parsed.getBuildIdOrName().getName(), equalTo( buildName ) );
    }

}
