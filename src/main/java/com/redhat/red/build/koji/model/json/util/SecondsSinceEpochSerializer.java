package com.redhat.red.build.koji.model.json.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;

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
