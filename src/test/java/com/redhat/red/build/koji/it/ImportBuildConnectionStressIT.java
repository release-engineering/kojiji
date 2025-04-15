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
package com.redhat.red.build.koji.it;

import com.redhat.red.build.koji.KojiClient;
import com.redhat.red.build.koji.KojijiErrorInfo;
import com.redhat.red.build.koji.model.ImportFile;
import com.redhat.red.build.koji.model.KojiImportResult;
import com.redhat.red.build.koji.model.json.BuildContainer;
import com.redhat.red.build.koji.model.json.KojiImport;
import com.redhat.red.build.koji.model.json.StandardArchitecture;
import com.redhat.red.build.koji.model.json.StandardBuildType;
import com.redhat.red.build.koji.model.json.StandardChecksum;
import com.redhat.red.build.koji.model.json.VerificationException;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTaskInfo;
import com.redhat.red.build.koji.model.xmlrpc.messages.CreateTagRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by jdcasey on 1/14/16.
 */
public class ImportBuildConnectionStressIT
        extends AbstractIT
{

    private static final int MODULE_COUNT = 32;

    private static final int BUILD_COUNT = 16;

    private ExecutorService executor = Executors.newCachedThreadPool();

    private ExecutorCompletionService<Exception> completions = new ExecutorCompletionService<Exception>( executor );

    /* Ignore this for the same reason as ImportSimpleBuildIT */
    @Ignore
    @Test
    public void run()
            throws Exception
    {
        try ( KojiClient client = newKojiClient() )
        {
            for ( int i = 0; i < BUILD_COUNT; i++ )
            {
                final int idx = i;
                completions.submit( () ->
                                  {
                                      Thread.currentThread().setName( "Build-" + idx );
                                      try
                                      {
                                          runImport( client );
                                      }
                                      catch ( Exception e )
                                      {
                                          e.printStackTrace();
                                          //fail( "Failed to import build." );
                                          return e;
                                      }

                                      return null;
                                  } );
            }
        }

        int errorCount = 0;
        for(int i=0; i<BUILD_COUNT; i++)
        {
            Future<Exception> future = completions.take();
            Exception error = future.get();
            if ( error != null )
            {
                errorCount++;
            }
        }

        assertThat( errorCount, equalTo( 0 ) );
    }

    private void runImport( final KojiClient client )
            throws Exception
    {
        KojiSessionInfo session = client.login();

        String tagName = getClass().getSimpleName() + "-" + selectWords( "-", 3 );

        CreateTagRequest req = new CreateTagRequest();
        req.setTagName( tagName );

        client.createTag( req, session );

        ProjectVersionRef topGav = generateGAV();
        KojiImport.Builder importBuilder = initImport( topGav );

        boolean packageAdded = client.addPackageToTag( tagName, topGav, session );
        assertThat( packageAdded, equalTo( true ) );

        List<Supplier<ImportFile>> fileSuppliers =
                new ArrayList<>( Arrays.asList( addPom( topGav, importBuilder ), addJar( topGav, importBuilder ) ) );

        for ( int i = 1; i < MODULE_COUNT; i++ )
        {
            ProjectVersionRef moduleGav = generateGAV();
            fileSuppliers.add( addPom( moduleGav, importBuilder ) );
            fileSuppliers.add( addJar( moduleGav, importBuilder ) );
        }

        KojiImport importMetadata = importBuilder.build();

        System.out.printf( "Starting CGImport using client: %s\n metadata: %s\n fileSuppliers: %s\n session: %s",
                           client, importMetadata, fileSuppliers, session );

        KojiImportResult result = client.importBuild( importMetadata, fileSuppliers, session );

        Map<String, KojijiErrorInfo> uploadErrors = result.getUploadErrors();

        if ( uploadErrors != null && !uploadErrors.isEmpty() )
        {
            StringBuilder sb = new StringBuilder();
            sb.append( "The following upload errors occurred:\n\n" );
            uploadErrors.forEach( ( k, v ) -> sb.append( k ).append( ":\n\n  " ).append( v ).append( "\n\n" ) );
            fail( sb.toString() );
        }

        KojiBuildInfo buildInfo = result.getBuildInfo();
        assertThat( buildInfo, notNullValue() );

        Integer taskId = client.tagBuild( tagName, buildInfo.getNvr(), session );
        assertThat( taskId, notNullValue() );

        KojiTaskInfo taskInfo = client.getTaskInfo( taskId, session );

        System.out.println( "State of tag operation: " + taskInfo.getState() );
    }

    private static List<String> words;

    private static Random rand = new Random();

    @BeforeClass
    public static void loadWords()
            throws IOException
    {
        words = IOUtils.readLines( Thread.currentThread().getContextClassLoader().getResourceAsStream( "words" ), StandardCharsets.UTF_8 );
    }

    private Supplier<ImportFile> addPom( ProjectVersionRef gav, KojiImport.Builder importBuilder )
            throws IOException
    {
        String pomPath = String.format( "%s/%s/%s/%s-%s.pom", gav.getGroupId().replace( '.', '/' ), gav.getArtifactId(),
                                        gav.getVersionString(), gav.getArtifactId(), gav.getVersionString() );

        Model model = new Model();
        model.setModelVersion( "4.0.0" );
        model.setGroupId( gav.getGroupId() );
        model.setArtifactId( gav.getArtifactId() );
        model.setVersion( gav.getVersionString() );
        model.setPackaging( "jar" );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new MavenXpp3Writer().write( baos, model );

        byte[] pomBytes = baos.toByteArray();

        importBuilder.withNewOutput( 1, new File( pomPath ).getName() )
                     .withFileSize( pomBytes.length )
                     .withChecksum( StandardChecksum.md5.name(), DigestUtils.md5Hex( pomBytes ) )
                     //                                                 .withOutputType( StandardOutputType.maven )
                     .withMavenInfoAndType( gav )
                     .parent();

        return () -> new ImportFile( new File( pomPath ).getName(), new ByteArrayInputStream( pomBytes ),
                                     pomBytes.length );
    }

    private Supplier<ImportFile> addJar( ProjectVersionRef gav, KojiImport.Builder importBuilder )
            throws IOException
    {
        String jarPath = String.format( "%s/%s/%s/%s-%s.jar", gav.getGroupId().replace( '.', '/' ), gav.getArtifactId(),
                                        gav.getVersionString(), gav.getArtifactId(), gav.getVersionString() );

        byte[] jarBytes = new byte[16384];
        rand.nextBytes( jarBytes );

        importBuilder.withNewOutput( 1, new File( jarPath ).getName() )
                     .withFileSize( jarBytes.length )
                     .withChecksum( StandardChecksum.md5.name(), DigestUtils.md5Hex( jarBytes ) )
                     //                                                 .withOutputType( StandardOutputType.maven )
                     .withMavenInfoAndType( gav )
                     .parent();

        return () -> new ImportFile( new File( jarPath ).getName(), new ByteArrayInputStream( jarBytes ),
                                     jarBytes.length );
    }

    private ProjectVersionRef generateGAV()
            throws IOException, VerificationException
    {
        String group = selectWords( ".", 2 );
        String artifact = selectWords( null, 1 );
        String version = selectNumeric( new ArrayList<>( Arrays.asList( ".", "." ) ), 3 );

        return new SimpleProjectVersionRef( group, artifact, version );
    }

    private KojiImport.Builder initImport( ProjectVersionRef gav )
    {
        KojiImport.Builder importBuilder = new KojiImport.Builder();

        int brId = 1;
        Date start = new Date( System.currentTimeMillis() - 86400 );
        Date end = new Date( System.currentTimeMillis() - 43200 );

        importBuilder.withNewBuildDescription( gav )
                     //        KojiImport importMetadata = importBuilder.withNewBuildDescription( "org.foo-bar", "1.1", "1" )
                     //                                                 .withBuildType( StandardBuildType.maven )
                     .withExternalBuildId( String.format( "%s-%s-%s", gav.getGroupId(), gav.getArtifactId(),
                                                          gav.getVersionString() ) )
                     .withStartTime( start )
                     .withEndTime( end )
                     .withBuildSource( "http://build.foo.com" )
                     .parent()
                     .withNewBuildRoot( brId )
                     .withTool( "apache-maven", "3.2.1" )
                     .withContentGenerator( "test-cg", "1.0" )
                     .withContainer(
                             new BuildContainer( StandardBuildType.maven.name(), StandardArchitecture.noarch.name() ) )
                     .withHost( "linux", StandardArchitecture.x86_64 )
                     .parent();

        return importBuilder;
    }

    private String selectNumeric( final List<String> sep, final int count )
    {
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < count; i++ )
        {
            if ( i > 0 )
            {
                sb.append( sep.remove( 0 ) );
            }

            sb.append( Math.abs( rand.nextInt( 100 ) ) );
        }

        return sb.toString();
    }

    private String selectWords( final String sep, final int count )
    {
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < count; i++ )
        {
            if ( i > 0 )
            {
                sb.append( sep );
            }

            sb.append( Math.abs( rand.nextInt( words.size() ) ) );
        }

        return sb.toString();
    }

    private class ProjectInfo
    {
        private ProjectVersionRef gav;

        private KojiImport.Builder importBuilder;

        private ProjectInfo( ProjectVersionRef gav, KojiImport.Builder importBuilder )
        {
            this.gav = gav;
            this.importBuilder = importBuilder;
        }
    }
}
