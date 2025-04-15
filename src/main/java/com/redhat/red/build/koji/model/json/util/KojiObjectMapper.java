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
package com.redhat.red.build.koji.model.json.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by jdcasey on 2/16/16.
 */
public class KojiObjectMapper
        extends ObjectMapper
{

    @SuppressWarnings( "deprecation" )
    public KojiObjectMapper()
    {
        setSerializationInclusion( JsonInclude.Include.NON_EMPTY );
        configure( JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, true );

        enable( SerializationFeature.INDENT_OUTPUT, SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID );

        enable( MapperFeature.AUTO_DETECT_FIELDS );
        //        disable( MapperFeature.AUTO_DETECT_GETTERS );

        disable( SerializationFeature.WRITE_NULL_MAP_VALUES, SerializationFeature.WRITE_EMPTY_JSON_ARRAYS );

        disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );

        disable( SerializationFeature.FAIL_ON_EMPTY_BEANS );

        registerModule( new KojiJsonModule() );
    }
}
