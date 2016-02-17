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

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by jdcasey on 2/10/16.
 */
public class MavenGAVDeserializer
        extends StdDeserializer<SimpleProjectVersionRef>
{
    public MavenGAVDeserializer( Class<? extends ProjectVersionRef> cls )
    {
        super( cls );
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
                    case ( "group_id" ):
                    {
                        g = jp.getText();
                        break;
                    }
                    case ( "artifact_id" ):
                    {
                        a = jp.getText();
                        break;
                    }
                    case ( "version" ):
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
