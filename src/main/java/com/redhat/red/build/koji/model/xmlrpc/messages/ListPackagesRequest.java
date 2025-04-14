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

import com.redhat.red.build.koji.model.xmlrpc.KojiPackageQuery;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.LIST_PACKAGES;

/**
 * Created by jdcasey on 1/7/16.
 */
@Request( method = LIST_PACKAGES )
public class ListPackagesRequest
{

    @DataIndex( 0 )
    private KojiPackageQuery query;

    public ListPackagesRequest()
    {
    }

    public ListPackagesRequest( KojiPackageQuery query )
    {
        this.query = query;
    }

    public KojiPackageQuery getQuery()
    {
        return query;
    }

    public void setQuery( KojiPackageQuery query )
    {
        this.query = query;
    }
}
