/**
 * Copyright (C) 2015 Red Hat, Inc. (jdcasey@commonjava.org)
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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.commonjava.util.jhttpc.HttpFactory;
import org.commonjava.util.jhttpc.INTERNAL.util.SSLUtils;
import org.commonjava.util.jhttpc.auth.MemoryPasswordManager;
import org.commonjava.util.jhttpc.auth.PasswordManager;
import org.commonjava.util.jhttpc.auth.PasswordType;
import org.commonjava.util.jhttpc.model.SiteConfig;
import org.commonjava.util.jhttpc.model.SiteConfigBuilder;
import org.commonjava.util.jhttpc.util.UrlUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public abstract class AbstractIT
{

    private static final String NON_SSL_HOST = "docker.containers.%s.ports.80/tcp.host";

    private static final String NON_SSL_PORT = "docker.containers.%s.ports.80/tcp.port";

    private static final String SSL_HOST = "docker.containers.%s.ports.443/tcp.host";

    private static final String SSL_PORT = "docker.containers.%s.ports.443/tcp.port";

    private static final String NON_SSL_URL_FORMAT = "http://%s:%s";

    private static final String SSL_URL_FORMAT = "https://%s:%s";

    private static final String CONTENT_MGMT_PATH = "/cgi-bin/content.py/";

    protected static final String SSL_CONFIG_BASE = "/clients/%s";

    protected static final String SITE_CERT_PATH = SSL_CONFIG_BASE + "/serverca.crt";

    @Rule
    public TestName name = new TestName();

    protected HttpFactory factory;

    protected PasswordManager passwordManager;

    private String kojiUser;

    protected abstract String getContainerId();

    protected SiteConfigBuilder getSiteConfigBuilder()
            throws Exception
    {
        return new SiteConfigBuilder( getContainerId(), getSSLBaseUrl() ).withServerCertPem( getServerCertsPem() );
    }

    @Before
    public void setup()
            throws Exception
    {
        System.out.println( "\n\n #### SETUP: " + name.getMethodName() + " #### \n\n" );
        passwordManager = new MemoryPasswordManager();
        factory = new HttpFactory( passwordManager );
        System.out.println( "\n\n #### START: " + name.getMethodName() + " #### \n\n" );
    }

    @After
    public void teardown()
            throws Exception
    {
        String buildDir = System.getProperty( "project.build.directory", "target" );

        File dir = new File( buildDir, "httpd-logs" );
        dir = new File( dir, getContainerId() );
        dir = new File( dir, name.getMethodName() );
        dir.mkdirs();

        System.out.println( "Downloading httpd logs to: " + dir );

        List<String> paths = Arrays.asList( "clients/%s/serverca.crt", "client/%s/clientca.crt", "clients/%s/client.crt",
                                            "clients/%s/client.pem", "httpd/conf/httpd.conf", "httpd/conf.d/kojihub.conf",
                                            "logs/error_log", "logs/ssl_error_log",
                                            "logs/access_log", "logs/ssl_access_log", "logs/ssl_request_log" );

        CloseableHttpClient client = null;
        FileOutputStream stream = null;
        try
        {
            client = factory.createClient();
            for ( String path : paths )
            {
                File httpdFile = new File( dir, path );
                httpdFile.getParentFile().mkdirs();

                CloseableHttpResponse response = client.execute( new HttpGet( formatUrl( path ) ) );

                if ( response.getStatusLine().getStatusCode() == 200 )
                {
                    try
                    {
                        stream = new FileOutputStream( httpdFile );
                        IOUtils.copy( response.getEntity().getContent(), stream );
                    }
                    finally
                    {
                        IOUtils.closeQuietly( stream );
                    }
                }
                else
                {
                    System.out.println( "Cannot retrieve: " + path + ". Status was: " + response.getStatusLine() );
                }
            }
        }
        catch ( Exception err )
        {
            System.out.println( "Failed to retrieve server logs after error. Reason: " + err.getMessage() );
            err.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly( client );
        }

        factory.close();
        System.out.println( "\n\n #### END: " + name.getMethodName() + "#### \n\n" );
    }

    protected String getServerCertsPem()
            throws Exception
    {
        StringBuilder pem = new StringBuilder();

        CloseableHttpClient client = null;
        try
        {
            client = factory.createClient();
            System.out.println( "\n\n ##### START: " + name.getMethodName() + " :: server.pem #####\n\n" );
            CloseableHttpResponse response = client.execute( new HttpGet( String.format( SITE_CERT_PATH, getKojiUser() ) ) );
            assertThat( response.getStatusLine().getStatusCode(), equalTo( 200 ) );
            String result = IOUtils.toString( response.getEntity().getContent() );

            System.out.println( result );
            assertThat( result, notNullValue() );
            System.out.println( "\n\n ##### END: " + name.getMethodName() + " :: server.pem #####\n\n" );

            return result;
        }
        finally
        {
            IOUtils.closeQuietly( client );
        }
    }

    protected String getClientKeyCertPem()
            throws Exception
    {
        CloseableHttpClient client = null;
        try
        {
            client = factory.createClient();
            System.out.println( "\n\n ##### START: " + name.getMethodName() + " :: client.pem #####\n\n" );
            CloseableHttpResponse response = client.execute(
                    new HttpGet( formatUrl( String.format( SSL_CONFIG_BASE, getKojiUser() ), "client.pem" ) ) );
            assertThat( response.getStatusLine().getStatusCode(), equalTo( 200 ) );
            String result = IOUtils.toString( response.getEntity().getContent() );

            System.out.println( result );
            assertThat( result, notNullValue() );

            System.out.println( "\n\n ##### END: " + name.getMethodName() + " :: client.pem #####\n\n" );
            return result;
        }
        finally
        {
            IOUtils.closeQuietly( client );
        }
    }

    protected synchronized String formatUrl( String... path )
            throws Exception
    {
        String baseUrl = getBaseUrl();
        return UrlUtils.buildUrl( baseUrl, path );
    }

    protected synchronized String formatSSLUrl( String... path )
            throws Exception
    {
        String baseUrl = getSSLBaseUrl();
        return UrlUtils.buildUrl( baseUrl, path );
    }

    protected String getBaseUrl()
    {
        String host = System.getProperty( String.format( NON_SSL_HOST, getContainerId() ) );
        String port = System.getProperty( String.format( NON_SSL_PORT, getContainerId() ) );

        if ( StringUtils.isEmpty( host ) || StringUtils.isEmpty( port ) )
        {
            Assert.fail( "Non-SSL host/port properties are missing for container: " + getContainerId()
                                 + ". Did you forget to configure the docker-maven-plugin?" );
        }

        return String.format( NON_SSL_URL_FORMAT, host, port );
    }

    protected String getSSLBaseUrl()
    {
        String host = System.getProperty( String.format( SSL_HOST, getContainerId() ) );
        String port = System.getProperty( String.format( SSL_PORT, getContainerId() ) );

        if ( StringUtils.isEmpty( host ) || StringUtils.isEmpty( port ) )
        {
            Assert.fail( "SSL host/port properties are missing for container: " + getContainerId()
                                 + ". Did you forget to configure the docker-maven-plugin?" );
        }

        return String.format( SSL_URL_FORMAT, host, port );
    }

    //    protected void deleteContent( String path )
    //            throws Exception
    //    {
    //        String url = formatUrl( CONTENT_MGMT_PATH, path );
    //        HttpDelete put = new HttpDelete( url );
    //
    //        CloseableHttpClient client = null;
    //        try
    //        {
    //            client = factory.createClient();
    //            CloseableHttpResponse response = client.execute( put );
    //            int code = response.getStatusLine().getStatusCode();
    //            if ( code != 404 && code != 204 )
    //            {
    //                String extra = "";
    //                if ( response.getEntity() != null )
    //                {
    //                    String body = IOUtils.toString( response.getEntity().getContent() );
    //                    extra = "\nBody:\n\n" + body;
    //                }
    //
    //                Assert.fail( "Failed to delete content from: " + path + ".\nURL: " + url + "\nStatus: "
    //                                     + response.getStatusLine() + extra );
    //            }
    //        }
    //        finally
    //        {
    //            IOUtils.closeQuietly( client );
    //        }
    //    }

    protected void putContent( String path, String content )
            throws Exception
    {
        String url = formatUrl( CONTENT_MGMT_PATH, path );
        HttpPut put = new HttpPut( url );
        put.setEntity( new StringEntity( content ) );

        CloseableHttpClient client = null;
        try
        {
            client = factory.createClient();
            CloseableHttpResponse response = client.execute( put );
            int code = response.getStatusLine().getStatusCode();
            if ( code != 200 && code != 201 )
            {
                String extra = "";
                if ( response.getEntity() != null )
                {
                    String body = IOUtils.toString( response.getEntity().getContent() );
                    extra = "\nBody:\n\n" + body;
                }

                Assert.fail(
                        "Failed to put content to: " + path + ".\nURL: " + url + "\nStatus: " + response.getStatusLine()
                                + extra );
            }
        }
        finally
        {
            IOUtils.closeQuietly( client );
        }
    }

    protected String getKojiUser()
    {
        return "kojiadmin";
    }
}
