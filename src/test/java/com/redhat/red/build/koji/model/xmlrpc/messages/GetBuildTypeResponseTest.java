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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildTypeInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiMavenBuildInfo;

public class GetBuildTypeResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetBuildTypeResponse parsed = parseCapturedMessage( GetBuildTypeResponse.class, "getBuildType-response.xml" );
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
        mInfo.setGroupId( "org.jboss.ws.cxf" );
        mInfo.setArtifactId( "jbossws-cxf" );
        mInfo.setVersion( "5.1.5.Final-redhat-1" );
        mInfo.setBuildId( 506045 );

        info.setMaven( mInfo );
        return new GetBuildTypeResponse( info );
    }
}
