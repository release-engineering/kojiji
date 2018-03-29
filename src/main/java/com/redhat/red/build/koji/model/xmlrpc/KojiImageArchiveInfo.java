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
public class KojiImageArchiveInfo
{
    @DataKey( "archive_id" )
    private Integer archiveId;

    @DataKey( "arch" )
    private String arch;

    @DataKey( "rootid" )
    private Boolean rootId;

    public KojiImageArchiveInfo()
    {
    }

    public KojiImageArchiveInfo( Integer archiveId, String arch, Boolean rootId )
    {
        this.archiveId = archiveId;
        this.arch = arch;
        this.rootId = rootId;
    }

    public Integer getArchiveId()
    {
        return archiveId;
    }

    public void setArchiveId( Integer archiveId )
    {
        this.archiveId = archiveId;
    }

    public String getArch()
    {
        return arch;
    }

    public void setArch( String arch )
    {
        this.arch = arch;
    }

    public Boolean getRootId()
    {
        return rootId;
    }

    public void setRootId( Boolean rootId )
    {
        this.rootId = rootId;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }

        if ( !( o instanceof KojiImageArchiveInfo ) )
        {
            return false;
        }

        KojiImageArchiveInfo that = (KojiImageArchiveInfo) o;

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
        return "KojiImageArchiveInfo{archiveId=" + archiveId + ", arch=" + arch + ", rootId=" + rootId + "}";
    }
}
