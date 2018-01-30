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
