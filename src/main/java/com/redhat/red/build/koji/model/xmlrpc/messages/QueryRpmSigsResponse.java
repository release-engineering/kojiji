/**
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

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmSignatureInfo;

import java.util.List;

import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Response;

@Response
public class QueryRpmSigsResponse
{
    @DataIndex( 0 )
    private List<KojiRpmSignatureInfo> rpmSignatureInfos;

    public QueryRpmSigsResponse( List<KojiRpmSignatureInfo> rpmSignatureInfos )
    {
        this.rpmSignatureInfos = rpmSignatureInfos;
    }

    public QueryRpmSigsResponse()
    {
    }

    public void setRpmSignatureInfos( List<KojiRpmSignatureInfo> rpmSignatureInfos )
    {
        this.rpmSignatureInfos = rpmSignatureInfos;
    }

    public List<KojiRpmSignatureInfo> getRpmSignatureInfos()
    {
        return rpmSignatureInfos;
    }
}
