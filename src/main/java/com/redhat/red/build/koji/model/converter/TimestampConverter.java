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
package com.redhat.red.build.koji.model.converter;

import org.commonjava.rwx.core.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ruhan on 8/11/17.
 */
public class TimestampConverter
                implements Converter<Date>
{
    Logger logger = LoggerFactory.getLogger( getClass() );

    //2015-02-24 16:03:34.451205
    private static final String TS_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date parse( Object object )
    {
        if ( object == null )
        {
            return null;
        }
        try
        {
            String s = String.valueOf( object );
            int idx = s.indexOf( "." );
            if ( idx > 0 )
            {
                s = s.substring( 0, idx ); // Date does not support nanosecond
            }
            return new SimpleDateFormat( TS_FORMAT ).parse( s );
        }
        catch ( ParseException e )
        {
            logger.debug( "Failed to parse timestamp: {} using format: {}", object, TS_FORMAT );
            return null;
        }
    }

    @Override
    public Object render( Date value )
    {
        if ( value == null )
        {
            return null;
        }
        return new SimpleDateFormat( TS_FORMAT ).format( value );
    }
}
