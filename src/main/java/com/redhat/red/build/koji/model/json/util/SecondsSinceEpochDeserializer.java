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
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang.StringUtils.isEmpty;

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
