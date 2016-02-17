package com.redhat.red.build.koji.it;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.junit.Assert.fail;

/**
 * Created by jdcasey on 2/8/16.
 */
public class AbstractWithSetupIT
        extends AbstractIT
{
    protected static final String TARGET_BASEDIR = "/var/www/html";
    protected static final String CONTENT_CGI = "/cgi-bin/content.py";
    protected static final String SETUP_CGI = "/cgi-bin/setup.py";

    protected void executeSetup( String importsDirPath, String scriptName, CloseableHttpClient client )
            throws Exception
    {
        InputStream is = getResourceStream( importsDirPath, scriptName );

        String scriptResource = Paths.get( importsDirPath, scriptName ).toString();
        String url = formatUrl( SETUP_CGI );
        System.out.println( "\n\n ##### START: " + name.getMethodName() + " :: Setup script: " + scriptResource + " #####\n\n" );

        CloseableHttpResponse response = null;
        try
        {
            HttpPost post = new HttpPost( url );
            post.setEntity( new InputStreamEntity( is ) );
            response = client.execute( post );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            fail( String.format( "Failed to execute GET request: %s. Reason: %s", url, e.getMessage() ) );
        }

        String result = IOUtils.toString( response.getEntity().getContent() );

        System.out.println( result );

        int code = response.getStatusLine().getStatusCode();
        if ( code != 201 || code != 200 )
        {
            fail( "Failed to execute script: " + scriptResource + "\nSERVER RESPONSE STATUS: " + response.getStatusLine() );
        }
        else
        {
            System.out.println( "\n\n ##### END: " + name.getMethodName() + " :: Setup script: " + scriptResource + " #####\n\n" );
        }
    }

    protected void stageImport( String importsDirPath, CloseableHttpClient client )
            throws Exception
    {
        InputStream is = getResourceStream( importsDirPath, "staging.properties" );

        Properties p = new Properties();
        try
        {
            p.load( is );
        }
        finally
        {
            closeQuietly( is );
        }

        p.stringPropertyNames().forEach( (fname)->{
            String targetPath = p.getProperty( fname );
            if ( isEmpty( targetPath ) )
            {
                fail( "No target path for staging file: " + fname + "!" );
            }

            uploadFile( getResourceStream( importsDirPath, fname ), targetPath, client );
        });
    }

    protected void uploadFile( InputStream resourceStream, String targetPath, CloseableHttpClient client )
    {
        String url = formatUrl( CONTENT_CGI, targetPath );
        System.out.println( "\n\n ##### START: " + name.getMethodName() + " :: Upload -> " + url + " #####\n\n" );

        CloseableHttpResponse response = null;
        try
        {
            HttpPut put = new HttpPut( url );
            put.setEntity( new InputStreamEntity( resourceStream ) );
            response = client.execute( put );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            fail( String.format( "Failed to execute GET request: %s. Reason: %s", url, e.getMessage() ) );
        }

        int code = response.getStatusLine().getStatusCode();
        if ( code != 201 || code != 200 )
        {
            fail( "Failed to upload content: " + targetPath + "\nSERVER RESPONSE STATUS: " + response.getStatusLine() );
        }
        else
        {
            System.out.println( "\n\n ##### END: " + name.getMethodName() + " :: Upload -> " + url + " #####\n\n" );
        }
    }


    protected InputStream getResourceStream( String importsDirPath, String filename )
    {
        String resource = Paths.get( importsDirPath, filename ).toString();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream( resource );
        if ( is == null )
        {
            fail( "Cannot find classpath resource: " + resource );
        }

        return is;
    }
}
