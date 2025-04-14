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
package com.redhat.red.build.koji.model.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiChecksumType;

public class KojiArchiveInfoTest
        extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip()
            throws VerificationException, IOException
    {
        KojiArchiveInfo info = new KojiArchiveInfo();
        info.setArchiveId( 1654342 );
        info.setTypeExtensions( "txt" );
        info.setFilename( "com.springsource.org.jaxen-1.1.1-license.txt" );
        info.setBuildId( 248078 );
        info.setTypeName( "txt" );
        info.setTypeId( 20 );
        info.setChecksum( "3b83ef96387f14655fc854ddc3c6bd57" );
        info.setChecksumType( KojiChecksumType.md5 );
        info.setTypeDescription( "Text file" );
        info.setMetadataOnly( false );
        info.setSize( 11358 );

        String json = mapper.writeValueAsString( info );
        System.out.println( json );

        KojiArchiveInfo out = mapper.readValue( json, KojiArchiveInfo.class );

        assertThat( out.toString(), equalTo( info.toString() ) );
    }
}
