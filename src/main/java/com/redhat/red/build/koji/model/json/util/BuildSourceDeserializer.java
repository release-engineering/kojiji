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
package com.redhat.red.build.koji.model.json.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.redhat.red.build.koji.model.json.BuildSource;

import java.io.IOException;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by jdcasey on 2/10/16.
 */
public class BuildSourceDeserializer
        extends StdDeserializer<BuildSource>
{
    public BuildSourceDeserializer()
    {
        super( BuildSource.class );
    }

    @Override
    public BuildSource deserialize( JsonParser jp, DeserializationContext ctxt )
            throws IOException, JsonProcessingException
    {
        String urlAndRev = jp.getText();
        String[] parts = urlAndRev.split( "#" );

        if ( parts.length < 2 || isEmpty( parts[0] ) || isEmpty( parts[1] ) )
        {
            throw new KojiJsonException(
                    "Invalid build-source: '" + urlAndRev + "'. Must be of format '<base-url>#<commit-ish>'",
                    jp.getCurrentLocation() );
        }

        BuildSource source = new BuildSource( parts[0] );
        if ( parts.length > 1 )
        {
            source.setRevision( parts[1] );
        }

        return source;
    }
}
