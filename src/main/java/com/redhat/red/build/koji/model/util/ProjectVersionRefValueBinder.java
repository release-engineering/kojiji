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

import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.binding.mapping.Mapping;
import org.commonjava.rwx.binding.spi.Binder;
import org.commonjava.rwx.binding.spi.BindingContext;
import org.commonjava.rwx.binding.spi.value.CustomStructBinder;
import org.commonjava.rwx.error.XmlRpcException;
import org.commonjava.rwx.spi.XmlRpcListener;
import org.commonjava.rwx.vocab.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jdcasey on 5/6/16.
 */
public class ProjectVersionRefValueBinder
        extends CustomStructBinder
{
    private String groupId, artifactId, version;

    public ProjectVersionRefValueBinder( Binder parent, Class<?> type, BindingContext context )
    {
        super( parent, type, context );
    }

    @Override
    protected void map( String key, Object value, ValueType type )
            throws XmlRpcException
    {
        if ( value == null )
        {
            return;
        }

        Logger logger = LoggerFactory.getLogger( getClass() );
        logger.trace( "Got {} with value: {}", key, value );

        switch ( key )
        {
            case "group_id":
            {
                groupId = String.valueOf( value );
                break;
            }
            case "artifact_id":
            {
                artifactId = String.valueOf( value );
                break;
            }
            case "version":
            {
                version = String.valueOf( value ).replace( '_', '-' );
                break;
            }
            default:
            {
                logger.debug( "Ignoring unknown struct member: '{}'", key );
            }
        }

    }

    @Override
    protected Object getBindingResult()
            throws XmlRpcException
    {
        return new SimpleProjectVersionRef( groupId, artifactId, version );
    }

    @Override
    public void generate( XmlRpcListener listener, Object value, Map<Class<?>, Mapping<?>> recipes )
            throws XmlRpcException
    {
        if ( value == null )
        {
            listener.value( null, ValueType.NIL );
        }
        else
        {
            ProjectVersionRef gav = (ProjectVersionRef) value;

            Logger logger = LoggerFactory.getLogger( getClass() );
            logger.trace( "GENERATE: Start struct for GAV: {}", gav );

            listener.startStruct();
            listener.startStructMember( "group_id" );
            listener.structMember( "group_id", gav.getGroupId(), ValueType.STRING );
            listener.value( gav.getGroupId(), ValueType.STRING );
            listener.endStructMember();

            listener.startStructMember( "artifact_id" );
            listener.structMember( "artifact_id", gav.getArtifactId(), ValueType.STRING );
            listener.value( gav.getArtifactId(), ValueType.STRING );
            listener.endStructMember();

            listener.startStructMember( "version" );
            listener.structMember( "version", gav.getVersionString().replace( '-', '_' ), ValueType.STRING );
            listener.value( gav.getVersionString(), ValueType.STRING );
            listener.endStructMember();

            listener.endStruct();

            listener.value( value.toString(), ValueType.STRUCT );
        }
    }
}
