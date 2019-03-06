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
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildState;
import com.redhat.red.build.koji.model.xmlrpc.KojiGetRpmHeadersParams;
import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import com.redhat.red.build.koji.model.xmlrpc.KojiNVRA;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmBuildList;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmDependencyInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmFileInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmFilesQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmSignatureInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmSigsQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

/**
 * Before running these tests, you need to set VM argument -Dkoji.hubUrl
 * These tests are ignored by default. They depends on the external Koji server content.
 */
public class ExternalKojiRpmTest
{
    private static final Logger logger = LoggerFactory.getLogger( ExternalKojiRpmTest.class );

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
    public void testNVRA()
            throws KojiClientException
    {
        String nvra = "rhmap-fh-openshift-templates-4.7.4-1.el7.src.rpm";
        KojiNVRA ret0 = KojiNVRA.parseNVRA( nvra );
        assertEquals( ret0.getName(), "rhmap-fh-openshift-templates" );
        assertEquals( ret0.getVersion(), "4.7.4" );
        assertEquals( ret0.getRelease(), "1.el7" );
        assertEquals( ret0.getEpoch(), null );
        assertEquals( ret0.getSrc(), Boolean.TRUE );
        assertEquals( ret0.getLocation(), null );
        logger.debug( ">>> {}", ret0 );

        nvra = "rhmap-fh-openshift-templates-4.7.4-1.el7.src";
        ret0 = KojiNVRA.parseNVRA( nvra );
        assertEquals( ret0.getName(), "rhmap-fh-openshift-templates" );
        assertEquals( ret0.getVersion(), "4.7.4" );
        assertEquals( ret0.getRelease(), "1.el7" );
        assertEquals( ret0.getEpoch(), null );
        assertEquals( ret0.getSrc(), Boolean.TRUE );
        assertEquals( ret0.getLocation(), null );
        logger.debug( ">>> {}", ret0 );

        nvra = "rhmap-fh-openshift-templates-4.7.4-1.el7.noarch";
        ret0 = KojiNVRA.parseNVRA( nvra );
        assertEquals( ret0.getName(), "rhmap-fh-openshift-templates" );
        assertEquals( ret0.getVersion(), "4.7.4" );
        assertEquals( ret0.getRelease(), "1.el7" );
        assertEquals( ret0.getEpoch(), null );
        assertEquals( ret0.getSrc(), Boolean.FALSE );
        assertEquals( ret0.getLocation(), null );
        logger.debug( ">>> {}", ret0 );

        nvra = "0:rhmap-fh-openshift-templates-4.7.4-1.el7.noarch@base";
        ret0 = KojiNVRA.parseNVRA( nvra );
        assertEquals( ret0.getName(), "rhmap-fh-openshift-templates" );
        assertEquals( ret0.getVersion(), "4.7.4" );
        assertEquals( ret0.getRelease(), "1.el7" );
        assertEquals( ret0.getEpoch(), "0" );
        assertEquals( ret0.getSrc(), Boolean.FALSE );
        assertEquals( ret0.getLocation(), "base" );
        logger.debug( ">>> {}", ret0 );
    }

    @Test
    public void testGetRPM()
            throws KojiClientException
    {
        Integer rpmId = Integer.valueOf( 6719757 );
        KojiIdOrName rpmInfo = KojiIdOrName.getFor( rpmId );
        List<KojiRpmInfo> ret0 = client.getRPM( rpmInfo, null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 1, ret0.size() );
        assertEquals( rpmId, ret0.get( 0 ).getId() );
    }

    @Test
    public void testGetLatestRPMs()
            throws KojiClientException
    {
        KojiTagInfo tagInfo = new KojiTagInfo();
        tagInfo.setName( "rhmap-4.7-rhel-7-candidate" );

        KojiRpmBuildList ret0 = client.getLatestRPMs( tagInfo, null );

        logger.debug( ">>> {}", ret0 );

        assertTrue( ret0.getRpms().get( 0 ).getId() > 0 );
        assertEquals( KojiBuildState.COMPLETE, ret0.getBuilds().get( 0 ).getBuildState() );
    }

    @Test
    public void testGetRPMDeps()
            throws KojiClientException
    {
        int rpmId = 6719757;

        List<KojiRpmDependencyInfo> ret0 = client.getRPMDeps( rpmId, null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 2, ret0.size() );
        assertEquals( Integer.valueOf( 0 ), ret0.get( 0 ).getType() );
    }

    @Test
    public void testGetRPMFile()
            throws KojiClientException
    {
        int rpmId = 6719757;
        String filename = "4.3-core-fh-core-backend.json";

        KojiRpmFileInfo ret0 = client.getRPMFile( rpmId, filename, null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( "sha256", ret0.getDigestAlgo() );
    }

    @Test
    public void testGetRPMHeaders()
            throws KojiClientException
    {
        Integer rpmId = Integer.valueOf( 6719757 );
        Integer taskId = null;
        String filepath = null;
        List<String> headers = Arrays.asList( "summary", "description");
        KojiGetRpmHeadersParams params = new KojiGetRpmHeadersParams().withRpmId( rpmId ).withTaskId( taskId ).withFilepath( filepath ).withHeaders( headers );

        Map<String, Object> ret0 = client.getRPMHeaders( params, null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 2, ret0.size() );
        assertNotNull( ret0.get( "summary" ) );
        assertNotNull( ret0.get( "description" ) );
    }

    @Test
    public void testListBuildRPMs()
            throws KojiClientException
    {
        int buildId = 834166;
        List<KojiRpmInfo> ret0 = client.listBuildRPMs( buildId, null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 2, ret0.size() );
        assertEquals( Integer.valueOf( buildId ), ret0.get( 0 ).getBuildId() );
    }

    @Test
    public void testListRPMs()
            throws KojiClientException
    {
        Integer buildId = Integer.valueOf( 834166 );
        KojiRpmQuery query = new KojiRpmQuery().withBuildId( buildId );
        List<KojiRpmInfo> ret0 = client.listRPMs( query, null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 2, ret0.size() );
        assertEquals( Integer.valueOf( buildId ), ret0.get( 0 ).getBuildId() );
    }

    @Test
    public void testListRPMFiles()
            throws KojiClientException
    {
        int rpmId = 6719757;
        KojiRpmFilesQuery query = null;

        List<KojiRpmFileInfo> ret0 = client.listRPMFiles( rpmId, query, null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( "sha256", ret0.get( 0 ).getDigestAlgo() );
    }

    @Test
    public void testListTaggedRPMs()
            throws KojiClientException
    {
        KojiTagInfo tagInfo = new KojiTagInfo();
        tagInfo.setName( "rhmap-4.7-rhel-7-candidate" );

        KojiRpmBuildList ret0 = client.listTaggedRPMS( tagInfo, null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( KojiBuildState.COMPLETE, ret0.getBuilds().get( 0 ).getBuildState() );
    }

    @Test
    public void testQueryRPMSigs()
            throws KojiClientException
    {
        Integer rpmId = Integer.valueOf ( 6719757 );
        KojiRpmSigsQuery query = new KojiRpmSigsQuery().withRpmId( rpmId );

        List<KojiRpmSignatureInfo> ret0 = client.queryRPMSigs( query, null );

        logger.debug( ">>> {}", ret0 );

        assertEquals( 2, ret0.size() );
        assertEquals( rpmId, ret0.get( 0 ).getRpmId() );
    }
}
