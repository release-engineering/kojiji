package com.redhat.red.build.koji.model.converter;

import com.redhat.red.build.koji.model.json.BuildSource;
import org.commonjava.rwx.core.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by ruhan on 8/11/17.
 */
public class KojiBuildSourceConverter implements Converter<BuildSource>
{
    Logger logger = LoggerFactory.getLogger( getClass() );

    @Override
    public BuildSource parse( Object object )
    {
        if ( object == null )
        {
            return null;
        }
        String[] parts = String.valueOf( object ).split( "#" );
        if ( parts.length < 2 || isEmpty( parts[0] ) || isEmpty( parts[1] ) )
        {
            logger.warn("Invalid build-source: '" + object + "'. Must be of format '<base-url>#<commit-ish>'");
        }
        BuildSource source = new BuildSource( parts[0] );
        if ( parts.length > 1 )
        {
            source.setRevision( parts[1] );
        }
        return source;
    }

    @Override
    public Object render( BuildSource value )
    {
        if ( value == null )
        {
            return null;
        }
        String rev = value.getRevision();
        if ( rev == null )
        {
            rev = "master";
        }
        return value.getUrl() + "#" + rev;
    }
}
