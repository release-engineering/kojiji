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

import com.redhat.red.build.koji.KojiBindery;
import com.redhat.red.build.koji.KojiClient;
import com.redhat.red.build.koji.config.KojiConfig;
import com.redhat.red.build.koji.config.SimpleKojiConfig;
import com.redhat.red.build.koji.config.SimpleKojiConfigBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.commonjava.rwx.binding.error.BindException;
import org.commonjava.util.jhttpc.HttpFactory;
import org.commonjava.util.jhttpc.auth.MemoryPasswordManager;
import org.commonjava.util.jhttpc.auth.PasswordManager;
import org.commonjava.util.jhttpc.auth.PasswordType;
import org.commonjava.util.jhttpc.model.SiteConfigBuilder;
import org.commonjava.util.jhttpc.util.UrlUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AbstractIT
{

    private static final String NON_SSL_HOST = "docker.containers.koji-hub.ports.80/tcp.host";

    private static final String NON_SSL_PORT = "docker.containers.koji-hub.ports.80/tcp.port";

    private static final String SSL_HOST = "docker.containers.koji-hub.ports.443/tcp.host";

    private static final String SSL_PORT = "docker.containers.koji-hub.ports.443/tcp.port";

    private static final String NON_SSL_URL_FORMAT = "http://%s:%s";

    private static final String SSL_URL_FORMAT = "https://%s:%s";

    private static final String CONTENT_MGMT_PATH = "/cgi-bin/content.py/";

    protected static final String SSL_CONFIG_BASE = "/clients/%s";

    protected static final String SITE_CERT_PATH = SSL_CONFIG_BASE + "/serverca.crt";

    private static final String KOJI_ID = "koji-test";

    @Rule
    public TestName name = new TestName();

    @ClassRule
    public static TemporaryFolder temp = new TemporaryFolder();

    protected HttpFactory factory;

    protected PasswordManager passwordManager;

    protected String kojiUser;

    protected File downloadDir;

    protected SimpleKojiConfigBuilder getKojiConfigBuilder()
            throws Exception
    {
        String baseUrl = formatSSLUrl("kojihub");
        SimpleKojiConfigBuilder builder = new SimpleKojiConfigBuilder( baseUrl ).withKojiSiteId( KOJI_ID )
                                                                                .withKojiClientCertificatePassword(
                                                                                        "mypassword" )
                                                                                .withTimeout( 2 );

        withNewClient( ( client ) -> {
            builder.withClientKeyCertificateFile( getClientKeyCertPem( client ).getPath() );
            builder.withServerCertificateFile( getServerCertsPem( client ).getPath() );
        } );

        return builder;
    }

    @Before
    public void setup()
    {
        System.out.println( "\n\n #### SETUP: " + name.getMethodName() + " #### \n\n" );
        passwordManager = new MemoryPasswordManager();
        factory = new HttpFactory( passwordManager );
        System.out.println( "\n\n #### START: " + name.getMethodName() + " #### \n\n" );

        String buildDir = System.getProperty( "project.build.directory", "target" );

        downloadDir = Paths.get( buildDir, "downloads", name.getMethodName() ).toFile();
        downloadDir.mkdirs();
    }

    protected KojiClient newKojiClient()
            throws Exception
    {
        try
        {
            System.out.println("SETTING UP KOJI CLIENT");
            KojiConfig config = getKojiConfigBuilder().build();

            PasswordManager passwords = new MemoryPasswordManager();
            passwords.bind( config.getKojiClientCertificatePassword(), config.getKojiSiteId(), PasswordType.KEY );

            HttpFactory httpFactory = new HttpFactory( passwords );
            KojiBindery bindery = new KojiBindery();

            System.out.println("DONE: SETTING UP KOJI CLIENT");
            return new KojiClient( config, bindery, httpFactory );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            throw e;
        }
    }

    @After
    public void teardown()
            throws Exception
    {
        System.out.println( "Downloading httpd logs to: " + downloadDir );

        List<String> paths =
                Arrays.asList( "clients/%s/serverca.crt", "clients/%s/clientca.crt", "clients/%s/client.crt",
                               "clients/%s/client.pem", "httpd/conf/httpd.conf", "httpd/conf.d/kojihub.conf",
                               "httpd/conf.d/test-accessories.conf", "logs/httpd/error_log", "logs/httpd/ssl_error_log",
                               "logs/httpd/access_log", "logs/httpd/ssl_access_log", "logs/httpd/ssl_request_log" );

        withNewClient( ( client ) -> {
            paths.forEach( ( path ) -> {
                if ( path.contains( "%s" ) )
                {
                    path = String.format( path, getKojiUser() );
                }

                downloadFile( path, client );
            } );
        } );

        factory.close();
        System.out.println( "\n\n #### END: " + name.getMethodName() + "#### \n\n" );
    }

    protected File downloadFile( String path, CloseableHttpClient client )
    {
        String url = formatUrl( path );
        System.out.println( "\n\n ##### START: " + name.getMethodName() + " :: " + url + " #####\n\n" );

        File targetFile = new File( downloadDir, path );
        targetFile.getParentFile().mkdirs();

        CloseableHttpResponse response = null;
        try
        {
            response = client.execute( new HttpGet( url ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            Assert.fail( String.format( "Failed to execute GET request: %s. Reason: %s", url, e.getMessage() ) );
            return null;
        }

        FileOutputStream stream = null;
        if ( response.getStatusLine().getStatusCode() == 200 )
        {
            try
            {
                stream = new FileOutputStream( targetFile );
                IOUtils.copy( response.getEntity().getContent(), stream );

                return targetFile;
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                Assert.fail(
                        String.format( "Failed to retrieve body content from: %s. Reason: %s", url, e.getMessage() ) );
            }
            finally
            {
                IOUtils.closeQuietly( stream );
                System.out.println(
                        "\n\n ##### END: " + name.getMethodName() + " :: " + SITE_CERT_PATH + " #####\n\n" );
            }
        }
        else
        {
            System.out.println( "Cannot retrieve: " + path + ". Status was: " + response.getStatusLine() );
            System.out.println( "\n\n ##### END: " + name.getMethodName() + " :: " + SITE_CERT_PATH + " #####\n\n" );
        }

        return null;
    }

    protected void withNewClient( Consumer<CloseableHttpClient> consumer )
    {
        CloseableHttpClient client = null;
        FileOutputStream stream = null;
        try
        {
            client = factory.createClient();
            consumer.accept( client );
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
    }

    protected File getServerCertsPem( CloseableHttpClient client )
    {
        System.out.println("Getting server cert(s) PEM");
        return downloadFile( String.format( SITE_CERT_PATH, getKojiUser() ), client );
    }

    protected File getClientKeyCertPem( CloseableHttpClient client )
    {
        System.out.println("Getting client key/cert PEM");
        return downloadFile( String.format( SSL_CONFIG_BASE, getKojiUser() ) + "/client.pem", client );
    }

    protected synchronized String formatUrl( String... path )
    {
        String baseUrl = getBaseUrl();
        try
        {
            return UrlUtils.buildUrl( baseUrl, path );
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
            Assert.fail(
                    String.format( "Failed to format URL from parts: [%s]. Reason: %s", StringUtils.join( path, ", " ),
                                   e.getMessage() ) );
        }

        return null;
    }

    protected synchronized String formatSSLUrl( String... path )
            throws Exception
    {
        String baseUrl = getSSLBaseUrl();
        return UrlUtils.buildUrl( baseUrl, path );
    }

    protected String getBaseUrl()
    {
        String host = System.getProperty( NON_SSL_HOST );
        String port = System.getProperty( NON_SSL_PORT );

        if ( StringUtils.isEmpty( host ) || StringUtils.isEmpty( port ) )
        {
            Assert.fail(
                    "Non-SSL host/port properties are missing. Did you forget to configure the docker-maven-plugin?" );
        }

        return String.format( NON_SSL_URL_FORMAT, host, port );
    }

    protected String getSSLBaseUrl()
    {
        String host = System.getProperty( SSL_HOST );
        String port = System.getProperty( SSL_PORT );

        if ( StringUtils.isEmpty( host ) || StringUtils.isEmpty( port ) )
        {
            Assert.fail( "SSL host/port properties are missing. Did you forget to configure the docker-maven-plugin?" );
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
