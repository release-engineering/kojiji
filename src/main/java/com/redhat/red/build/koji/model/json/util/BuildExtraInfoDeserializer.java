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
package com.redhat.red.build.koji.model.json.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.redhat.red.build.koji.model.json.BuildExtraInfo;
import com.redhat.red.build.koji.model.json.MavenExtraInfo;

import java.io.IOException;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.MAVEN_INFO;

/**
 * Created by jdcasey on 2/10/16.
 */
public class BuildExtraInfoDeserializer
        extends StdDeserializer<BuildExtraInfo>
{
    public BuildExtraInfoDeserializer()
    {
        super( BuildExtraInfo.class );
    }

    @Override
    public BuildExtraInfo deserialize( JsonParser jp, DeserializationContext ctxt )
            throws IOException, JsonProcessingException
    {
        JsonToken token = null;
        while ( ( token = jp.nextToken() ) != JsonToken.END_OBJECT )
        {
            if ( token == JsonToken.START_OBJECT )
            {
                String field = jp.getCurrentName();
                switch ( field )
                {
                    case ( MAVEN_INFO ):
                    {
                        JsonDeserializer<Object> mvnDeser =
                                ctxt.findRootValueDeserializer( ctxt.constructType( MavenExtraInfo.class ) );

                        MavenExtraInfo mvnInfo = (MavenExtraInfo) mvnDeser.deserialize( jp, ctxt );
                        return new BuildExtraInfo( mvnInfo );
                    }
                    default:
                    {
                        throw new KojiJsonException( "Unknown extra_info type: " + field, jp.getCurrentLocation() );
                    }
                }
            }
        }

        return null;
    }
}
