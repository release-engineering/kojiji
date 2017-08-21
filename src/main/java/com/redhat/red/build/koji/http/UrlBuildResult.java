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
package com.redhat.red.build.koji.http;

import org.commonjava.rwx.error.XmlRpcException;

import java.net.MalformedURLException;

/**
 * Created by jdcasey on 2/8/16.
 */
public final class UrlBuildResult
{
    private Throwable error;

    private String url;

    public UrlBuildResult( MalformedURLException error )
    {
        this.error = error;
    }

    public UrlBuildResult( XmlRpcException error )
    {
        this.error = error;
    }

    public UrlBuildResult( String url )
    {
        this.url = url;
    }

    public UrlBuildResult throwError()
        throws XmlRpcException, MalformedURLException
    {
        if ( error != null )
        {
            if ( error instanceof MalformedURLException )
            {
                throw (MalformedURLException) error;
            }
            else if ( error instanceof XmlRpcException )
            {
                throw (XmlRpcException) error;
            }
        }

        return this;
    }

    public String get()
    {
        return url;
    }

    @Override
    public String toString()
    {
        return "UrlBuildResult{" +
                "error=" + error +
                ", url='" + url + '\'' +
                '}';
    }
}
