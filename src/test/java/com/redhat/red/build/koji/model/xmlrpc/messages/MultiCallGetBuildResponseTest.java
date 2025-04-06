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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallValueObj;
import com.redhat.red.build.koji.model.xmlrpc.KojiWinBuildInfo;
import org.commonjava.rwx.core.Registry;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class MultiCallGetBuildResponseTest
                extends AbstractKojiMessageTest
{
    // Zero build found
    @Test
    public void verifyVsCapturedHttp_0() throws Exception
    {
        MultiCallResponse parsed = parseCapturedMessage( MultiCallResponse.class, "multicall-getBuild-response-0.xml" );

        List<KojiMultiCallValueObj> valueObjs = parsed.getValueObjs();

        assertEquals( valueObjs.size(), 2 );

        // check #1 object
        KojiMultiCallValueObj value = valueObjs.get( 0 );
        Object data = value.getData();
        assertTrue( data == null );

        // check #2 object
        value = valueObjs.get( 1 );
        assertTrue( value.getData() == null );
    }

    // One build found, the other not found
    @Test
    public void verifyVsCapturedHttp_1() throws Exception
    {
        MultiCallResponse parsed = parseCapturedMessage( MultiCallResponse.class, "multicall-getBuild-response-1.xml" );

        List<KojiMultiCallValueObj> valueObjs = parsed.getValueObjs();

        assertEquals( valueObjs.size(), 2 );

        // check #1 object
        KojiMultiCallValueObj value = valueObjs.get( 0 );
        Object data = value.getData();
        assertTrue( data instanceof Map );
        KojiBuildInfo kojiBuildInfo = Registry.getInstance().parseAs( data, KojiBuildInfo.class );
        assertEquals( 513598, kojiBuildInfo.getId() );
        assertEquals( "org.dashbuilder-dashbuilder-parent-metadata", kojiBuildInfo.getName() );

        // check #2 object
        value = valueObjs.get( 1 );
        assertTrue( value.getData() == null );
    }

    // All builds found
    @Test
    public void verifyVsCapturedHttp_2() throws Exception
    {
        MultiCallResponse parsed = parseCapturedMessage( MultiCallResponse.class, "multicall-getBuild-response-2.xml" );

        List<KojiMultiCallValueObj> valueObjs = parsed.getValueObjs();

        assertEquals( valueObjs.size(), 2 );

        // check #1 object
        KojiMultiCallValueObj value = valueObjs.get( 0 );
        Object data = value.getData();
        assertTrue( data instanceof Map );

        KojiBuildInfo kojiBuildInfo = Registry.getInstance().parseAs( data, KojiBuildInfo.class );
        assertEquals( 513598, kojiBuildInfo.getId() );
        assertEquals( "org.dashbuilder-dashbuilder-parent-metadata", kojiBuildInfo.getName() );

        // check #2 object
        value = valueObjs.get( 1 );
        kojiBuildInfo = Registry.getInstance().parseAs( value.getData(), KojiBuildInfo.class );
        assertEquals( 513599, kojiBuildInfo.getId() );
        assertEquals( "org.kie-kie-parent-metadata", kojiBuildInfo.getName() );

    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiWinBuildInfo info = new KojiWinBuildInfo();
        info.setBuildId( 560936 );
        info.setPlatform( "w2k8-x64" );

        GetWinBuildResponse parsed = roundTrip( GetWinBuildResponse.class, new GetWinBuildResponse( info ) );

        assertThat( parsed.getWinBuildInfo(), equalTo( info ) );
    }
}
