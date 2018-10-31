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
package com.redhat.red.build.koji.model.json;

import com.redhat.red.build.koji.model.generated.Model_Registry;
import com.redhat.red.build.koji.model.json.util.KojiObjectMapper;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.core.Registry;
import org.junit.BeforeClass;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by jdcasey on 2/16/16.
 */
public class AbstractJsonTest
{

    protected SimpleProjectVersionRef mainGav = new SimpleProjectVersionRef( "group.id", "artifact-id", "ver.sio.n" );

    protected KojiObjectMapper mapper = new KojiObjectMapper();

    protected BuildSource newBuildSource()
    {
        BuildSource src = new BuildSource( "https://github.com/release-engineering/kojiji" );
        src.setRevision( "abcdefg" );
        return src;
    }

    protected BuildDescription newBuildDescription()
            throws VerificationException
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime( new Date( System.currentTimeMillis() - 30000 ) );
        cal.set( Calendar.MILLISECOND, 0 );

        BuildDescription.Builder builder =
                new BuildDescription.Builder( String.format( "%s-%s", mainGav.getGroupId(), mainGav.getArtifactId() ),
                                              mainGav.getVersionString() ).withStartTime(
                        cal.getTime() );

        cal.setTime( new Date() );
        cal.set( Calendar.MILLISECOND, 0 );

        BuildDescription src = builder.withEndTime( cal.getTime() )
                                                                          .withBuildSource( newBuildSource() )
                                                                          .build();

        src.setExtraInfo( new BuildExtraInfo( mainGav ) );
        return src;
    }

    protected BuildHost newBuildHost()
    {
        return new BuildHost( "rhel-7", "x86_64" );
    }

    protected BuildTool newBuildTool( String name, String version )
    {
        return new BuildTool( name, version );
    }

    protected BuildContainer newBuildContainer( String type, String arch )
    {
        return new BuildContainer( type, arch );
    }

    protected BuildRoot newBuildRoot()
            throws VerificationException
    {
        BuildRoot root = new BuildRoot.Builder( 1 ).withContainer( newBuildContainer( "docker", "x86_64" ) )
                                                   .withHost( newBuildHost() )
                                                   .withTools( Arrays.asList( newBuildTool( "docker", "1.5.0" ),
                                                                              newBuildTool( "maven", "3.3.1" ) )
                                                                     .stream()
                                                                     .collect( Collectors.toSet() ) )
                                                   .withContentGenerator( "test-cg", "0.8" )
                                                   .withExtraInfo( "test-cg",
                                                                   Collections.singletonMap( "buildId", 1001 ) )
                                                   .build();

        return root;
    }

    protected BuildOutput newBuildOutput( int buildrootId, String filename )
            throws VerificationException
    {
        return new BuildOutput.Builder( buildrootId, filename ).withArch( StandardArchitecture.noarch )
                                                               .withChecksum( "md5", "aaffddcceeddaa" )
                                                               .withFileSize( 12345 )
                                                               .withMavenInfoAndType(
                                                                       new SimpleProjectVersionRef( "org.foo", "bar",
                                                                                                    "1.0" ) )
                                                               .build();
    }

    protected BuildOutput newLogOutput( int buildrootId, String filename )
            throws VerificationException
    {
        return new BuildOutput.Builder( buildrootId, filename ).withArch( StandardArchitecture.noarch )
                                                               .withChecksum( "md5", "aaffddcceeddaa" )
                                                               .withFileSize( 12345 )
                                                               .withOutputType( StandardOutputType.log )
                                                               .build();
    }
}
