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

import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import com.redhat.red.build.koji.model.xmlrpc.KojiGetRpmParams;
import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.GET_RPM;

@Request( method = GET_RPM )
public class GetRpmRequest
{
    @DataIndex( 0 )
    private KojiIdOrName rpminfo;

    @DataIndex( 1 )
    private KojiGetRpmParams params;

    public GetRpmRequest()
    {
    }

    public GetRpmRequest( KojiIdOrName rpminfo )
    {
        this.rpminfo = rpminfo;
    }

    public KojiIdOrName getRpminfo()
    {
        return rpminfo;
    }

    public void setRpminfo( KojiIdOrName rpminfo )
    {
        this.rpminfo = rpminfo;
    }

    public GetRpmRequest withRpminfo( KojiIdOrName rpminfo )
    {
        this.rpminfo = rpminfo;
        return this;
    }

    public KojiGetRpmParams getParams()
    {
        return params;
    }

    public void setParams( KojiGetRpmParams params )
    {
        this.params = params;
    }

    public GetRpmRequest withParams( KojiGetRpmParams params )
    {
        this.params = params;
        return this;
    }
}
