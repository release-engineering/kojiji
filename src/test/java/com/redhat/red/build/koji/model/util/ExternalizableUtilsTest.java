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
package com.redhat.red.build.koji.model.util;

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiChecksumType;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTaskInfo;
import com.redhat.red.build.koji.model.xmlrpc.messages.AbstractKojiMessageTest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetArchiveResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetBuildResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetRpmResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetTaskResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.TagResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.redhat.red.build.koji.model.xmlrpc.KojiBtype.maven;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ExternalizableUtilsTest
    extends AbstractKojiMessageTest
{
    @Test
    public void testExternalizeKojiArchiveInfo()
            throws Exception
    {
        GetArchiveResponse parsed = parseCapturedMessage( GetArchiveResponse.class, "getArchive-response.xml" );
        KojiArchiveInfo o1 = parsed.getArchiveInfo();
        KojiArchiveInfo o2 = new KojiArchiveInfo();

        try ( ByteArrayOutputStream bout = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream( bout ) )
        {
            o1.writeExternal( out );

            out.close();

            try ( ByteArrayInputStream bin = new ByteArrayInputStream( bout.toByteArray() ); ObjectInputStream in = new ObjectInputStream( bin ) )
            {
                o2.readExternal( in );
            }
        }

        assertThat( o2.getArtifactId(), equalTo( "netty-all" ) );
        assertThat( o2.getBuildType(), equalTo( maven ) );
        assertThat( o2.getBuildTypeId(), equalTo( 2 ) );
        assertThat( o2.getBuildId(), equalTo( 558964 ) );
        assertThat( o2.getBuildrootId(), equalTo( null ) );
        assertThat( o2.getChecksum(), equalTo( "a04e5b625b09efcf1af859f365f44e60" ) );
        assertThat( o2.getChecksumType(), equalTo( KojiChecksumType.md5 ) );
        assertThat( o2.getExtra(), equalTo( null ) );
        assertThat( o2.getFilename(), equalTo( "netty-all-4.1.9.Final-redhat-1.pom" ) );
        assertThat( o2.getGroupId(), equalTo( "io.netty" ) );
        assertThat( o2.getArchiveId(), equalTo( 1907538 ) );
        assertThat( o2.getMetadataOnly(), equalTo( false ) );
        assertThat( o2.getSize(), equalTo( 21110 ) );
        assertThat( o2.getTypeDescription(), equalTo( "Maven Project Object Management file" ) );
        assertThat( o2.getTypeExtensions(), equalTo(  "pom" ) );
        assertThat( o2.getTypeId(), equalTo( 3 ) );
        assertThat( o2.getTypeName(), equalTo( "pom" ) );
        assertThat( o2.getVersion(), equalTo( "4.1.9.Final-redhat-1" ) );
    }

    @Test
    public void testExternalizeKojiBuildInfo()
            throws Exception
    {
        GetBuildResponse parsed = parseCapturedMessage( GetBuildResponse.class, "getBuild-response.xml" );
        KojiBuildInfo o1 = parsed.getBuildInfo();
        KojiBuildInfo o2 = new KojiBuildInfo();

        try ( ByteArrayOutputStream bout = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream( bout ) )
        {
            o1.writeExternal( out );

            out.close();

            try ( ByteArrayInputStream bin = new ByteArrayInputStream( bout.toByteArray() ); ObjectInputStream in = new ObjectInputStream( bin ) )
            {
                o2.readExternal( in );
            }
        }

        assertThat( o2.getNvr(), equalTo( "org.dashbuilder-dashbuilder-parent-metadata-0.4.0.Final" ) );
    }

    @Test
    public void testExternalizeKojiTagInfo()
            throws Exception
    {
        TagResponse parsed = parseCapturedMessage( TagResponse.class, "getTag-response.xml" );
        KojiTagInfo o1 = parsed.getTagInfo();
        KojiTagInfo o2 = new KojiTagInfo();

        try ( ByteArrayOutputStream bout = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream( bout ) )
        {
            o1.writeExternal( out );

            out.close();

            try ( ByteArrayInputStream bin = new ByteArrayInputStream( bout.toByteArray() ); ObjectInputStream in = new ObjectInputStream( bin ) )
            {
                o2.readExternal( in );
            }
        }

        assertThat( o2.getName(), equalTo( "test-tag" ) );
        assertThat( o2.getArches().toString(), equalTo( Arrays.asList( "x86_64", "i386" ).toString() ) );
    }

    @Test
    public void testExternalizeKojiTaskInfo()
            throws Exception
    {
        GetTaskResponse response = parseCapturedMessage( GetTaskResponse.class, "getTaskInfo-response.xml" );
        KojiTaskInfo o1 = response.getTaskInfo();
        KojiTaskInfo o2 = new KojiTaskInfo();

        try ( ByteArrayOutputStream bout = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream( bout ) )
        {
            o1.writeExternal( out );

            out.close();

            try ( ByteArrayInputStream bin = new ByteArrayInputStream( bout.toByteArray() ); ObjectInputStream in = new ObjectInputStream( bin ) )
            {
                o2.readExternal( in );
            }
        }

        assertEquals( 0.20000000000000001D, o2.getWeight(), 0D );
        assertEquals( 10211252, o2.getTaskId() );
        assertEquals( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2015-12-09 01:12:25" ), o2.getCreateTime() );

        List<Object> request = o2.getRequest();

        assertEquals( "git://g.a.e.b.r.c/apache/cxf.git#154e3b7969fcf622aba3fb494b1b11f9215a9173", request.get( 0 ));
        assertEquals( "jb-eap-7.0-rhel-7-maven-candidate", request.get( 1 ) );

        Object obj = request.get( 2 );

        assertTrue( obj instanceof Map );

        Map<?, ?> m = (Map<?, ?>) obj;
        List<?> goals = (List<?>) m.get( "goals" );

        assertEquals( "install", goals.get( 0 ) );
        assertEquals( "javadoc:aggregate-jar", goals.get( 1 ) );
    }

    @Test
    public void testExternalizeKojiRpmInfo()
            throws Exception
    {
        GetRpmResponse response = parseCapturedMessage( GetRpmResponse.class, "getRPM-response.xml" );
        KojiRpmInfo o1 = response.getRpmInfo();
        KojiRpmInfo o2 = new KojiRpmInfo();

        try ( ByteArrayOutputStream bout = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream( bout ) )
        {
            o1.writeExternal( out );

            out.close();

            try ( ByteArrayInputStream bin = new ByteArrayInputStream( bout.toByteArray() ); ObjectInputStream in = new ObjectInputStream( bin ) )
            {
                o2.readExternal( in );
            }
        }

        assertEquals( Integer.valueOf( 834166 ), o2.getBuildId() );
        assertEquals( "rhmap-fh-openshift-templates", o2.getName() );
        assertNull( o2.getExtra() );
        assertEquals( "src", o2.getArch() );
        assertEquals( Long.valueOf( 1548265387L * 1000L ), Long.valueOf( o2.getBuildtime().getTime() ) );
        assertEquals( "7b6b83eb9f34747e8ddc782dbd8a186e", o2.getPayloadhash() );
        assertNull( o2.getEpoch() );
        assertEquals( "4.7.4", o2.getVersion() );
        assertEquals( Boolean.FALSE, o2.getMetadataOnly() );
        assertEquals( Integer.valueOf( 0 ), o2.getExternalRepoId() );
        assertEquals( "1.el7", o2.getRelease() );
        assertEquals( Integer.valueOf( 4695526 ), o2.getBuildrootId() );
        assertEquals( Integer.valueOf( 6719757 ), o2.getId() );
        assertEquals( "INTERNAL", o2.getExternalRepoName() );
        assertEquals( Long.valueOf( 973320L ), Long.valueOf( o2.getSize() ) );
    }
}
