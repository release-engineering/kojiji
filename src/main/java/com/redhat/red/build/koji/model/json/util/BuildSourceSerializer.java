package com.redhat.red.build.koji.model.json.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.redhat.red.build.koji.model.json.BuildSource;

import java.io.IOException;

/**
 * Created by jdcasey on 2/10/16.
 */
public class BuildSourceSerializer
        extends StdSerializer<BuildSource>
{

    public BuildSourceSerializer()
    {
        super( BuildSource.class );
    }

    @Override
    public void serialize( BuildSource value, JsonGenerator jgen, SerializerProvider provider )
            throws IOException, JsonGenerationException
    {
        jgen.writeString( String.format("%s#%s", value.getUrl(), value.getRevision() ) );
    }
}
