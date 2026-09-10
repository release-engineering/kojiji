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
package com.redhat.red.build.koji.model.xmlrpc;

import java.util.Collections;
import java.util.List;

public class KojiTaskRequest
{
    private List<Object> request;

    public KojiTaskRequest() {}

    public KojiTaskRequest( List<Object> request )
    {
        this.request = request;
    }

    public List<Object> getRequest()
    {
        if ( request == null )
        {
            request = Collections.emptyList();
        }
        return Collections.unmodifiableList( request );
    }

    public void setRequest( List<Object> request )
    {
        this.request = request;
    }

    public KojiTaskRequest withRequest( List<Object> request )
    {
        this.request = request;
        return this;
    }

    @Deprecated
    public KojiBuildRequest asBuildRequest()
    {
        return asBuildRequest( KojiTaskMethod.build );
    }

    public KojiMavenBuildRequest asMavenBuildRequest()
    {
        return new KojiMavenBuildRequest( request );
    }

    public KojiBuildRequest asBuildRequest( KojiTaskMethod method )
    {
        return method.asBuildRequest( request );
    }

    @Deprecated
    public KojiBuildRequest asBuildRequest( String method )
    {
        return asBuildRequest( KojiTaskMethod.fromString( method ) );
    }

    @Override
    public String toString()
    {
        return "KojiTaskRequest{request=" + request + "}";
    }
}
