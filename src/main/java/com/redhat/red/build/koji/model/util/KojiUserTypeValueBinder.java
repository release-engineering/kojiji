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

import java.util.Map;

import org.commonjava.rwx.binding.mapping.Mapping;
import org.commonjava.rwx.binding.spi.Binder;
import org.commonjava.rwx.binding.spi.BindingContext;
import org.commonjava.rwx.binding.spi.value.CustomValueBinder;
import org.commonjava.rwx.error.XmlRpcException;
import org.commonjava.rwx.spi.XmlRpcListener;
import org.commonjava.rwx.vocab.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.red.build.koji.model.xmlrpc.KojiUserType;

public class KojiUserTypeValueBinder
        extends CustomValueBinder
{
    public KojiUserTypeValueBinder( Binder parent, Class<?> type, BindingContext context )
    {
        super( parent, type, context );
    }

    @Override
    public void generate( XmlRpcListener listener, Object value,
                          Map<Class<?>, Mapping<?>> recipes ) throws XmlRpcException
    {
        Logger logger = LoggerFactory.getLogger( getClass() );
        if ( value == null )
        {
            throw new XmlRpcException( "No user type selected. One is requested. The default user type is NORMAL." );
        }
        else if ( value instanceof KojiUserType )
        {
            KojiUserType userType = (KojiUserType) value;
            Integer intUserType = userType.getValue();
            if ( intUserType == null )
            {
                logger.debug( "Generating nil" );
                listener.value( null, ValueType.NIL );
            }
            else
            {
                listener.value( intUserType, ValueType.INT );
            }
        }
        else
        {
            throw new XmlRpcException( "Invalid value type: {} for converter: {} (expects: {})", value.getClass().getName(),
                                       getClass().getName(), KojiUserType.class.getName() );
        }
    }

    @Override
    protected Object getResult( Object v, ValueType t ) throws XmlRpcException
    {
        if ( v == null || v instanceof Integer )
        {
            return KojiUserType.fromInteger( ( Integer ) v );
        }
        else
        {
            throw new XmlRpcException( "Cannot parse user type: converter: {} (expects: {} or null, but {} was given)",
                                       getClass().getName(), Integer.class.getName(), v.getClass().getName() );
        }
    }

    @Override
    protected ValueType getResultType( Object v, ValueType t ) throws XmlRpcException
    {
        return t;
    }
}
