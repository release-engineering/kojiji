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
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ARTIFACT_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.GROUP_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.VERSION;
import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by jdcasey on 2/10/16.
 */
public class MavenGAVDeserializer
        extends StdDeserializer<SimpleProjectVersionRef>
{
    public MavenGAVDeserializer()
    {
        super( SimpleProjectVersionRef.class );
    }

    @Override
    public SimpleProjectVersionRef deserialize( JsonParser jp, DeserializationContext ctxt )
            throws IOException, JsonProcessingException
    {
        String g = null;
        String a = null;
        String v = null;

        JsonToken token = null;
        while ( ( token = jp.nextToken() ) != JsonToken.END_OBJECT )
        {
            if ( token == JsonToken.VALUE_STRING )
            {
                String field = jp.getCurrentName();
                switch ( field )
                {
                    case ( GROUP_ID ):
                    {
                        g = jp.getText();
                        break;
                    }
                    case ( ARTIFACT_ID ):
                    {
                        a = jp.getText();
                        break;
                    }
                    case ( VERSION ):
                    {
                        v = jp.getText();
                        break;
                    }
                    default:
                    {
                        Logger logger = LoggerFactory.getLogger( getClass() );
                        logger.debug( "Ignoring unknown field: {}", field );
                    }
                }
            }
        }

        if ( isEmpty( g ) || isEmpty( a ) || isEmpty( v ) )
        {
            throw new KojiJsonException( "Invalid GAV: " + g + ":" + a + ":" + v );
        }

        return new SimpleProjectVersionRef( g, a, v );
    }
}
