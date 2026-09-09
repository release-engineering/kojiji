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

import com.redhat.red.build.koji.model.xmlrpc.KojiTaskInfo;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetTaskChildrenResponseTest
                extends AbstractKojiMessageTest
{
    private static final int TASK_ID = 12345678;

    private static final int TAG_BUILD_TASK_ID = 34567891;

    private static final int VM_EXEC_TASK_ID = 23456789;

    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        GetTaskChildrenResponse response = parseCapturedMessage( GetTaskChildrenResponse.class, "getTaskChildren-winbuild-response.xml" );
        List<KojiTaskInfo> children = response.getChildren();

        assertEquals( 2, children.size() );

        KojiTaskInfo tagBuild = children.get( 0 );
        assertEquals( "tagBuild", tagBuild.getMethod() );
        assertEquals( TAG_BUILD_TASK_ID, tagBuild.getTaskId() );
        assertThat( tagBuild.getParentTaskId(), equalTo( TASK_ID ) );
        assertThat( tagBuild.getRequest(), notNullValue() );

        KojiTaskInfo vmExec = children.get( 1 );
        assertEquals( "vmExec", vmExec.getMethod() );
        assertEquals( VM_EXEC_TASK_ID, vmExec.getTaskId() );
        assertThat( vmExec.getParentTaskId(), equalTo( TASK_ID ) );
        assertThat( vmExec.getRequest(), notNullValue() );
    }

    @Test
    public void roundTrip() throws Exception
    {
        KojiTaskInfo taskInfo = new KojiTaskInfo();
        taskInfo.setTaskId( 101 );
        taskInfo.setArch( "x86" );
        taskInfo.setMethod( "vmExec" );

        GetTaskChildrenResponse inst = new GetTaskChildrenResponse();
        inst.setChildren( List.of( taskInfo ) );

        GetTaskChildrenResponse parsed = roundTrip( GetTaskChildrenResponse.class, inst );

        assertThat( parsed.getChildren().get( 0 ).getTaskId(), equalTo( taskInfo.getTaskId() ) );
        assertThat( parsed.getChildren().get( 0 ).getArch(), equalTo( taskInfo.getArch() ) );
        assertThat( parsed.getChildren().get( 0 ).getMethod(), equalTo( taskInfo.getMethod() ) );
    }
}
