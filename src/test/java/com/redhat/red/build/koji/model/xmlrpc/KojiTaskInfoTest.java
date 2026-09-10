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
package com.redhat.red.build.koji.model.xmlrpc;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class KojiTaskInfoTest
{
    private static final String SCM_URL = "git://example.com/foo.git#0abcdef";

    private static final String TARGET = "foo-candidate";

    @Test
    public void testAsBuildRequest()
    {
        KojiTaskInfo taskInfo = new KojiTaskInfo();
        taskInfo.setMethod( "maven" );
        taskInfo.setRequest( List.of( SCM_URL, TARGET, Map.of() ) );
        KojiBuildRequest buildRequest = taskInfo.asBuildRequest();
        assertThat( buildRequest, instanceOf( KojiMavenBuildRequest.class ) );
        assertThat( buildRequest.getSource(), equalTo( SCM_URL ) );
        assertThat( buildRequest.getTarget().getName(), equalTo( TARGET ) );
    }

    @Test( expected = IllegalStateException.class )
    public void testAsBuildRequestWithoutRequestThrows()
    {
        KojiTaskInfo taskInfo = new KojiTaskInfo();
        taskInfo.setMethod( "build" );
        taskInfo.asBuildRequest();
    }

    @Test( expected = IllegalStateException.class )
    public void testAsBuildRequestWithoutMethodThrows()
    {
        KojiTaskInfo taskInfo = new KojiTaskInfo();
        taskInfo.setRequest( List.of( SCM_URL, TARGET ) );
        taskInfo.asBuildRequest();
    }
}
