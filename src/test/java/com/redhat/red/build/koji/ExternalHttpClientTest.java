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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static org.apache.commons.lang.StringUtils.isBlank;

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
        if ( isBlank( hubUrl ) )
        {
            return;
        }
        KojiConfig config = new SimpleKojiConfigBuilder().withKojiURL( hubUrl ).build();
        client = new KojiClient( config, null, Executors.newFixedThreadPool( 5 ) );
    }

    @Ignore
    @Test
    public void testListArchives() throws Exception
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

}
