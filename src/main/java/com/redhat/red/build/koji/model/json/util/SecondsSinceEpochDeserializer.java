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

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jdcasey on 2/10/16.
 */
public class SecondsSinceEpochDeserializer
        extends StdDeserializer<Date>
{
    public SecondsSinceEpochDeserializer()
    {
        super( Date.class );
    }

    @Override
    public Date deserialize( JsonParser jp, DeserializationContext ctxt )
            throws IOException, JsonProcessingException
    {
        long val = jp.getLongValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime( new Date( val * 1000L ) );
        cal.set( Calendar.MILLISECOND, 0 );

        return cal.getTime();
    }
}
