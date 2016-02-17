package com.redhat.red.build.koji.model.json.util;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by jdcasey on 2/16/16.
 */
public class KojiJsonException
        extends JsonProcessingException
{
    public KojiJsonException( String message )
    {
        super( message );
    }

    public KojiJsonException( String message, JsonLocation location )
    {
        super( message, location );
    }
}
