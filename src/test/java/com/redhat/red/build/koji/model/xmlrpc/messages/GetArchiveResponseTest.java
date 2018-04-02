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

import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiChecksumType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GetArchiveResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetArchiveResponse parsed =
                        parseCapturedMessage( GetArchiveResponse.class, "getArchive-response.xml" );

        KojiArchiveInfo archiveInfo = parsed.getArchiveInfo();

        assertThat( archiveInfo.getArtifactId(), equalTo( "netty-all" ) );
        assertThat( archiveInfo.getBuildType(), equalTo( "maven" ) );
        assertThat( archiveInfo.getBuildTypeId(), equalTo( 2 ) );
        assertThat( archiveInfo.getBuildId(), equalTo( 558964 ) );
        assertThat( archiveInfo.getBuildrootId(), equalTo( null ) );
        assertThat( archiveInfo.getChecksum(), equalTo( "a04e5b625b09efcf1af859f365f44e60" ) );
        assertThat( archiveInfo.getChecksumType(), equalTo( KojiChecksumType.md5 ) );
        assertThat( archiveInfo.getExtra(), equalTo( null ) );
        assertThat( archiveInfo.getFilename(), equalTo( "netty-all-4.1.9.Final-redhat-1.pom" ) );
        assertThat( archiveInfo.getGroupId(), equalTo( "io.netty" ) );
        assertThat( archiveInfo.getArchiveId(), equalTo( 1907538 ) );
        assertThat( archiveInfo.getMetadataOnly(), equalTo( false ) );
        assertThat( archiveInfo.getSize(), equalTo( 21110 ) );
        assertThat( archiveInfo.getTypeDescription(), equalTo( "Maven Project Object Management file" ) );
        assertThat( archiveInfo.getTypeExtensions(), equalTo(  "pom" ) );
        assertThat( archiveInfo.getTypeId(), equalTo( 3 ) );
        assertThat( archiveInfo.getTypeName(), equalTo( "pom" ) );
        assertThat( archiveInfo.getVersion(), equalTo( "4.1.9.Final-redhat-1" ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        GetArchiveResponse resp = new GetArchiveResponse();
        KojiArchiveInfo archiveInfo = new KojiArchiveInfo();
        archiveInfo.setArtifactId( "netty-all" );
        archiveInfo.setBuildType( "maven" );
        archiveInfo.setBuildTypeId( 2 );
        archiveInfo.setBuildId( 558964 );
        archiveInfo.setBuildrootId( null );
        archiveInfo.setChecksum( "a04e5b625b09efcf1af859f365f44e60" );
        archiveInfo.setChecksumType( KojiChecksumType.md5 );
        archiveInfo.setExtra( null );
        archiveInfo.setFilename( "netty-all-4.1.9.Final-redhat-1.pom" );
        archiveInfo.setGroupId( "io.netty" );
        archiveInfo.setArchiveId( 1907538 );
        archiveInfo.setMetadataOnly( false );
        archiveInfo.setSize( 21110 );
        archiveInfo.setTypeDescription( "Maven Project Object Management file" );
        archiveInfo.setTypeExtensions( "pom" );
        archiveInfo.setTypeId( 3 );
        archiveInfo.setTypeName( "pom" );
        archiveInfo.setVersion( "4.1.9.Final-redhat-1" );

        resp.setArchiveInfo( archiveInfo );

        GetArchiveResponse parsed = roundTrip( GetArchiveResponse.class, resp );

        assertThat( parsed.getArchiveInfo().getArtifactId(), equalTo( resp.getArchiveInfo().getArtifactId() ) );
        assertThat( parsed.getArchiveInfo().getBuildType(), equalTo( resp.getArchiveInfo().getBuildType() ) );
        assertThat( parsed.getArchiveInfo().getBuildTypeId(), equalTo( resp.getArchiveInfo().getBuildTypeId() ) );
        assertThat( parsed.getArchiveInfo().getBuildId(), equalTo( resp.getArchiveInfo().getBuildId() ) );
        assertThat( parsed.getArchiveInfo().getBuildrootId(), equalTo( resp.getArchiveInfo().getBuildrootId() ) );
        assertThat( parsed.getArchiveInfo().getChecksum(), equalTo( resp.getArchiveInfo().getChecksum() ) );
        assertThat( parsed.getArchiveInfo().getChecksumType(), equalTo( resp.getArchiveInfo().getChecksumType() ) );
        assertThat( parsed.getArchiveInfo().getExtra(), equalTo( resp.getArchiveInfo().getExtra() ) );
        assertThat( parsed.getArchiveInfo().getFilename(), equalTo( resp.getArchiveInfo().getFilename() ) );
        assertThat( parsed.getArchiveInfo().getGroupId(), equalTo( resp.getArchiveInfo().getGroupId() ) );
        assertThat( parsed.getArchiveInfo().getArchiveId(), equalTo( resp.getArchiveInfo().getArchiveId() ) );
        assertThat( parsed.getArchiveInfo().getMetadataOnly(), equalTo( resp.getArchiveInfo().getMetadataOnly() ) );
        assertThat( parsed.getArchiveInfo().getSize(), equalTo( resp.getArchiveInfo().getSize() ) );
        assertThat( parsed.getArchiveInfo().getTypeDescription(), equalTo( resp.getArchiveInfo().getTypeDescription() ) );
        assertThat( parsed.getArchiveInfo().getTypeExtensions(), equalTo( resp.getArchiveInfo().getTypeExtensions() ) );
        assertThat( parsed.getArchiveInfo().getTypeId(), equalTo( resp.getArchiveInfo().getTypeId() ) );
        assertThat( parsed.getArchiveInfo().getTypeName(), equalTo( resp.getArchiveInfo().getTypeName() ) );
        assertThat( parsed.getArchiveInfo().getVersion(), equalTo( resp.getArchiveInfo().getVersion() ) );
    }
}
