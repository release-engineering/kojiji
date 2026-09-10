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

import static java.util.Map.entry;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class KojiMavenBuildRequestTest
{
    private static final String SCM_URL = "git://example.com/messaging/activemq-artemis.git#2.6.3.jbossorg-00017";

    private static final String TARGET = "jb-amq-7-candidate";

    private static final String PATCHES_URL = "svn+http://example-svn.com/repos/mead/patches/org.rh-messaging.AMQ7-A-MQ7-parent/activemq-artemis/2.6.3.redhat-00017-1#1234";

    private static final String SPECFILE_URL = "svn+http://example-svn.com/repos/mead/specfiles/org.rh-messaging.AMQ7-A-MQ7-parent/activemq-artemis/2.6.3.redhat-00017-1#1234";

    @Test
    public void testOpts()
    {
        Map<String, Object> opts = Map.ofEntries( entry( "patches", PATCHES_URL ),
                                                  entry( "specfile", SPECFILE_URL ),
                                                  entry( "goals", List.of( "install", "javadoc:aggregate-jar" ) ),
                                                  entry( "profiles", List.of( "release" ) ),
                                                  entry( "packages", List.of( "tar", "bzip2" ) ),
                                                  entry( "jvm_options", List.of( "-Xms512m", "-Xmx3096m" ) ),
                                                  entry( "maven_options", List.of( "-pl", "!tests/activemq5-unit-tests" ) ),
                                                  entry( "properties", Map.of( "skipTests", "true" ) ),
                                                  entry( "envs", Map.of( "MAVEN_OPTS", "-Xmx3096m" ) ),
                                                  entry( "scratch", true ),
                                                  entry( "skip_tag", true ),
                                                  entry( "repo_id", 1234 ) );
        KojiMavenBuildRequest request = (KojiMavenBuildRequest) new KojiTaskRequest( List.of( SCM_URL, TARGET, opts ) ).asBuildRequest( KojiTaskMethod.maven );
        assertThat( request.getScmUrl(), equalTo( SCM_URL ) );
        assertThat( request.getTarget().getName(), equalTo( TARGET ) );
        assertThat( request.getPatches(), equalTo( PATCHES_URL ) );
        assertThat( request.getSpecfile(), equalTo( SPECFILE_URL ) );
        assertThat( request.getGoals(), equalTo( List.of( "install", "javadoc:aggregate-jar" ) ) );
        assertThat( request.getProfiles(), equalTo( List.of( "release" ) ) );
        assertThat( request.getPackages(), equalTo( List.of( "tar", "bzip2" ) ) );
        assertThat( request.getJvmOptions(), equalTo( List.of( "-Xms512m", "-Xmx3096m" ) ) );
        assertThat( request.getMavenOptions(), equalTo( List.of( "-pl", "!tests/activemq5-unit-tests" ) ) );
        assertThat( request.getProperties(), equalTo( Map.of( "skipTests", "true" ) ) );
        assertThat( request.getEnvs(), equalTo( Map.of( "MAVEN_OPTS", "-Xmx3096m" ) ) );
        assertThat( request.isScratch(), equalTo( true ) );
        assertThat( request.isSkipTag(), equalTo( true ) );
        assertThat( request.getRepoId(), equalTo( 1234 ) );
    }

    @Test
    public void testEmptyOpts()
    {
        KojiMavenBuildRequest request = (KojiMavenBuildRequest) new KojiTaskRequest( List.of( SCM_URL, TARGET ) ).asBuildRequest( KojiTaskMethod.maven );
        assertThat( request.getScmUrl(), equalTo( SCM_URL ) );
        assertThat( request.getProperties(), equalTo( Map.of() ) );
        assertThat( request.getGoals(), equalTo( List.of() ) );
        assertThat( request.getDeps(), equalTo( List.of() ) );
        assertThat( request.isScratch(), equalTo( false ) );
        assertThat( request.getRepoId(), nullValue() );
    }
}
