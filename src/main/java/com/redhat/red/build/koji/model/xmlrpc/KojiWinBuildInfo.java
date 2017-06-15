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

import java.util.List;

import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.StructPart;

@StructPart
public class KojiWinBuildInfo
{
    @DataKey( "build_id" )
    private int buildId;

    @DataKey( "platform" )
    private String platform;

    public KojiWinBuildInfo()
    {
    }

    public KojiWinBuildInfo( int buildId, String platform )
    {
        this.buildId = buildId;
        this.platform = platform;
    }

    public int getBuildId()
    {
        return buildId;
    }

    public void setBuildId( int buildId )
    {
        this.buildId = buildId;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform( String platform )
    {
        this.platform = platform;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof KojiWinBuildInfo) )
        {
            return false;
        }

        KojiWinBuildInfo that = (KojiWinBuildInfo) o;

        return getBuildId() == that.getBuildId();

    }

    @Override
    public int hashCode()
    {
        return getBuildId();
    }
}
