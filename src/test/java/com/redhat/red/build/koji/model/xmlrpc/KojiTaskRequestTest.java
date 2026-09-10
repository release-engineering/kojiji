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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class KojiTaskRequestTest
{
    private static final String SCM_URL = "git://example.com/foo.git#0abcdef";

    private static final String TARGET = "foo-candidate";

    private static final Integer TARGET_ID = 1;

    @Test
    public void testAsBuildRequestValid()
    {
        KojiBuildRequest buildRequest = new KojiTaskRequest( List.of( SCM_URL, TARGET, Map.of() ) ).asBuildRequest( KojiTaskMethod.build );
        assertThat( buildRequest.getSource(), equalTo( SCM_URL ) );
        assertThat( buildRequest.getTarget().getName(), equalTo( TARGET ) );
        KojiBuildRequest mavenBuildRequest = new KojiTaskRequest( List.of( SCM_URL, TARGET, Map.of( "profiles", List.of( "p1" ) ) ) ).asBuildRequest( KojiTaskMethod.maven );
        assertThat( mavenBuildRequest, instanceOf( KojiMavenBuildRequest.class ) );
        assertThat( mavenBuildRequest.getTarget().getName(), equalTo( TARGET ) );
        assertThat( ( (KojiMavenBuildRequest) mavenBuildRequest ).getProfiles(), equalTo( List.of( "p1" ) ) );
        KojiBuildRequest structTargetRequest = new KojiTaskRequest( List.of( SCM_URL, Map.of( "id", TARGET_ID ) ) ).asBuildRequest( KojiTaskMethod.build );
        assertThat( structTargetRequest.getTarget().getName(), nullValue() );
        assertThat( structTargetRequest.getTarget().getId(), equalTo( TARGET_ID ) );
    }

    @Test
    public void testAsBuildRequestInvalid()
    {
        assertThat( new KojiTaskRequest( List.of( SCM_URL, TARGET ) ).asBuildRequest( KojiTaskMethod.unknown ), nullValue() );
        KojiBuildRequest buildRequest = new KojiTaskRequest( List.of() ).asBuildRequest( KojiTaskMethod.build );
        assertThat( buildRequest.getSource(), nullValue() );
        assertThat( buildRequest.getTarget(), nullValue() );
    }

    @Test
    @SuppressWarnings( "deprecation" )
    public void testDeprecatedAsBuildRequest()
    {
        KojiBuildRequest buildRequest = new KojiTaskRequest( List.of( SCM_URL, TARGET, Map.of() ) ).asBuildRequest();
        assertThat( buildRequest.getSource(), equalTo( SCM_URL ) );
        assertThat( buildRequest.getTarget().getName(), equalTo( TARGET ) );
        assertThat( new KojiTaskRequest( List.of( SCM_URL, TARGET, Map.of() ) ).asBuildRequest( KojiTaskMethod.maven.name() ), instanceOf( KojiMavenBuildRequest.class ) );
        assertThat( new KojiTaskRequest( List.of( SCM_URL, TARGET ) ).asBuildRequest( "foo" ), nullValue() );
        assertThat( new KojiTaskRequest( List.of( SCM_URL ) ).asBuildRequest( (String) null ), nullValue() );
    }
}
