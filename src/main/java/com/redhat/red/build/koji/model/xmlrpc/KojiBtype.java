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

public enum KojiBtype
{
    unknown,
    rpm,
    maven,
    win,
    image,
    module,
    operator_manifests,
    remote_sources,
    remote_source_file,
    npm,
    icm;

    private static final Logger logger = LoggerFactory.getLogger( KojiBtype.class );

    KojiBtype()
    {

    }

    public static KojiBtype fromString( String name )
    {
        for ( KojiBtype btype : values() )
        {
            if ( btype.name().replace( '_', '-' ).equals( name ) )
            {
                return btype;
            }
        }

        logger.warn( "Unknown Koji btype: {}", name );
        return unknown;
    }

    @Override
    public String toString()
    {
        return name();
    }
}
