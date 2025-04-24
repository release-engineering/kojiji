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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildTypeInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiMavenBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmBuildInfo;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetBuildTypeResponseMultiTypesTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetBuildTypeResponse parsed = parseCapturedMessage( GetBuildTypeResponse.class, "getBuildType-response-multiTypes.xml" );
        GetBuildTypeResponse expected = getInstance();

        assertEquals( expected.getBuildTypeInfo(), parsed.getBuildTypeInfo() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        GetBuildTypeResponse inst = getInstance();
        GetBuildTypeResponse parsed = roundTrip( GetBuildTypeResponse.class, inst );

        assertThat( parsed.getBuildTypeInfo(), equalTo( inst.getBuildTypeInfo() ) );
    }

    private GetBuildTypeResponse getInstance()
    {
        KojiBuildTypeInfo info = new KojiBuildTypeInfo();

        KojiMavenBuildInfo mInfo = new KojiMavenBuildInfo();
        mInfo.setGroupId( "artemis-native-linux" );
        mInfo.setArtifactId( "artemis-native-linux-repolib" );
        mInfo.setVersion( "2.3.0.amq_710003-1.redhat_1.el6" );
        mInfo.setBuildId( 610867 );

        info.setMaven( mInfo );

        KojiRpmBuildInfo rpm = new KojiRpmBuildInfo();
        info.setRpm( rpm );

        return new GetBuildTypeResponse( info );
    }
}
