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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jdcasey on 5/11/16.
 */
public class BuildListResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        BuildListResponse parsed = parseCapturedMessage( BuildListResponse.class, "listBuilds-byGAV-response.xml" );
        assertThat( parsed.getBuilds() != null, equalTo( true ) );
        assertThat( parsed.getBuilds().size(), equalTo( 2 ) );

        KojiBuildInfo build_0 = parsed.getBuilds().get( 0 );
        KojiBuildInfo build_1 = parsed.getBuilds().get( 1 );

        assertEquals( build_0.getName(), "commons-io-commons-io" );
        assertEquals( build_0.getId(), 422953 );
        assertEquals( build_0.getPackageId(), 12630 );
        assertEquals( build_0.getCreationTime(),
                      new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2015-02-24 16:03:34" ) );

        assertThat( build_1, notNullValue() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        BuildListResponse inst = getInstance();
        List<KojiBuildInfo> builds = inst.getBuilds();
        BuildListResponse parsed = roundTrip( BuildListResponse.class, inst );

        assertThat( parsed.getBuilds().containsAll( builds ), equalTo( true ) );
    }

    private BuildListResponse getInstance()
    {
        KojiBuildInfo one = new KojiBuildInfo( 422953, 12630, "commons-io-commons-io", "2.4.0.redhat_1", "1" );
        KojiBuildInfo two = new KojiBuildInfo( 447248, 12630, "commons-io-commons-io", "2.4.0.redhat_1", "2" );
        return new BuildListResponse( Arrays.asList( one, two ) );
    }

    @Test
    public void parseNilResult() throws Exception
    {
        BuildListResponse parsed = parseCapturedMessage( BuildListResponse.class, "listBuilds-byGAV-responseNIL.xml" );
        assertThat( parsed.getBuilds() == null || parsed.getBuilds().isEmpty(), equalTo( true ) );
    }
}
