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

import com.redhat.red.build.koji.model.xmlrpc.KojiQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmSigsQuery;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.QUERY_RPM_SIGS;

@Request( method = QUERY_RPM_SIGS )
public class QueryRpmSigsRequest
        extends KojiQuery
{
    @DataIndex( 0 )
    private KojiRpmSigsQuery query;

    public QueryRpmSigsRequest()
    {

    }

    public QueryRpmSigsRequest( KojiRpmSigsQuery query )
    {
        this.query = query;
    }

    public KojiRpmSigsQuery getQuery()
    {
        return query;
    }

    public void setQuery( KojiRpmSigsQuery query )
    {
        this.query = query;
    }
}
