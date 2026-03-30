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
package com.redhat.red.build.koji.http.httpclient4;

import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallObj;
import com.redhat.red.build.koji.model.xmlrpc.messages.MultiCallRequest;
import org.apache.http.NoHttpResponseException;
import org.commonjava.rwx.anno.Request;
import org.commonjava.rwx.error.XmlRpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.List;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.MULTI_CALL;

public class RetryUtils
{
    private static final Logger logger = LoggerFactory.getLogger( RetryUtils.class );

    public static final int DEFAULT_RETRY_COUNT = 3;

    public static final long DEFAULT_WAIT_SECONDS = 10L;

    public static final long MAX_WAIT_SECONDS = 60L;

    public interface CallToRetry<R>
    {
        R process() throws XmlRpcException;
    }

    public static <T> T withRetry( CallToRetry call, Object request ) throws XmlRpcException
    {
        return withRetry( DEFAULT_RETRY_COUNT, DEFAULT_WAIT_SECONDS, call, request );
    }

    public static <T> T withRetry( int retryCount, long interval, CallToRetry call, Object request ) throws XmlRpcException
    {
        int count = 0;
        while ( true )
        {
            try
            {
                return (T) call.process();
            }
            catch ( XmlRpcException e )
            {
                if ( ++count > retryCount )
                {
                    throw e;
                }

                Throwable cause = e.getCause();

                if ( cause instanceof ConnectException || ( cause instanceof NoHttpResponseException && isSafeToRetry( request ) ) )
                {
                    long seconds = Math.min( interval << ( count - 1 ), MAX_WAIT_SECONDS );
                    logger.info( "ConnectException {}/{}, Waiting for {} second(s) before next retry ...", cause.getClass(), cause.getMessage(), seconds );

                    try
                    {
                        Thread.sleep( seconds * 1000L );
                    }
                    catch ( InterruptedException ex )
                    {
                        throw new RuntimeException( ex );
                    }
                }
                else
                {
                    throw e;
                }
            }
        }
    }

    static boolean isSafeToRetry( Object obj )
    {
        Request request = obj.getClass().getAnnotation( Request.class );

        if ( request == null )
        {
            return false;
        }

        String method = request.method();

        if ( MULTI_CALL.equals( method ) )
        {
            MultiCallRequest multiCallRequest = (MultiCallRequest) obj;
            List<KojiMultiCallObj> multiCallObjs = multiCallRequest.getMultiCallObjs();

            for ( KojiMultiCallObj multiCallObj : multiCallObjs )
            {
                if ( !isSafeCall( multiCallObj.getMethodName() ) )
                {
                    return false;
                }
            }

            return true;
        }

        return isSafeCall( method );
    }

    static boolean isSafeCall( String methodName )
    {
        return methodName.startsWith( "get" ) || methodName.startsWith( "list" ) || methodName.startsWith( "query" ) || methodName.startsWith( "has" );
    }
}
