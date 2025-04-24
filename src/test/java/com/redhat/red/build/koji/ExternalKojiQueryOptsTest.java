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
package com.redhat.red.build.koji;

import com.redhat.red.build.koji.config.KojiConfig;
import com.redhat.red.build.koji.config.SimpleKojiConfigBuilder;
import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildTypeQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import com.redhat.red.build.koji.model.xmlrpc.KojiPackageQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagQuery;
import com.redhat.red.build.koji.model.xmlrpc.messages.Constants;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeNotNull;

/**
 * Before running these tests, you need to set VM argument -Dkoji.hubUrl
 * These tests are ignored by default.
 * They depend on the external Koji server content.
 */
public class ExternalKojiQueryOptsTest
{
    private static final Logger logger = LoggerFactory.getLogger( ExternalKojiQueryOptsTest.class );

    private KojiClient client;

    @Before
    public void setUp()
            throws KojiClientException
    {
        String hubUrl = System.getProperty( "koji.hubUrl" );

        assumeNotNull ( hubUrl );

        KojiConfig config = new SimpleKojiConfigBuilder().withKojiURL( hubUrl ).build();

        client = new KojiClient( config, null, Executors.newFixedThreadPool( 5 ) );
    }

    @Test
    public void testListArchives()
            throws KojiClientException
    {
        List<KojiArchiveQuery> queries = new ArrayList<>();

        KojiArchiveQuery query = new KojiArchiveQuery();
        query.setBuildId( 731240 );
        queries.add( query );

        query = new KojiArchiveQuery();
        query.setBuildId( 0 );
        queries.add( query );

        query = new KojiArchiveQuery();
        query.setBuildId( 731239 );
        queries.add( query );

        int ret0 = client.getArchiveCount( queries.get( 0 ), null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 3, ret0 );

        List<Integer> ret = client.queryCountOnly( Constants.LIST_ARCHIVES, queries, null );

        for ( Integer count : ret )
        {
            logger.debug( ">>> {}", count );
        }

        assertEquals( Arrays.asList( 3, 0, 3 ), ret );
    }

    @Test
    public void testListBuilds()
            throws KojiClientException
    {
        List<KojiBuildQuery> queries = new ArrayList<>();

        KojiBuildQuery query = new KojiBuildQuery();
        query.setPackageId( 13174 );
        queries.add( query );

        query = new KojiBuildQuery();
        query.setPackageId( 31928 );
        queries.add( query );

        query = new KojiBuildQuery();
        query.setPackageId( 31930 );
        queries.add( query );

        int ret0 = client.getBuildCount( queries.get( 0 ), null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 2, ret0 );

        List<Integer> ret = client.queryCountOnly( Constants.LIST_BUILDS, queries, null );

        for ( Integer count : ret )
        {
            logger.debug( ">>> {}", count );
        }

        assertEquals( Arrays.asList( 2, 36, 11 ), ret );
    }

    @Test
    public void testListBuildTypes()
            throws KojiClientException
    {
        List<KojiBuildTypeQuery> queries = new ArrayList<>();

        KojiBuildTypeQuery query = new KojiBuildTypeQuery();
        query.setId( 1 );
        queries.add( query );

        query = new KojiBuildTypeQuery();
        query.setId( 0 );
        queries.add( query );

        query = new KojiBuildTypeQuery();
        query.setId( 2 );
        queries.add( query );

        queries.forEach( q -> logger.debug( "{}", q ) );

        int ret0 = client.getBuildTypeCount( queries.get( 0 ), null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 1, ret0 );

        List<Integer> ret = client.queryCountOnly( Constants.LIST_BTYPES, queries, null );

        for ( Integer count : ret )
        {
            logger.debug( ">>> {}", count );
        }

        assertEquals( Arrays.asList( 1, 0, 1 ), ret );

    }

    @Test
    public void testListPackages()
            throws KojiClientException
    {
        List<KojiPackageQuery> queries = new ArrayList<>();

        KojiPackageQuery query = new KojiPackageQuery();
        query.setPkgId( 13174 );
        queries.add( query );

        query = new KojiPackageQuery();
        query.setPkgId( 31928 );
        queries.add( query );

        query = new KojiPackageQuery();
        query.setPkgId( 31930 );
        queries.add( query );

        int ret0 = client.getPackageCount( queries.get( 0 ), null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 1, ret0 );

        List<Integer> ret = client.queryCountOnly( Constants.LIST_PACKAGES, queries, null );

        for ( Integer count : ret )
        {
            logger.debug( ">>> {}", count );
        }

        assertEquals( Arrays.asList( 1, 1, 1 ), ret );
    }

    @Test
    public void testListTags()
            throws KojiClientException
    {
        List<KojiTagQuery> queries = new ArrayList<>();

        KojiTagQuery query = new KojiTagQuery();
        query.setBuildId( KojiIdOrName.getFor( 13174 ) );
        queries.add( query );

        query = new KojiTagQuery();
        query.setBuildId( KojiIdOrName.getFor( 31928 ) );
        queries.add( query );

        query = new KojiTagQuery();
        query.setBuildId( KojiIdOrName.getFor( 31930 ) );
        queries.add( query );

        int ret0 = client.getTagCount( queries.get( 0 ), null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 1, ret0 );

        List<Integer> ret = client.queryCountOnly( Constants.LIST_TAGS, queries, null );

        for ( Integer count : ret )
        {
            logger.debug( ">>> {}", count );
        }

        assertEquals( Arrays.asList( 1, 8, 1 ), ret );
    }
}
