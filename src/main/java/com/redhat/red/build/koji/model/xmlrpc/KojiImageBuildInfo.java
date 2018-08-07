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
package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

@StructPart
public class KojiImageBuildInfo
{
    @DataKey( "build_id" )
    private int buildId;

    public KojiImageBuildInfo()
    {
    }

    public KojiImageBuildInfo(int buildId)
    {
        this.buildId = buildId;
    }

    public int getBuildId()
    {
        return buildId;
    }

    public void setBuildId( int buildId )
    {
        this.buildId = buildId;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof KojiImageBuildInfo) )
        {
            return false;
        }

        KojiImageBuildInfo that = (KojiImageBuildInfo) o;

        return getBuildId() == that.getBuildId();

    }

    @Override
    public int hashCode()
    {
        return getBuildId();
    }

    @Override
    public String toString()
    {
        return "KojiImageBuildInfo{buildId=" + buildId + "}";
    }
}
