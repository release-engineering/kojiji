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

import com.redhat.red.build.koji.model.xmlrpc.KojiTaskInfo;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GetTaskInfoResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetTaskResponse response = parseCapturedMessage( GetTaskResponse.class, "getTaskInfo-response.xml" );
        KojiTaskInfo taskInfo = response.getTaskInfo();

        assertEquals(0.20000000000000001D, taskInfo.getWeight());
        assertEquals( 10211252, taskInfo.getTaskId() );
        assertEquals( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( "2015-12-09 01:12:25" ),
                      taskInfo.getCreateTime() );

        // assert request field which is a list of objects with misc unknown types
        List<Object> reqeust = taskInfo.getRequest();
        assertEquals( "git://g.a.e.b.r.c/apache/cxf.git#154e3b7969fcf622aba3fb494b1b11f9215a9173", reqeust.get( 0 ));
        assertEquals( "jb-eap-7.0-rhel-7-maven-candidate", reqeust.get( 1 ) );

        Object obj = reqeust.get( 2 );
        assertTrue( obj instanceof Map );

        Map m = (Map) obj;
        List goals = (List) m.get( "goals" );

        assertEquals( "install", goals.get( 0 ) );
        assertEquals( "javadoc:aggregate-jar", goals.get( 1 ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiTaskInfo taskInfo = new KojiTaskInfo();
        taskInfo.setTaskId( 101 );
        taskInfo.setArch( "x86" );
        taskInfo.setMethod( "maven" );

        GetTaskResponse inst = new GetTaskResponse();
        inst.setTaskInfo( taskInfo );

        GetTaskResponse parsed = roundTrip( GetTaskResponse.class, inst );

        assertThat( parsed.getTaskInfo().getTaskId(), equalTo( taskInfo.getTaskId() ) );
        assertThat( parsed.getTaskInfo().getArch(), equalTo( taskInfo.getArch() ) );
        assertThat( parsed.getTaskInfo().getMethod(), equalTo( taskInfo.getMethod() ) );
        assertThat( parsed.getTaskInfo().getRequest(), equalTo( null ) );
    }
}
