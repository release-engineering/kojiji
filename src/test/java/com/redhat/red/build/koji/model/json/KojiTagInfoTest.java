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
package com.redhat.red.build.koji.model.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;

public class KojiTagInfoTest
        extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip()
            throws VerificationException, IOException
    {
        KojiTagInfo info = new KojiTagInfo();
        info.setId( 12345 );
        List<String> arches = new ArrayList<>(2);
        arches.add( "i386" );
        arches.add( "x86_64" );
        info.setArches( arches );
        info.setLocked( false );
        info.setMavenIncludeAll( false );
        info.setMavenSupport( false );
        info.setName( "foo" );
        info.setPermission( "bar" );
        info.setPermissionId( 0 );
        info.setUseKojiKeywords( false );

        String json = mapper.writeValueAsString( info );
        System.out.println( json );

        KojiTagInfo out = mapper.readValue( json, KojiTagInfo.class );

        assertThat( out.toString(), equalTo( info.toString() ) );
    }
}
