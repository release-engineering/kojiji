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

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmInfo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class RpmListResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        RpmListResponse parsed = parseCapturedMessage( RpmListResponse.class, "listRPMs-response.xml" );
        assertThat( parsed.getRpms() != null, equalTo( true ) );
        assertThat( parsed.getRpms().size(), equalTo( 2 ) );

        KojiRpmInfo build_0 = parsed.getRpms().get( 0 );
        KojiRpmInfo build_1 = parsed.getRpms().get( 1 );
        assertEquals( "rhmap-fh-openshift-templates", build_0.getName() );
        assertEquals( Integer.valueOf( 6719758 ), build_0.getId() );
        assertEquals( Integer.valueOf ( 834166 ), build_0.getBuildId() );
        assertEquals( new Date( 1548265391L * 1000L ), build_0.getBuildtime() );
        assertThat( build_1, notNullValue() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        RpmListResponse inst = getInstance();
        List<KojiRpmInfo> builds = inst.getRpms();
        RpmListResponse parsed = roundTrip( RpmListResponse.class, inst );

        assertThat( parsed.getRpms().containsAll( builds ), equalTo( true ) );
    }

    private RpmListResponse getInstance()
    {
        KojiRpmInfo one = new KojiRpmInfo( 6777603, 848604, "rhmap-fh-openshift-templates", "4.7.4", "2.el7", "noarch" );
        KojiRpmInfo two = new KojiRpmInfo( 6777602, 848604, "rhmap-fh-openshift-templates", "4.7.4", "2.el7", "noarch" );

        return new RpmListResponse( Arrays.asList( one, two ) );
    }

    @Test
    public void parseEmptyResult() throws Exception
    {
        RpmListResponse parsed = parseCapturedMessage( RpmListResponse.class, "listRPMs-responseEmpty.xml" );
        assertThat( parsed.getRpms() == null || parsed.getRpms().isEmpty(), equalTo( true ) );
    }
}
