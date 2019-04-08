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

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmBuildList;
import com.redhat.red.build.koji.model.xmlrpc.KojiRpmInfo;

import java.util.ArrayList;
import java.util.List;

import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Response;
import org.commonjava.rwx.core.Registry;

@Response
public class RpmBuildListResponse
{
    private static final Registry registry = Registry.getInstance();

    @DataIndex( 0 )
    private List<List<Object>> objs;

    private KojiRpmBuildList rpmBuildList;

    public RpmBuildListResponse()
    {

    }

    public RpmBuildListResponse( List<List<Object>> objs )
    {
        this.objs = objs;
    }

    public List<List<Object>> getObjs()
    {
        return objs;
    }

    public void setObjs( List<List<Object>> objs )
    {
        this.objs = objs;
        this.rpmBuildList = handleObjs( objs );
    }

    public KojiRpmBuildList getRpmBuildList()
    {
        return rpmBuildList;
    }

    private KojiRpmBuildList handleObjs( List<List<Object>> objs )
    {
        List<Object> one = objs.get( 0 );
        List<Object> two = objs.get( 1 );

        List<KojiRpmInfo> rpms = new ArrayList<>( one.size() );
        List<KojiBuildInfo> builds = new ArrayList<>( two.size() );

        for ( Object o : one )
        {
            KojiRpmInfo i = registry.parseAs( o, KojiRpmInfo.class );
            rpms.add( i );
        }

        for ( Object o : two )
        {
            KojiBuildInfo i = registry.parseAs( o, KojiBuildInfo.class );
            builds.add( i );
        }

        return new KojiRpmBuildList( rpms, builds );
    }
}
