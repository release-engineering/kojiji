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

import com.fasterxml.jackson.core.JsonProcessingException;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 2/17/16.
 */
public class BuildOutputTest
    extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip_MavenFile()
            throws VerificationException, IOException
    {
        BuildOutput src = newBuildOutput( 1001, "foo.txt" );

        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        BuildOutput out = mapper.readValue( json, BuildOutput.class );

        assertThat( out, equalTo( src ) );
    }

    @Test
    public void jsonRoundTrip_LogFile()
            throws VerificationException, IOException
    {
        BuildOutput src = newLogOutput( 1001, "build.log" );

        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        BuildOutput out = mapper.readValue( json, BuildOutput.class );

        assertThat( out, equalTo( src ) );
    }

}
