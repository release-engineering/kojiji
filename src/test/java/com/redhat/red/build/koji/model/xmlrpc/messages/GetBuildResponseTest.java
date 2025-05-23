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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetBuildResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void roundTrip() throws Exception
    {
        KojiBuildInfo info = new KojiBuildInfo();
        info.setName( "org.dashbuilder-dashbuilder-parent-metadata" );
        info.setVersion( "0.4.0.Final" );
        info.setRelease( "1" );
        info.setVolumeName( "DEFAULT" );

        GetBuildResponse parsed = roundTrip( GetBuildResponse.class, new GetBuildResponse( info ) );

        assertThat( parsed.getBuildInfo().getVolumeName(), equalTo( "DEFAULT" ) );
    }
}
