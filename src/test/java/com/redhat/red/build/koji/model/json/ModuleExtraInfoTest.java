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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.red.build.koji.model.json.util.KojiObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModuleExtraInfoTest
{
    private static final ObjectMapper MAPPER = new KojiObjectMapper();

    @Test
    public void testParsing() throws IOException
    {
        URL url = ModuleExtraInfoTest.class.getResource( "/module-extra.json" );
        assertThat( url, notNullValue() );
        BuildExtraInfo extra;
        try ( InputStream in = url.openStream() )
        {
            extra = MAPPER.readValue( in, BuildExtraInfo.class );
        }
        assertThat( extra, notNullValue() );
        TypeInfoExtraInfo typeInfo = extra.getTypeInfo();
        assertThat( typeInfo, notNullValue() );
        ModuleExtraInfo module = typeInfo.getModuleExtraInfo();
        assertThat( module, notNullValue() );
        assertThat( module.getModuleBuildServiceId(), equalTo( 12345 ) );
        assertThat( module.getContentKojiTag(), equalTo( "module-testmodule-private_user_rhel_8.1.0-8010020260821115618-a01ab0a2" ) );
        assertThat( module.getModulemdStr(), equalTo( "---\ndocument: modulemd\nversion: 2\ndata:\n  name: testmodule\n  stream: \"private_user_rhel_8.1.0\"\n" ) );
        assertThat( module.getName(), equalTo( "testmodule" ) );
        assertThat( module.getStream(), equalTo( "private_user_rhel_8.1.0" ) );
        assertThat( module.getVersion(), equalTo( "8010020260821115618" ) );
        assertThat( module.getContext(), equalTo( "a01ab0a2" ) );
    }
}
