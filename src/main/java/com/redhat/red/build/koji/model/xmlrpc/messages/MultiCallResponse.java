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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallValueObj;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruhan on 8/4/17.
 */
@Response
public class MultiCallResponse
{
    @DataIndex( 0 )
    private List<Object> response;

    private List<KojiMultiCallValueObj> valueObjs;

    public Object getResponse()
    {
        return response;
    }

    public void setResponse( List<Object> response )
    {
        this.response = response;
        this.valueObjs = new ArrayList<>( response.size() );
        response.forEach(r -> this.valueObjs.add( new KojiMultiCallValueObj( r ) ) );
    }

    public List<KojiMultiCallValueObj> getValueObjs()
    {
        return valueObjs;
    }

    public void setValueObjs( List<KojiMultiCallValueObj> valueObjs )
    {
        this.valueObjs = valueObjs;
    }
}
