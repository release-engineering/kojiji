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

import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jdcasey on 12/3/15.
 */
public class TagResponseTest
                extends AbstractKojiMessageTest
{

    private static final String TAG = "test-tag";

    private static List<String> arches = Arrays.asList( "x86_64", "i386" );

    private static List<String> singleArches = Collections.singletonList( "x86_64" );

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        TagResponse parsed = parseCapturedMessage( TagResponse.class, "getTag-response.xml" );
        assertTagResponse( parsed, arches );
    }

    @Test
    public void roundTrip() throws Exception
    {
        TagResponse parsed = roundTrip( TagResponse.class, newResponse( arches ) );
        assertTagResponse( parsed, arches );
    }

    @Test
    public void verifyVsCapturedHttpRequest_singleArch() throws Exception
    {
        TagResponse parsed = parseCapturedMessage( TagResponse.class, "getTag-response-singleArch.xml" );
        assertTagResponse( parsed, singleArches );
    }

    @Test
    public void roundTrip_singleArch() throws Exception
    {

        TagResponse parsed = roundTrip( TagResponse.class, newResponse( singleArches ) );
        assertTagResponse( parsed, singleArches );
    }

    private void assertTagResponse( TagResponse parsed, List<String> arches )
    {
        KojiTagInfo tagInfo = parsed.getTagInfo();
        assertNotNull( tagInfo );

        assertThat( tagInfo.getName(), equalTo( TAG ) );
        assertThat( tagInfo.getArches().toString(), equalTo( arches.toString() ) );
    }

    private TagResponse newResponse( List<String> arches )
    {
        return new TagResponse( new KojiTagInfo( 1001, "test-tag", "admin", 1, arches, true, true, true ) );
    }
}
