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

import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import com.redhat.red.build.koji.model.xmlrpc.KojiGetLatestRpmsParams;
import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.GET_LATEST_RPMS;

@Request( method = GET_LATEST_RPMS )
public class GetLatestRpmsRequest
{
    @DataIndex( 0 )
    private KojiIdOrName tag;

    @DataIndex( 1 )
    private KojiGetLatestRpmsParams params;

    public GetLatestRpmsRequest()
    {

    }

    public GetLatestRpmsRequest( String tag )
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

    public KojiGetLatestRpmsParams getParams()
    {
        return params;
    }

    public void setParams( KojiGetLatestRpmsParams params )
    {
        this.params = params;
    }

    public GetLatestRpmsRequest withTag( KojiIdOrName tag )
    {
      this.tag = tag;
      return this;
    }

    public GetLatestRpmsRequest withTag( String tag )
    {
        this.tag = new KojiIdOrName( tag );
        return this;
    }

    public GetLatestRpmsRequest withTag( int tag )
    {
        this.tag = new KojiIdOrName( tag );
        return this;
    }

    public GetLatestRpmsRequest withParams( KojiGetLatestRpmsParams params )
    {
        this.params = params;
        return this;
    }
}
