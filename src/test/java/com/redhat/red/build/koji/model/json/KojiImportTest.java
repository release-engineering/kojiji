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
package com.redhat.red.build.koji.model.json;

import com.redhat.red.build.koji.model.xmlrpc.KojiBtype;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jdcasey on 2/17/16.
 */
public class KojiImportTest
        extends AbstractJsonTest
{
    @Test
    public void jsonRoundTripMaven()
            throws VerificationException, IOException
    {
        KojiImport info = new KojiImport( KojiJsonConstants.DEFAULT_METADATA_VERSION, newBuildDescription(),
                Collections.singletonList( newBuildRoot() ),
                new ArrayList<>( Arrays.asList(newBuildOutput( 1001, "foo-1.jar" ),
                        newLogOutput( 1001, "build.log" ) ) ) );

        String json = mapper.writeValueAsString( info );
        System.out.println( json );

        KojiImport out = mapper.readValue( json, KojiImport.class );

        assertThat( out.getBuild(), equalTo( info.getBuild() ) );
    }

    @Test
    public void jsonRoundTripRpm()
            throws VerificationException, IOException
    {
        BuildOutput rpm = new BuildOutput.Builder( 1001, "apache-sshd-2.14.0-3.redhat_00005.1.el8eap.noarch.rpm" ).withArch( StandardArchitecture.noarch )
            .withChecksum( "md5", "aaffddcceeddaa" )
            .withFileSize( 12345 )
            .withRpmInfoAndType()
            .build();
        BuildOutput srcRpm = new BuildOutput.Builder( 1001, "apache-sshd-2.14.0-3.redhat_00005.1.el8eap.src.rpm" ).withArch( StandardArchitecture.src )
                .withChecksum( "md5", "aaffddcceeddab" )
                .withFileSize( 123456 )
                .withRpmInfoAndType()
                .build();
        List<BuildOutput> outputs = new ArrayList<>();
        outputs.add(rpm);
        outputs.add(srcRpm);
        outputs.add(newLogOutput( 1001, "build.log" ) );

        KojiImport info = new KojiImport( KojiJsonConstants.DEFAULT_METADATA_VERSION, newBuildDescription(KojiBtype.rpm),
                Collections.singletonList( newBuildRoot() ),
                outputs);

        String json = mapper.writeValueAsString( info );
        System.out.println( json );

        KojiImport out = mapper.readValue( json, KojiImport.class );

        assertThat( out.getBuild(), equalTo( info.getBuild() ) );
    }
}
