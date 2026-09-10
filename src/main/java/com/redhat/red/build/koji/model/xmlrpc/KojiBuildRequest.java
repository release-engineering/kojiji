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

import java.util.List;

import static com.redhat.red.build.koji.model.util.RWXUtil.getObjFromList;

public class KojiBuildRequest
{
    protected String source;

    protected KojiIdOrName target;

    protected KojiBuildRequest()
    {
    }

    public KojiBuildRequest( List<Object> request )
    {
        this.source = getObjFromList( request, 0, String.class );
        this.target = getTargetFromList( request, 1 );
    }

    protected static KojiIdOrName getTargetFromList( List<?> request, int index )
    {
        return KojiIdOrName.getFor( getObjFromList( request, index, Object.class ) );
    }

    public String getSource()
    {
        return source;
    }

    public void setSource( String source )
    {
        this.source = source;
    }

    public KojiIdOrName getTarget()
    {
        return target;
    }

    public void setTarget( KojiIdOrName target )
    {
        this.target = target;
    }

    @Override
    public String toString()
    {
        return "KojiBuildRequest{source=" + source + ", target=" + target + "}";
    }
}
