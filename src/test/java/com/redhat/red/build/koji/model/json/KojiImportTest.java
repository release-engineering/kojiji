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
package com.redhat.red.build.koji.model.json;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jdcasey on 2/17/16.
 */
public class KojiImportTest
        extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip()
            throws VerificationException, IOException
    {
        KojiImport info = new KojiImport( KojiJsonConstants.DEFAULT_METADATA_VERSION, newBuildDescription(),
                                          Arrays.asList( newBuildRoot() ),
                                          Arrays.asList( newBuildOutput( 1001, "foo-1.jar" ),
                                                         newLogOutput( 1001, "build.log" ) )
                                                .stream()
                                                .collect( Collectors.toList() ) );

        String json = mapper.writeValueAsString( info );
        System.out.println( json );

        KojiImport out = mapper.readValue( json, KojiImport.class );

        assertThat( out.getBuild(), equalTo( info.getBuild() ) );
    }

}
