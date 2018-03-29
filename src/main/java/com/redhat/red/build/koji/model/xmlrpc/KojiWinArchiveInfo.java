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

import com.redhat.red.build.koji.model.converter.StringListConverter;

import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.List;

@StructPart
public class KojiWinArchiveInfo
{
    @DataKey( "archive_id" )
    private Integer archiveId;

    @DataKey( "relpath" )
    private String relPath;

    @DataKey( "platforms" )
    @Converter( StringListConverter.class )
    private List<String> platforms;

    @DataKey( "flags" )
    @Converter( StringListConverter.class )
    private List<String> flags;

    public KojiWinArchiveInfo()
    {
    }

    public KojiWinArchiveInfo( Integer archiveId, String relPath, List<String> platforms, List<String> flags )
    {
        this.archiveId = archiveId;
        this.relPath = relPath;
        this.platforms = platforms;
        this.flags = flags;
    }

    public Integer getArchiveId()
    {
        return archiveId;
    }

    public void setArchiveId( Integer archiveId )
    {
        this.archiveId = archiveId;
    }

    public String getRelPath()
    {
        return relPath;
    }

    public void setRelPath( String relPath )
    {
        this.relPath = relPath;
    }

    public List<String> getPlatforms()
    {
        return platforms;
    }

    public void setPlatforms( List<String> platforms )
    {
        this.platforms = platforms;
    }

    public List<String> getFlags()
    {
        return flags;
    }

    public void setFlags( List<String> flags )
    {
        this.flags = flags;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }

        if ( !( o instanceof KojiWinArchiveInfo ) )
        {
            return false;
        }

        KojiWinArchiveInfo that = (KojiWinArchiveInfo) o;

        return getArchiveId().equals( that.getArchiveId() );
    }

    @Override
    public int hashCode()
    {
        return getArchiveId();
    }

    @Override
    public String toString()
    {
        return "KojiWinArchiveInfo{archiveId=" + archiveId + ", relPath=" + relPath + ", platforms=" + platforms + ", flags=" + flags + "}";
    }
}
