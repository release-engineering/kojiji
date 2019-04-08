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

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmInfo;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GetRpmResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetRpmResponse response = parseCapturedMessage( GetRpmResponse.class, "getRPM-response.xml" );
        KojiRpmInfo rpmBuildInfo = response.getRpmBuildInfo();

        assertEquals( Integer.valueOf ( 6719757 ), rpmBuildInfo.getId() );
        assertEquals( Integer.valueOf( 834166 ), rpmBuildInfo.getBuildId() );
        assertEquals( "rhmap-fh-openshift-templates", rpmBuildInfo.getName() );
        assertEquals( "4.7.4", rpmBuildInfo.getVersion() );
        assertEquals( "1.el7", rpmBuildInfo.getRelease() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiRpmInfo info = new KojiRpmInfo();
        info.setName( "rhmap-fh-openshift-templates" );
        info.setVersion( "4.7.4" );
        info.setRelease( "1.el7" );

        GetRpmResponse parsed = roundTrip( GetRpmResponse.class, new GetRpmResponse( info ) );

        assertThat( parsed.getRpmBuildInfo().getName(), equalTo( "rhmap-fh-openshift-templates" ) );
    }
}
