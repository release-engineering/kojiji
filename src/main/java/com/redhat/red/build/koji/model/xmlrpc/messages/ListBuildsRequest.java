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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiMavenRef;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 1/29/16.
 */
@Request( method="listBuilds" )
public class ListBuildsRequest
{
    @DataIndex( 0 )
    private final KojiBuildQuery query;

    @IndexRefs( 0 )
    public ListBuildsRequest( KojiBuildQuery query )
    {
        this.query = query;
    }

    public ListBuildsRequest( ProjectVersionRef gav )
    {
        this.query = new KojiBuildQuery( gav );
    }

    public ListBuildsRequest( KojiMavenRef gav )
    {
        this.query = new KojiBuildQuery( gav );
    }

    public KojiBuildQuery getQuery()
    {
        return query;
    }
}
