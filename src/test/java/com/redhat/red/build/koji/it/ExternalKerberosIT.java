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
package com.redhat.red.build.koji.it;

import com.redhat.red.build.koji.KojiClient;
import com.redhat.red.build.koji.KojiClientException;
import com.redhat.red.build.koji.config.KojiConfig;
import com.redhat.red.build.koji.config.SimpleKojiConfigBuilder;
import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiUserInfo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.Executors;

import static com.redhat.red.build.koji.model.xmlrpc.KojiAuthType.KERB;
import static com.redhat.red.build.koji.model.xmlrpc.KojiUserStatus.NORMAL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeNotNull;

public class ExternalKerberosIT
{
    private static final Logger logger = LoggerFactory.getLogger( ExternalKerberosIT.class );

    private static final String hubUrl = System.getProperty( "koji.hubUrl" );

    private static final String krbCcache = System.getProperty( "krb.ccache" );

    private static final String krbKeytab = System.getProperty( "krb.keytab" );

    private static final String krbPassword = System.getProperty( "krb.password" );

    private static final String krbPrincipal = System.getProperty( "krb.principal" );

    private static final String krbService = System.getProperty( "krb.service" );

    private static void validateLoggedInUser( KojiConfig config )
            throws KojiClientException
    {
        try ( KojiClient client = new KojiClient( config, null, Executors.newSingleThreadExecutor() ) )
        {
            client.krbLogin();

            KojiSessionInfo session = client.krbLogin();
            KojiUserInfo info = client.getLoggedInUserInfo( session );

            logger.info( "{}", info );

            assertThat( info.getStatus(), is( NORMAL ) );
            assertThat( info.getAuthType(), is( KERB ) );
            assertThat( info.getKerberosPrincipal(), is( krbPrincipal ) );
        }
    }

    @Test
    public void testKrbLoginWithCcache()
            throws KojiClientException
    {
        assumeNotNull( hubUrl, krbService, krbPrincipal, krbCcache );

        File ccacheFile = new File( krbCcache );

        assertThat( ccacheFile.isFile(), is( true ) );

        KojiConfig config = new SimpleKojiConfigBuilder().withKojiURL( hubUrl ).withKrbService( krbService )
                .withKrbPrincipal( krbPrincipal ).withKrbCCache( krbCcache ).build();

        validateLoggedInUser( config );
    }

    @Test
    public void testKrbLoginWithKeytab()
            throws KojiClientException
    {
        assumeNotNull( hubUrl, krbService, krbPrincipal, krbKeytab );

        File keytabFile = new File( krbKeytab );

        assertThat( keytabFile.isFile(), is( true ) );

        KojiConfig config = new SimpleKojiConfigBuilder().withKojiURL( hubUrl ).withKrbService( krbService )
                .withKrbPrincipal( krbPrincipal ).withKrbKeytab( krbKeytab ).build();

        validateLoggedInUser( config );
    }

    @Test
    public void testKrbLoginWithPassword()
            throws KojiClientException
    {
        assumeNotNull( hubUrl, krbService, krbPrincipal, krbPassword );

        KojiConfig config = new SimpleKojiConfigBuilder().withKojiURL( hubUrl ).withKrbService( krbService )
                .withKrbPrincipal( krbPrincipal ).withKrbPassword( krbPassword ).build();

        validateLoggedInUser( config );
    }
}
