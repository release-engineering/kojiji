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
package com.redhat.red.build.koji;

import com.redhat.red.build.koji.config.KojiConfig;
import com.redhat.red.build.koji.config.SimpleKojiConfigBuilder;
import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveQuery;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

public class ExternalMultiCallTimeoutTest
{
    private static final Logger logger = LoggerFactory.getLogger( ExternalMultiCallTimeoutTest.class );

    private static final List<String> CHECKSUMS = Arrays.asList( new String[] { "2f934f61014c4d0c99df46de6b270259", "4fe01c5b4f06c6e86ebed1fc7b3f2e10", "6599317108e9b49b4fcc2cf861d6cd91", "737bc95210dd9bd87d5a5472ce135e64", "c3da00af895d34cf6dea3a531ae814ca", "cf4808aacdf9116b05aba96f55f80899", "6eb3e4e0ec1cbbd8e7a0a8f8fc70eee1", "c99263abda84bc8f8efd04f256791340", "10c24fb284d86fda258b0658a6800c13", "071a8e53cdf4de088a200d352239790f", "0c647409664d80dc0866d94dcf21bb8e", "20a92051b8b118d8803ec959796467b5", "9bdb645b55a1c41e657182d6dd481d29", "f4db0d0030a4f3e78c86dd0604d03bb1", "fc12b288915d4cb2952ad6f58feb9f1a", "afdc85d3f14481e4842c317c4f414f7e", "d2cd47b3ade42f428600f5330d9ce110", "13f20b0fd76d6156cc1e670cf8b96e15", "af7f514226d218ae2ec2b56c8af2b490", "fc93034754ba7477a6d64f64258d282d", "363678f015902bcc040308136f845a3f", "4400f7a47130dcb501af32edce50a2ea", "7f00d5f8a11b175d02c85ea84d4f2a96", "a215845468342a83de6c454fbc6bfe9a", "c7c084fa3e1f8b9bf3c6fb105435a717", "64602836148c4ada342a496bb67d603f", "5b969fea3e2dc6792d3335d55566624a", "85f6b8a4321de5fe745cd36c3b80d5d2", "bfcb7bbf61eac8db4bf007218d364d12", "3d2aa30721b6cac5af137bfcf99146ee", "6bca620f50d423a9879d6211f24c1c96", "8c1f436dfb997b41cc4e02ac8104063a", "13e8de083a2d90b7083d8923f76df808", "86c4495a0a1ce72ce1cd4f319e7c122e", "e5febb30d86ea108d8ad7aea516cb3ec", "a64bb382a19178be188b3430040400da", "3bf91f72b61cb6a7eda97169fadd3204", "8cb8dfac80c2beada46f76493632c0b0", "2897971606386ea5c6bf49032c94b8ac", "1df6f29c3fdeb91c5ed8499edf9dc164", "334912bf8d138687bf605819fea3d5d2", "c4a45f79e5bac369a82d6bce771469f6", "b65771c38743b57eb852a3f57656234c", "aab8416195efcb9289d34c8ec5eb3c1a", "65ae171828ee7262c68be2ea1d6f7708", "c5eca4e58a75eabe3379926803421bab", "7550024fe5df720a7baecb9c2be5b093", "95184b2ed644903ce7d623c5e269a107", "293aeffce5ea67a19aeb85636b58f1f0", "c85438322bdc00f19011f136e5e36830", "a2d33eaee337376f0b4220c32a0b8af4", "80a369bcb90689103ebf3bbc4f507b09", "2af4791307c62a058575035189de34be", "a06f0d0b82d536532037c76f11516aaf", "42c0406fb04a784272aa66957ac6e689", "674019d9f6354f13eb520b6ae72db386", "e4f35765ed3f0176652134c0bc3a6015", "2ef804370f27e6777b5f389a07dc8be1", "76ea5312e962cbcf783155ffb118d204", "b3ed202177222523af3e08886d0e7a50", "827367b938980cb21f2e6f5ae7f256af", "fcf4098c96589aa293a1dc649ddd4cd1", "7c8d0c8fc1232ceb53eecaa5f15016f2", "26be387711c7bdab30bab03488246a9d", "bcd270fbd640c2e0929a6d8b6a1d1f62", "53a418c6a6023b8cd37592e7c9b29867", "84b55ce735b53c6c4acee152195a1c8e", "631bfc43cf5f601d34f1f5ea16751061", "45a71c083211d431e07bdf4f17644f9f", "15c974f39f997a39ead8c8dde0f46d51", "2c20a5628ca7073b9a1a45497b38caa0", "9f6f4cd41a76d6b725f2e0e54c151457", "fb9eed94942c9f6771da8638ae44c2de", "6a42670959341bc12b373680a1ead6b1", "e90882e5eb013e4257ffe9179c00dcb0", "5ac121f66ecbfb03d63142900a86dacc", "767a149c0a09985a59333cdb403ddebd", "3734654deb48c10aed8f81d2226fe02d", "628c2f7e368b11e6e9f48bbf94b4f8af", "f18cb0f4ab3804358e726108f0facec0", "8981f82200bacb3c187c98f2b1ddd402", "ed29dc61708dd30b799801ab55e1e2f6", "31f1ac3506cd2ef916c1b773bedc1597", "6576cfcfe5c303b3f29e078ddc174001", "b83dfef66f307a7d71f52ab59a8bce54", "3dae170712ecd3ecc82bfc93bd70ccc1", "cccc5aaba015f6855de873c675bbaf34", "8a0c177162db21ac2ed543f926eba8ec", "dada47d749d62d382c59ec51a9caff9e", "a8ac467ed9c90f52b62a43e3e9d850af", "64c94d22254d34bdf4cb0138e79e359c", "4c82c38f7d053281c20215a5b4c558b3", "087b3722b3ca445457843fe90bb08e44", "b7f4b39b6c0f9f0f73103066518583ac", "fdfb0529cc3db657f28fc85b6d96eb63", "1699200baad275f0a21d4e2f01428de4", "cfa4a0ec3f94f654de0f4ca58467ee3b", "e2f6885ba52484fe272b01120b12c927", "d799f7ab5c1d7a98235abb7fadd4edb8", "78f61398079b02353be6970edf53520e", "bdf6e4ffe50b56689be11b289bee3eb3", "01adf2a1e6ad5b27f5a560012dd9cdac", "980a37ae2e0b9a54a005c82b98f3aca3", "f445198259d0e2dc473129963855e4e0", "2d497a5286406be37e35dc1a5a941e54", "40644c6d47d881829600f19b251042a4", "e7776f2b03a4c62d691a90d3c68c93c0", "a243efc0ebc1a30858174e1d2ea469a7", "f744344266b2ed88b20131a457c5efc8", "5c4f529ca8406a4ce1b85073e35825e3", "83626d38d1351702150cb83f55407474", "54db4afff96d30fe1bb1761fce9d3abf", "27f3d65d956752e4a3b394af6f949806", "4dc8159f848253a7078fbee790564466", "0ffa55418214f714738526aff4fca2b7", "a51ab4d2f6621c13dfb2e279c0a45caa", "f314fbbd3b938074ac81b5bd0de74379", "0df21aeb09e37b535d9d76330f93fae4", "41189b889f0a17394e722c5b2d5dcdec", "e2ddf81181d971b9679db2b9d12d1035", "1c5568522576741c949981280785f728", "86bc98ff5f33ae49a5e174ef6d6d5d6e", "44bed4707b6a2406c2ec3b1816303d6f", "3bac3e1144c8de7dac36c36dc7ccfe17", "4c9272163aa6a32440cd7ecdf6e64017", "3c38abdb8ec2c2a44854f4ac447ed942", "3c32e9b3ac1cbd201e448e26b8fc7dc9", "8ea9226c54ce2096bc8826b4bf0d1f50" } );

