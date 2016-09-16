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
import com.redhat.red.build.koji.model.json.FileExtraInfo;
import com.redhat.red.build.koji.model.json.KojiJsonConstants;

import java.io.IOException;

/**
 * Created by jdcasey on 2/10/16.
 */
public class FileExtraInfoSerializer<T extends FileExtraInfo>
        extends StdSerializer<T>
{

    protected FileExtraInfoSerializer( Class<T> cls )
    {
        super( cls );
    }

    @Override
    public void serialize( FileExtraInfo value, JsonGenerator jgen, SerializerProvider provider )
            throws IOException, JsonGenerationException
    {
        if ( value instanceof FileExtraInfo )
        {
            jgen.writeStartObject();
            jgen.writeFieldName( KojiJsonConstants.MAVEN_INFO );
            provider.defaultSerializeValue( ((FileExtraInfo) value).getMavenExtraInfo(), jgen );
            jgen.writeEndObject();
        }
        else
        {
            throw new JsonGenerationException( "Unknown FileExtraInfo type: " + value.getClass().getName() );
        }
    }
}
