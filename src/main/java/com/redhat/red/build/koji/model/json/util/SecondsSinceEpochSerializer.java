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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jdcasey on 2/10/16.
 */
public class SecondsSinceEpochSerializer
        extends StdSerializer<Date>
{

    protected SecondsSinceEpochSerializer()
    {
        super( Date.class );
    }

    @Override
    public void serialize( Date value, JsonGenerator jgen, SerializerProvider provider )
            throws IOException, JsonGenerationException
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime( value );
        cal.set( Calendar.MILLISECOND, 0 );
        jgen.writeNumber( cal.getTime().getTime() / 1000L );
    }
}
