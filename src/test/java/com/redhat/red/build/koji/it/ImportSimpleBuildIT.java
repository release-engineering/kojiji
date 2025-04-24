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
import com.redhat.red.build.koji.KojijiErrorInfo;
import com.redhat.red.build.koji.model.ImportFile;
import com.redhat.red.build.koji.model.KojiImportResult;
import com.redhat.red.build.koji.model.json.StandardChecksum;
import com.redhat.red.build.koji.model.json.BuildContainer;
import com.redhat.red.build.koji.model.json.KojiImport;
import com.redhat.red.build.koji.model.json.StandardArchitecture;
import com.redhat.red.build.koji.model.json.StandardBuildType;
import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveType;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTaskInfo;
import com.redhat.red.build.koji.model.xmlrpc.messages.CreateTagRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.redhat.red.build.koji.testutil.TestResourceUtils.readTestResourceBytes;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by jdcasey on 1/14/16.
 */
public class ImportSimpleBuildIT
        extends AbstractIT
{

    /**
     * Something must have broken in latest Koji. When we call addPackageToTag -> ListPackagesRequest,
     * the args for kojihub.py listPackages(...) must have changed, and it does not accept a dict type now.
     *
     * We got error:
     * ...
     * File "/usr/share/koji-hub/kojihub.py", line 2799, in lookup_name
     * raise koji.GenericError('invalid type for id lookup: %s' % type(info))
     * GenericError: invalid type for id lookup: &lt;type 'dict'&gt;
     *
     * The cause:
     *
     * Frame call_with_argcheck in /usr/lib/python2.7/site-packages/koji/util.py at line 212
     * args = {'pkgID': None, 'tagID': 1, 'userID': 2, 'prefix': None, 'inherited': None, 'event': None}
     * func = RootExports.listPackages
     *
     * It calls func(*args, **kwargs), pass the *args to listPackages. Bug the latter can not handle the args correctly.
     *
     * Frame listPackages in /usr/share/koji-hub/kojihub.py at line 10001
     * pkgID = None
     * userID = None
     * tagID = {'pkgID': None, 'tagID': 1, 'userID': 2, 'prefix': None, 'inherited': None, 'event': None}
     * ...
     *
     * Refer to the method signature:
     * def listPackages(self, tagID=None, userID=None, pkgID=None, prefix=None, inherited=False, with_dups=False, event=None, queryOpts=None)
     *
     * If I skip ListPackagesRequest in KojiClient addPackageToTag, this case can pass.
     *
     * I ignore this case for now. --henry 2017-8-22
     */
    @Ignore
    @Test
    public void run()
            throws Exception
    {
        KojiClient client = newKojiClient();
        KojiSessionInfo session = client.login();

        String tagName = getClass().getSimpleName();
        CreateTagRequest req = new CreateTagRequest();
        req.setTagName( tagName );

        client.createTag( req, session );

        ProjectVersionRef gav = new SimpleProjectVersionRef( "org.foo", "bar", "1.1" );

        boolean packageAdded;
        try
        {
            packageAdded = client.addPackageToTag( tagName, gav, session );
        }
        catch ( KojiClientException e )
        {
            if ( e.getCause() != null )
            {
                e.getCause().printStackTrace();
            }
            throw e;
        }
        assertThat( packageAdded, equalTo( true ) );

        Map<String, KojiArchiveType> archiveTypes = client.getArchiveTypeMap( session );

        KojiArchiveType pomType = archiveTypes.get( "pom" );

        Date start = new Date( System.currentTimeMillis() - 86400 );
        Date end = new Date( System.currentTimeMillis() - 43200 );

        KojiImport.Builder importBuilder = new KojiImport.Builder();

        int brId = 1;
        String pomPath = "org/foo/bar/1.1/bar-1.1.pom";
        byte[] pomBytes = readTestResourceBytes( "import-data/" + pomPath );

        KojiImport importMetadata = importBuilder.withNewBuildDescription( gav )
//        KojiImport importMetadata = importBuilder.withNewBuildDescription( "org.foo-bar", "1.1", "1" )
//                                                 .withBuildType( StandardBuildType.maven )
                                                 .withExternalBuildId( "foo-bar-1.1" )
                                                 .withStartTime( start )
                                                 .withEndTime( end )
                                                 .withBuildSource( "http://build.foo.com" )
                                                 .parent()
                                                 .withNewBuildRoot( brId )
                                                 .withTool( "apache-maven", "3.2.1" )
                                                 .withContentGenerator( "test-cg", "1.0" )
                                                 .withContainer( new BuildContainer( StandardBuildType.maven.name(),
                                                                                     StandardArchitecture.noarch.name() ) )
                                                 .withHost( "linux", StandardArchitecture.x86_64 )
                                                 .parent()
                                                 .withNewOutput( 1, new File( pomPath ).getName() )
                                                 .withFileSize( pomBytes.length )
                                                 .withChecksum( StandardChecksum.md5.name(), DigestUtils.md5Hex( pomBytes ) )
//                                                 .withOutputType( StandardOutputType.maven )
                                                 .withMavenInfoAndType( gav )
                                                 .parent()
                                                 .build();

        List<Supplier<ImportFile>> fileSuppliers = Arrays.asList(
                ()->new ImportFile( new File(pomPath).getName(), new ByteArrayInputStream( pomBytes ), pomBytes.length )
        );

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
}
