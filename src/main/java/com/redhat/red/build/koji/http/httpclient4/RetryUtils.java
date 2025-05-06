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

import org.commonjava.rwx.error.XmlRpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;

public class RetryUtils
{
    private static final Logger logger = LoggerFactory.getLogger( RetryUtils.class );

    public static final int DEFAULT_RETRY_COUNT = 3;

    public static final long DEFAULT_WAIT_SECONDS = 10;

    public interface CallToRetry<R>
    {
        R process() throws XmlRpcException;
    }

    public static <T> T withRetry( CallToRetry call ) throws XmlRpcException
    {
        return withRetry( DEFAULT_RETRY_COUNT, DEFAULT_WAIT_SECONDS, call );
    }

    public static <T> T withRetry( int retryCount, long interval, CallToRetry call ) throws XmlRpcException
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

                if ( e.getCause() instanceof ConnectException )
                {
                    logger.info( "ConnectException {}/{}, Waiting for {} second(s) before next retry ...", e.getCause().getClass(), e.getCause().getMessage(), interval );
                    try
                    {
                        Thread.sleep( interval * 1000l );
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

}
