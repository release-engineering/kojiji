/**
 * Copyright (C) 2010 Red Hat, Inc. (jdcasey@commonjava.org)
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
package com.redhat.red.build.koji.http.httpclient4;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.commonjava.rwx.api.RWXMapper;
import org.commonjava.rwx.error.XmlRpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ObjectResponseHandler<T>
                implements ResponseHandler<T>
{

    private XmlRpcException error;

    private final Class<T> responseType;

    public ObjectResponseHandler( final Class<T> responseType )
    {
        this.responseType = responseType;
    }

    public void throwExceptions() throws XmlRpcException
    {
        if ( error != null )
        {
            throw error;
        }
    }

    @Override
    public T handleResponse( final HttpResponse resp ) throws IOException
    {
        Logger logger = LoggerFactory.getLogger( getClass() );
        final StatusLine status = resp.getStatusLine();
        logger.debug( status.toString() );
        if ( status.getStatusCode() > 199 && status.getStatusCode() < 203 )
        {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy( resp.getEntity().getContent(), baos );

            if ( logger.isTraceEnabled() )
            {
                File recording = null;
                FileOutputStream stream = null;
                try
                {
                    recording = File.createTempFile( "xml-rpc.response.", ".xml" );
                    stream = new FileOutputStream( recording );
                    stream.write( baos.toByteArray() );
                }
                catch ( final IOException e )
                {
                    logger.debug( "Failed to record xml-rpc response to file.", e );
                    // this is an auxilliary function. ignore errors.
                }
                finally
                {
                    IOUtils.closeQuietly( stream );
                    logger.info( "\n\n\nRecorded response to: {}\n\n\n", recording );
                }
            }

            try
            {
                logger.trace( "Got response: \n\n{}", new Object()
                {
                    @Override
                    public String toString()
                    {
                        try
                        {
                            return new String( baos.toByteArray(), "UTF-8" );
                        }
                        catch ( final UnsupportedEncodingException e )
                        {
                            return new String( baos.toByteArray() );
                        }
                    }
                } );

                return new RWXMapper().parse( new ByteArrayInputStream( baos.toByteArray() ), responseType );
            }
            catch ( final XmlRpcException e )
            {
                error = e;
                return null;
            }
        }
        else
        {
            error = new XmlRpcException( "Invalid response status: '" + status + "'." );
            return null;
        }
    }
}
