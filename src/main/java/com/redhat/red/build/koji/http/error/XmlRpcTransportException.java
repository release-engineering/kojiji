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
package com.redhat.red.build.koji.http.error;

import org.commonjava.rwx.error.XmlRpcException;

public class XmlRpcTransportException
    extends XmlRpcException
{

    private static final long serialVersionUID = 1L;

    private final Object request;

    public XmlRpcTransportException( final String format, final Object request, Object...params )
    {
        super( format, params );
        this.request = request;
    }

    public XmlRpcTransportException( final String message, final Object request, final Throwable cause, Object...params )
    {
        super( message, cause, params );
        this.request = request;
    }

    public Object getRequest()
    {
        return request;
    }

}
