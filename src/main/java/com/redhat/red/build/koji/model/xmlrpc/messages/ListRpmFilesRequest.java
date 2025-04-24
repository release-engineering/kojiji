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

import com.redhat.red.build.koji.model.xmlrpc.KojiRpmFilesQuery;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.LIST_RPM_FILES;

@Request( method = LIST_RPM_FILES )
public class ListRpmFilesRequest
{
    @DataIndex( 0 )
    private int rpmId;

    @DataIndex( 1 )
    private KojiRpmFilesQuery query;

    public ListRpmFilesRequest()
    {
    }

    public ListRpmFilesRequest( int rpmId )
    {
        this.rpmId = rpmId;
    }

    public ListRpmFilesRequest( int rpmId, KojiRpmFilesQuery query )
    {
        this.rpmId = rpmId;
        this.query = query;
    }

    public int getRpmId()
    {
        return rpmId;
    }

    public void setRpmId( int rpmId )
    {
        this.rpmId = rpmId;
    }

    public KojiRpmFilesQuery getQuery()
    {
        return query;
    }

    public void setQuery( KojiRpmFilesQuery query )
    {
        this.query = query;
    }
}