    private static final long SHORT_TIME = TimeUnit.MINUTES.toMillis( 1L );

    private static final long LONG_TIME = TimeUnit.MINUTES.toMillis( 5L );

    private static final long DELTA = TimeUnit.SECONDS.toMillis( 5L );

    private String hubUrl;

    private KojiClient client;

    private KojiClientHelper helper;

    @Before
    public void setUp()
            throws KojiClientException
    {
        hubUrl = System.getProperty( "koji.hubUrl" );

        assumeNotNull ( hubUrl );

    }

    public List<List<KojiArchiveInfo>> listArchivesWithTimeout( long timeoutMillis )
            throws KojiClientException
    {
        KojiConfig config;

        if ( timeoutMillis != 0L )
        {
            config = new SimpleKojiConfigBuilder().withKojiURL( hubUrl ).withTimeout( Long.valueOf( TimeUnit.MILLISECONDS.toSeconds( timeoutMillis ) ).intValue() ).build();
        }
        else
        {
            config = new SimpleKojiConfigBuilder().withKojiURL( hubUrl ).build();
        }

        client = new KojiClient( config, null, Executors.newFixedThreadPool( 1 ) );
        helper = new KojiClientHelper( client );

        int checksumsSize = CHECKSUMS.size();
        List<KojiArchiveQuery> queries = new ArrayList<>( checksumsSize );

        CHECKSUMS.forEach( checksum -> queries.add( new KojiArchiveQuery().withChecksum( checksum ) ) );

        int queriesSize = queries.size();

        logger.debug( "Number of queries for listArchives(): {}", queriesSize );

        assertThat( queriesSize, equalTo( checksumsSize ) );

        Instant start = Instant.now();

        try
        {
            List<List<KojiArchiveInfo>> archiveInfos = helper.listArchives( queries, null );

            return archiveInfos;
        }
        catch ( NullPointerException | KojiClientException e )
        {
            return null;
        }
        finally
        {
            Instant end = Instant.now();

            Duration duration = Duration.between( start, end );

            logger.debug( "listArchives() took: {}", duration );
            logger.debug( "listArchives() timeout was set to: {} ms", timeoutMillis );

            assertTrue( timeoutMillis == 0L || duration.toMillis() < timeoutMillis + DELTA );
        }
    }

    @Test
    public void testKojiArchiveQueryWithDefaultTimeout()
            throws KojiClientException
    {
        List<List<KojiArchiveInfo>> archiveInfos = listArchivesWithTimeout( 0L );

        assertNull( archiveInfos );
    }

    @Test
    public void testKojiArchiveQueryWithLowTimeout()
            throws KojiClientException

    {
        List<List<KojiArchiveInfo>> archiveInfos = listArchivesWithTimeout( SHORT_TIME );

        assertNull( archiveInfos );
    }

    @Test
    public void testKojiArchiveQueryWithHighTimeout()
            throws KojiClientException

    {
        List<List<KojiArchiveInfo>> archiveInfos = listArchivesWithTimeout( LONG_TIME );

        assertNotNull( archiveInfos );

        assertThat( archiveInfos.size(), equalTo( CHECKSUMS.size() ) );
    }
}
