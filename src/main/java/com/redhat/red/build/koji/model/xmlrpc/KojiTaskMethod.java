/*
 * Copyright (C) 2015 Red Hat, Inc.
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
package com.redhat.red.build.koji.model.xmlrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

public enum KojiTaskMethod
{
    unknown( request -> null ),
    build( KojiBuildRequest::new ),
    maven( KojiMavenBuildRequest::new );

    private static final Logger logger = LoggerFactory.getLogger( KojiTaskMethod.class );

    private final Function<List<Object>, KojiBuildRequest> factory;

    KojiTaskMethod(Function<List<Object>, KojiBuildRequest> factory)
    {
        this.factory = factory;
    }

    public KojiBuildRequest asBuildRequest( List<Object> request )
    {
        return factory.apply( request );
    }

    public static KojiTaskMethod fromString( String name )
    {
        if ( name == null )
        {
            return unknown;
        }

        for ( KojiTaskMethod method : values() )
        {
            if ( method.name().equals( name ) )
            {
                return method;
            }
        }

        logger.warn( "Unknown Koji task method: {}", name );
        return unknown;
    }

    @Override
    public String toString()
    {
        return name();
    }
}
