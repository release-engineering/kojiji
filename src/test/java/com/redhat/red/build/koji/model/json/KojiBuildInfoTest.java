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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;

public class KojiBuildInfoTest
        extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip()
            throws VerificationException, IOException
    {
        KojiBuildInfo info = new KojiBuildInfo();
        info.setId( 559800 );
        info.setTaskId( 13261948 );
        info.setName( "org.fusesource.hawtjni-hawtjni-project" );
        info.setVersion( "1.15.0.redhat_1" );
        info.setRelease( "1" );

        String json = mapper.writeValueAsString( info );
        System.out.println( json );

        KojiBuildInfo out = mapper.readValue( json, KojiBuildInfo.class );

        assertThat( out.toString(), equalTo( info.toString() ) );
    }
}
