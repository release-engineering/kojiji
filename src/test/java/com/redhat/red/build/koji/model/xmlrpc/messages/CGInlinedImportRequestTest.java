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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.json.BuildContainer;
import com.redhat.red.build.koji.model.json.KojiImport;
import com.redhat.red.build.koji.model.json.StandardArchitecture;
import com.redhat.red.build.koji.model.json.StandardBuildType;
import com.redhat.red.build.koji.model.json.StandardChecksum;
import com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcBindery;
import org.apache.commons.codec.digest.DigestUtils;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.impl.TrackingXmlRpcListener;
import org.commonjava.rwx.impl.estream.EventStreamParserImpl;
import org.commonjava.rwx.impl.jdom.JDomRenderer;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Test;

import java.io.File;
import java.util.Date;

import static com.redhat.red.build.koji.testutil.TestResourceUtils.readTestResourceBytes;
import static org.apache.commons.lang.StringUtils.join;

/**
 * Created by jdcasey on 9/16/16.
 */
public class CGInlinedImportRequestTest
{
    @Test
    public void xmlRpcRender()
            throws Exception
    {
        ProjectVersionRef gav = new SimpleProjectVersionRef( "org.foo", "bar", "1.1" );

        Date start = new Date( System.currentTimeMillis() - 86400 );
        Date end = new Date( System.currentTimeMillis() - 43200 );

        KojiImport.Builder importBuilder = new KojiImport.Builder();

        int brId = 1;
        String pomPath = "org/foo/bar/1.1/bar-1.1.pom";
        byte[] pomBytes = readTestResourceBytes( "import-data/" + pomPath );

        KojiImport importMetadata = importBuilder.withNewBuildDescription( gav )
                                                 .withStartTime( start )
                                                 .withEndTime( end )
                                                 .withBuildSource( "http://builder.foo.com", "1.0" )
                                                 .parent()
                                                 .withNewBuildRoot( brId )
                                                 .withContentGenerator( "test-cg", "1.0" )
                                                 .withContainer( new BuildContainer( StandardBuildType.maven.name(),
                                                                                     StandardArchitecture.noarch.name() ) )
                                                 .withHost( "linux", StandardArchitecture.x86_64 )
                                                 .parent()
                                                 .withNewOutput( 1, new File( pomPath ).getName() )
                                                 .withFileSize( pomBytes.length )
                                                 .withChecksum( StandardChecksum.md5.name(), DigestUtils.md5Hex( pomBytes ) )
                                                 .withOutputType( "pom" )
                                                 .parent()
                                                 .build();

        CGInlinedImportRequest req = new CGInlinedImportRequest( importMetadata, "mydir" );

        JDomRenderer jdom = new JDomRenderer( new XMLOutputter( Format.getPrettyFormat() ) );
        TrackingXmlRpcListener tracker = new TrackingXmlRpcListener( jdom );
        EventStreamParserImpl eventParser = new EventStreamParserImpl( tracker );

        new KojiXmlRpcBindery().render( eventParser, req );

        System.out.printf( "Calls:\n\n  %s\n\nEvent tree:\n\n%s\n\nXML:\n\n%s\n\nJDom Calls & Changes:\n\n%s\n\n", join( tracker.getCalls(), "\n  "), eventParser.renderEventTree(), jdom.documentToString(), jdom.renderCallsAndChanges() );
    }
}
