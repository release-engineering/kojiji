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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildRequest;
import com.redhat.red.build.koji.model.xmlrpc.KojiMavenBuildRequest;
import com.redhat.red.build.koji.model.xmlrpc.KojiTaskRequest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class GetTaskRequestResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetTaskRequestResponse parsed =
                        parseCapturedMessage( GetTaskRequestResponse.class, "getTaskRequest-response.xml" );

        List<Object> taskRequestInfo = parsed.getTaskRequestInfo();

        assertEquals( "git+https://c.e.r.c/gerrit/dashbuilder.git#sync-6.4.x-2016.09.15", taskRequestInfo.get( 0 ) );
        assertEquals( "jb-bxms-6.3-candidate", taskRequestInfo.get( 1 ) );

        Map<String, Object> map = (Map) taskRequestInfo.get( 2 );
        Object obj = map.get( "jvm_options" );
        assertTrue( obj instanceof List );

        List<Object> jvm_options = (List) obj;
        assertEquals( 5, jvm_options.size() );
        assertEquals( "-Xms512m", jvm_options.get( 0 ) );
        assertEquals( "-Xmx3096m", jvm_options.get( 1 ) );
        assertEquals( "-XX:MaxPermSize=1024m", jvm_options.get( 2 ) );

        obj = map.get( "profiles" );
        assertTrue( obj instanceof List );
        assertTrue( ( (List) obj ).isEmpty() );

        obj = map.get( "deps" );
        assertTrue( obj instanceof List );

        obj = map.get( "properties" );
        assertTrue( obj instanceof Map );

        Map properties = (Map) obj;
        assertEquals( "0.4.0.Final", properties.get( "version.override" ) );

        // and so on...
    }

    @Test
    public void roundTrip() throws Exception
    {
        GetTaskRequestResponse inst = new GetTaskRequestResponse();
        List<Object> taskRequestInfo = new ArrayList<>();
        taskRequestInfo.add( "This" );
        taskRequestInfo.add( "is" );
        taskRequestInfo.add( "test" );
        taskRequestInfo.add( 101 );
        inst.setTaskRequestInfo( taskRequestInfo );

        GetTaskRequestResponse parsed = roundTrip( GetTaskRequestResponse.class, inst );

        assertThat( parsed.getTaskRequestInfo().size(), equalTo( inst.getTaskRequestInfo().size() ) );
    }

    @Test
    public void verifyVsCapturedHttp_asBuildRequest() throws Exception
    {
        final String source = "git+https://c.e.r.c/gerrit/dashbuilder.git#sync-6.4.x-2016.09.15";
        final String target = "jb-bxms-6.3-candidate";

        GetTaskRequestResponse parsed =
                        parseCapturedMessage( GetTaskRequestResponse.class, "getTaskRequest-response.xml" );

        KojiTaskRequest kojiTaskRequest = new KojiTaskRequest( parsed.getTaskRequestInfo() );

        KojiBuildRequest buildRequest = kojiTaskRequest.asBuildRequest();
        assertEquals( source, buildRequest.getSource());
        assertEquals( target, buildRequest.getTarget() );

        KojiMavenBuildRequest mavenBuildRequest = kojiTaskRequest.asMavenBuildRequest();
        assertEquals( source, mavenBuildRequest.getScmUrl() );
        List<String> options = mavenBuildRequest.getJvmOptions();
        assertTrue( options.contains( "-Xms512m" ) );

        //assertTrue( mavenBuildRequest.getDeps().contains( "org.uberfire-uberfire-extensions-0.8.0.Final-1" ) );
        boolean hasUberfire = false;
        for ( String s : mavenBuildRequest.getDeps() )
        {
            assertTrue( s != null );
            if ( s.contains( "org.uberfire-uberfire-extensions-0.8.0.Final-1" ))
            {
                hasUberfire = true;
            }
        }
        assertTrue( hasUberfire );

        assertTrue( mavenBuildRequest.getProfiles().isEmpty() );

        assertEquals( "0.4.0.Final", mavenBuildRequest.getProperties().get( "version.override" ));
    }
}
