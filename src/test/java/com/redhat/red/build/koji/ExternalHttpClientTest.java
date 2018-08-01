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
package com.redhat.red.build.koji;

import com.redhat.red.build.koji.config.KojiConfig;
import com.redhat.red.build.koji.config.SimpleKojiConfigBuilder;
import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTaskInfo;
import org.commonjava.maven.atlas.ident.ref.SimpleArtifactRef;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.GET_TASK_INFO;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;

/**
 * Before running these tests, you need to set VM argument -Dkoji.hubUrl
 * These tests are ignored by default. They depends on the external Koji server content.
 */
public class ExternalHttpClientTest
{
    private KojiClient client;

    @Before
    public void setUp() throws KojiClientException
    {
        String hubUrl = System.getProperty( "koji.hubUrl" );
        assumeTrue( hubUrl != null );

        KojiConfig config = new SimpleKojiConfigBuilder().withKojiURL( hubUrl ).build();
        client = new KojiClient( config, null, Executors.newFixedThreadPool( 5 ) );
    }

    @Test
    public void testGetTaskInfo_multiCall() throws Exception
    {
        List<Object> req1 = new ArrayList<>(  );
        req1.add( 513598 );
        req1.add( true );

        List<Object> req2 = new ArrayList<>(  );
        req2.add( 513599 );
        req2.add( true );

        List<Object> args = new ArrayList<>();
        args.add( req1 );
        args.add( req2 );
        List<KojiTaskInfo> ret = client.multiCall( GET_TASK_INFO, args, KojiTaskInfo.class, null );
        ret.forEach( kojiTaskInfo ->
            {
                System.out.println( ">>> " + kojiTaskInfo.getTaskId() );
                assertTrue( kojiTaskInfo.getTaskId() == 513598 || kojiTaskInfo.getTaskId() == 513599 );
            } );
    }

    @Test
    public void testListBuildsContaining_multiCall() throws Exception
    {
        String groupId = "javax.activation";
        String artifactId = "activation";
        String version = "1.1-rev-1";
        SimpleArtifactRef gav = new SimpleArtifactRef( groupId, artifactId, version, "jar", null );
        List<KojiBuildInfo> ret = client.listBuildsContaining( gav, null );
        for ( KojiBuildInfo info : ret )
        {
            System.out.println( ">>> " + info.toString() );
            assertTrue( "KojiBuildInfo[javax.activation-activation-1.1_rev_1-1]".equals( info.toString() ) );
        }
    }

    @Test
    public void testListArchives_multiCall() throws Exception
    {
        KojiClientHelper kojiClientHelper = new KojiClientHelper( client );

        List<KojiArchiveQuery> queries = new ArrayList<>(  );

        KojiArchiveQuery query = new KojiArchiveQuery();
        query.setBuildId( 731240 );
        queries.add( query );

        query = new KojiArchiveQuery();
        query.setBuildId( 0 );
        queries.add( query );

        query = new KojiArchiveQuery();
        query.setBuildId( 731239 );
        queries.add( query );

        List<List<KojiArchiveInfo>> ret = kojiClientHelper.listArchives( queries, null );

        for ( List<KojiArchiveInfo> archiveInfos : ret )
        {
            System.out.println(">>>");
            for ( KojiArchiveInfo archiveInfo : archiveInfos )
            {
                System.out.println("   >>>" + archiveInfo);
            }
        }
    }

    @Test
    public void testlistTags_multiCall() throws Exception
    {
        List<Integer> buildIds = new ArrayList<>(  );
        buildIds.add( 731240 );
        buildIds.add( 0 ); // this would cause a Fault resp
        buildIds.add( 731239 );

        Map<Integer, List<KojiTagInfo>> ret = client.listTags( buildIds, null );
        List<KojiTagInfo> l = ret.get( 731240 );
        assertTrue( l != null );

        l = ret.get( 0 );
        assertTrue( l == null );

        l = ret.get( 731239 );
        assertTrue( l != null );
    }

}
