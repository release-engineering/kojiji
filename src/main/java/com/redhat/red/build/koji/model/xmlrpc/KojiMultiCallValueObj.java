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
package com.redhat.red.build.koji.model.xmlrpc;

import java.util.Map;

import org.commonjava.rwx.core.Registry;

/**
 * Created by ruhan on 8/4/17.
 */
public class KojiMultiCallValueObj
{
    private Object response;

    private Object data;

    private KojiMultiCallFault fault;

    public KojiMultiCallValueObj( Object response )
    {
        setResponse( response );
    }

    public Object getResponse()
    {
        return response;
    }

    public void setResponse( Object response )
    {
        this.response = response;

        Registry registry = Registry.getInstance();

        if ( response instanceof Map )
        {
            this.fault = registry.parseAs( response, KojiMultiCallFault.class );
        }
        else
        {
            this.data = registry.parseAs( response, KojiMultiCallData.class ).getData();
        }
    }

    public Object getData()
    {
        return data;
    }

    public void setData( Object data )
    {
        this.data = data;
    }

    public KojiMultiCallFault getFault()
    {
        return fault;
    }

    public void setFault( KojiMultiCallFault fault )
    {
        this.fault = fault;
    }
}
