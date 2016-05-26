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
package com.redhat.red.build.koji.model.util;

import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import org.commonjava.rwx.binding.mapping.Mapping;
import org.commonjava.rwx.binding.spi.Binder;
import org.commonjava.rwx.binding.spi.BindingContext;
import org.commonjava.rwx.binding.spi.value.CustomValueBinder;
import org.commonjava.rwx.error.XmlRpcException;
import org.commonjava.rwx.spi.XmlRpcListener;
import org.commonjava.rwx.vocab.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import static com.redhat.red.build.koji.model.util.DateUtils.toUTC;

/**
 * Created by jdcasey on 1/14/16.
 */
public class TimestampValueBinder
        extends CustomValueBinder
{
    private static final String TS_FORMAT = "yyyy-MM-dd hh:mm:ss ZZZ";

    public TimestampValueBinder( Binder parent, Class<?> type, BindingContext context )
    {
        super( parent, type, context );
    }

    @Override
    public void generate( XmlRpcListener listener, Object value, Map<Class<?>, Mapping<?>> recipes )
            throws XmlRpcException
    {
        Logger logger = LoggerFactory.getLogger( getClass() );
        if ( value == null )
        {
            logger.debug( "Generating nil" );
            listener.value( null, ValueType.NIL );
            return;
        }
        else if ( value instanceof Date )
        {
            Date d = (Date) value;
            listener.value( Long.toString( toUTC( d ).getTime() ) + ".00000", ValueType.STRING );
        }
        else
        {
            throw new XmlRpcException( "Invalid value type: {} for converter: {} (expects: {} or a subclass)", value.getClass().getName(),
                                       getClass().getName(), Date.class.getName() );
        }
    }

    @Override
    public Object getResult( Object value, ValueType type )
            throws XmlRpcException
    {
        if ( value == null )
        {
            return null;
        }

        Logger logger = LoggerFactory.getLogger( getClass() );
        logger.debug( "Setting value: {} (type: {})", value, type );
        logger.debug( "Setting value on parent: {}", getParent() );

        String[] parts = String.valueOf( value ).split( "\\." );
        if ( parts.length != 2 )
        {
            throw new XmlRpcException( "Invalid timestamp: '%s'", value );
        }

        try
        {
            return new SimpleDateFormat( TS_FORMAT ).parse( parts[0] + " UTC" );
        }
        catch ( ParseException e )
        {
            throw new XmlRpcException( "Unparseable timestamp: %s", e, value );
        }
    }

    @Override
    protected ValueType getResultType( Object v, ValueType t )
            throws XmlRpcException
    {
        return t;
    }
}
