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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmInfo;

import static junit.framework.TestCase.assertEquals;

import java.util.Arrays;
import java.util.List;

public class RpmBuildListResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        RpmBuildListResponse response = parseCapturedMessage( RpmBuildListResponse.class, "getLatestRPMS-response.xml" );

        List<KojiRpmInfo> rpms = response.getRpmBuildList().getRpms();
        List<KojiBuildInfo> builds = response.getRpmBuildList().getBuilds();

        assertEquals( 2, rpms.size() );
        assertEquals( 1, builds.size() );

        KojiRpmInfo rpm1 = rpms.get( 0 );
        KojiRpmInfo rpm2 = rpms.get( 1 );
        KojiBuildInfo build1 = builds.get( 0 );

        assertEquals( Integer.valueOf( 6777603 ), rpm1.getId() );
        assertEquals( Integer.valueOf( 6777602 ), rpm2.getId() );
        assertEquals( 848604, build1.getId() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiRpmInfo a1 = new KojiRpmInfo( 6777603, 848604, "rhmap-fh-openshift-templates", "4.7.4", "2.el7", "noarch" );
        KojiRpmInfo b1 = new KojiRpmInfo( 6777602, 848604, "rhmap-fh-openshift-templates", "4.7.4", "2.el7", "noarch" );
        List<Object> one = Arrays.asList( a1, b1 );

        KojiBuildInfo a2 = new KojiBuildInfo( 848604, 59108, "rhmap-fh-openshift-templates", "4.7.4", "2.el7" );
        List<Object> two = Arrays.asList( a2 );

        List<List<Object>> objs = Arrays.asList( one, two );

        // FIXME: Fix this test
        RpmBuildListResponse parsed = roundTrip( RpmBuildListResponse.class, new RpmBuildListResponse( objs ) );

        List<KojiRpmInfo> rpms = parsed.getRpmBuildList().getRpms();
        List<KojiBuildInfo> builds = parsed.getRpmBuildList().getBuilds();

        assertEquals( 2, rpms.size() );
        assertEquals( 1, builds.size() );

        KojiRpmInfo rpm1 = rpms.get( 0 );
        KojiRpmInfo rpm2 = rpms.get( 1 );
        KojiBuildInfo build1 = builds.get( 0 );

        assertEquals( Integer.valueOf( 6777603 ), rpm1.getId() );
        assertEquals( Integer.valueOf( 6777602 ), rpm2.getId() );
        assertEquals( 848604, build1.getId() );
    }
}
