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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import com.redhat.red.build.koji.model.xmlrpc.KojiListTaggedRpmsParams;

import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.LIST_TAGGED_RPMS;

@Request( method = LIST_TAGGED_RPMS )
public class ListTaggedRpmsRequest
{
    @DataIndex( 0 )
    private KojiIdOrName tag;

    @DataIndex( 1 )
    private KojiListTaggedRpmsParams params;

    public ListTaggedRpmsRequest()
    {

    }

    public ListTaggedRpmsRequest( String tag )
    {
        this.tag = new KojiIdOrName( tag );
    }

    public KojiIdOrName getTag()
    {
        return tag;
    }

    public void setTag( KojiIdOrName tag )
    {
        this.tag = tag;
    }

    public ListTaggedRpmsRequest withTag( KojiIdOrName tag )
    {
        this.tag = tag;
        return this;
    }

    public ListTaggedRpmsRequest withTag( String tag )
    {
        this.tag = new KojiIdOrName( tag );
        return this;
    }

    public ListTaggedRpmsRequest withTag( int tag )
    {
        this.tag = new KojiIdOrName( tag );
        return this;
    }

    public KojiListTaggedRpmsParams getParams()
    {
        return params;
    }

    public void setParams( KojiListTaggedRpmsParams params )
    {
        this.params = params;
    }

    public ListTaggedRpmsRequest withParams( KojiListTaggedRpmsParams params )
    {
        this.params = params;
        return this;
    }
}
