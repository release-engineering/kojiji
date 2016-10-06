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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.redhat.red.build.koji.model.json.KojiJsonConstants;
import com.redhat.red.build.koji.model.json.TypeInfo;

import java.io.IOException;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 10/6/16
 * Time: 10:31 AM
 */
public class TypeInfoSerializer<T extends TypeInfo>
        extends StdSerializer<T> {

    protected TypeInfoSerializer(Class<T> cls )
    {
        super( cls );
    }

    @Override
    public void serialize( TypeInfo value, JsonGenerator jgen, SerializerProvider provider )
            throws IOException, JsonGenerationException
    {
        if ( value instanceof TypeInfo )
        {
            jgen.writeStartObject();
            jgen.writeFieldName( KojiJsonConstants.TYPE_INFO );
            provider.defaultSerializeValue( ((TypeInfo) value).getMavenExtraInfo(), jgen );
            jgen.writeEndObject();
        }
        else
        {
            throw new JsonGenerationException( "Unknown BuildExtraInfo type: " + value.getClass().getName() );
        }
    }
}
