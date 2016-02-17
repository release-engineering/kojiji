package com.redhat.red.build.koji.model.json.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;

import java.io.IOException;

/**
 * Created by jdcasey on 2/10/16.
 */
public class MavenGAVSerializer<T extends ProjectVersionRef>
        extends StdSerializer<T>
{

    protected MavenGAVSerializer( Class<T> cls )
    {
        super( cls );
    }

    @Override
    public void serialize( ProjectVersionRef value, JsonGenerator jgen, SerializerProvider provider )
            throws IOException, JsonGenerationException
    {
        jgen.writeStartObject();
        jgen.writeStringField( "group_id", value.getGroupId() );
        jgen.writeStringField( "artifact_id", value.getArtifactId() );
        jgen.writeStringField( "version", value.getVersionString() );
        jgen.writeEndObject();
    }
}
