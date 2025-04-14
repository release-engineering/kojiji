/*
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
package com.redhat.red.build.koji.http.httpclient4;

import com.redhat.red.build.koji.model.xmlrpc.messages.VoidResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.commonjava.o11yphant.metrics.api.MetricRegistry;
import org.commonjava.o11yphant.metrics.api.Timer;
import org.commonjava.rwx.anno.Request;
import org.commonjava.rwx.api.RWXMapper;
import org.commonjava.rwx.error.XmlRpcException;
import com.redhat.red.build.koji.http.RequestModifier;
import com.redhat.red.build.koji.http.UrlBuildResult;
import com.redhat.red.build.koji.http.UrlBuilder;
import com.redhat.red.build.koji.http.error.XmlRpcTransportException;
import org.commonjava.util.jhttpc.HttpFactory;
import org.commonjava.util.jhttpc.JHttpCException;
import org.commonjava.util.jhttpc.model.SiteConfig;
import org.commonjava.util.jhttpc.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Arrays;

import static org.commonjava.o11yphant.metrics.util.NameUtils.name;

public class HC4SyncObjectClient
{
    private final HttpFactory httpFactory;

    private final SiteConfig siteConfig;

    private final MetricRegistry metricRegistry;

    private final String[] extraPath;

    public HC4SyncObjectClient( final HttpFactory httpFactory, final SiteConfig siteConfig,
                                final MetricRegistry metricRegistry, String... extraPath )
    {
        this.httpFactory = httpFactory;
        this.siteConfig = siteConfig;
        this.metricRegistry = metricRegistry;
        this.extraPath = extraPath;
    }

    public <T> T call( final Object request, final Class<T> responseType, final UrlBuilder urlBuilder,
                       final RequestModifier requestModifier ) throws XmlRpcException
    {
        if ( metricRegistry == null )
        {
            return RetryUtils.withRetry( () -> doCall(request, responseType, urlBuilder, requestModifier) );
        }

        // Apply global and per request metric

        final Timer.Context timerContext = metricRegistry.timer( name( getClass(), "call" ) ).time();
        final Timer.Context requestTimerContext = metricRegistry.timer( name( request.getClass(), "call" ) ).time();
        try
        {
            return RetryUtils.withRetry( () -> doCall(request, responseType, urlBuilder, requestModifier) );
        }
        finally
        {
            timerContext.stop();
            requestTimerContext.stop();
        }
    }

    private <T> T doCall( final Object request, final Class<T> responseType, final UrlBuilder urlBuilder,
                          final RequestModifier requestModifier ) throws XmlRpcException
    {
        final String methodName = getRequestMethod( request );
        if ( methodName == null )
        {
            throw new XmlRpcTransportException( "Request value is not annotated with @Request.", request );
        }

        final HttpPost method;
        try
        {
            Logger logger = LoggerFactory.getLogger( getClass() );

            String url = UrlUtils.buildUrl( siteConfig.getUri(), extraPath );
            logger.trace( "Unadorned URL: '{}'", url );

            if ( urlBuilder != null )
            {
                UrlBuildResult buildResult = urlBuilder.buildUrl( url );
                logger.trace( "UrlBuilder ({}) result: {}", urlBuilder.getClass().getName(), buildResult );
                url = buildResult.throwError().get();
            }
            logger.trace( "POSTing {} request to: '{}'", methodName, url );

            method = new HttpPost( url );
            method.setHeader( "Content-Type", "text/xml" );

            if ( requestModifier != null )
            {
                requestModifier.modifyRequest( method );
            }

            final String content = new RWXMapper().render( request );
            logger.trace( "Sending request:\n\n{}\n\n", content );

            method.setEntity( new StringEntity( content ) );
        }
        catch ( final UnsupportedEncodingException e )
        {
            throw new XmlRpcTransportException( "Call failed: " + methodName, request, e );
        }
        catch ( MalformedURLException e )
        {
            throw new XmlRpcTransportException( "Failed to construct URL from: %s and extra-path: %s. Reason: %s", e,
                                                siteConfig.getUri(), Arrays.asList( extraPath ), e.getMessage() );
        }

        try ( CloseableHttpClient client = httpFactory.createClient( siteConfig ) )
        {
            if ( Void.class.equals( responseType ) )
            {
                final ObjectResponseHandler<VoidResponse> handler =
                                new ObjectResponseHandler<VoidResponse>( VoidResponse.class );
                client.execute( method, handler );

                handler.throwExceptions();
                return null;
            }
            else
            {
                final ObjectResponseHandler<T> handler = new ObjectResponseHandler<T>( responseType );
                final T response = client.execute( method, handler );

                handler.throwExceptions();
                return response;

            }
        }
        catch ( final ClientProtocolException e )
        {
            throw new XmlRpcTransportException( "Call failed: " + methodName, request, e );
        }
        catch ( final IOException e )
        {
            throw new XmlRpcTransportException( "Call failed: " + methodName, request, e );
        }
        catch ( JHttpCException e )
        {
            throw new XmlRpcTransportException( "Call failed: " + methodName, request, e );
        }
    }

    private String getRequestMethod( Object obj )
    {
        final Class<?> type = ( ( obj instanceof Class<?> ) ? (Class<?>) obj : obj.getClass() );
        final Request req = type.getAnnotation( Request.class );
        if ( req != null )
        {
            return req.method();
        }
        return null;
    }

    public void close()
    {
        if ( httpFactory != null )
        {
            try
            {
                httpFactory.close();
            } catch (IOException e) {

            }
        }
    }
}
