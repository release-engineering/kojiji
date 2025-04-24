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

import com.redhat.red.build.koji.model.converter.StringListConverter;
import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import java.util.List;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.CREATE_TAG;

/**
 * Created by jdcasey on 1/11/16.
 */
@Request( method = CREATE_TAG )
public class CreateTagRequest
{
    @DataIndex( 0 )
    private String tagName;

    @DataIndex( 1 )
    private KojiIdOrName parent;

    @DataIndex( 2 )
    @Converter( StringListConverter.class )
    private List<String> arches;

    @DataIndex( 3 )
    private String permission;

    @DataIndex( 4 )
    private boolean locked;

    @DataIndex( 5 )
    private boolean mavenSupport = true;

    @DataIndex( 6 )
    private boolean mavenIncludeAll = true;

    public CreateTagRequest()
    {
    }

    public String getTagName()
    {
        return tagName;
    }

    public void setTagName( String tagName )
    {
        this.tagName = tagName;
    }

    public String getPermission()
    {
        return permission;
    }

    public void setPermission( String permission )
    {
        this.permission = permission;
    }

    public List<String> getArches()
    {
        return arches;
    }

    public void setArches( List<String> arches )
    {
        this.arches = arches;
    }

    public boolean isLocked()
    {
        return locked;
    }

    public boolean getLocked()
    {
        return locked;
    }

    public void setLocked( boolean locked )
    {
        this.locked = locked;
    }

    public boolean isMavenSupport()
    {
        return mavenSupport;
    }

    public boolean getMavenSupport()
    {
        return mavenSupport;
    }

    public void setMavenSupport( boolean mavenSupport )
    {
        this.mavenSupport = mavenSupport;
    }

    public boolean isMavenIncludeAll()
    {
        return mavenIncludeAll;
    }

    public boolean getMavenIncludeAll()
    {
        return mavenIncludeAll;
    }

    public void setMavenIncludeAll( boolean mavenIncludeAll )
    {
        this.mavenIncludeAll = mavenIncludeAll;
    }

    public KojiIdOrName getParent()
    {
        return parent;
    }

    public void setParent( KojiIdOrName parent )
    {
        this.parent = parent;
    }

    public CreateTagRequest withTagName( String tagName )
    {
        this.tagName = tagName;
        return this;
    }

    public CreateTagRequest withArches( List<String> arches )
    {
        this.arches = arches;
        return this;
    }

    public CreateTagRequest withParent( KojiIdOrName parent )
    {
        this.parent = parent;
        return this;
    }

    public CreateTagRequest withPermission( String permission )
    {
        this.permission = permission;
        return this;
    }

    public CreateTagRequest withLocked( boolean locked )
    {
        this.locked = locked;
        return this;
    }

    public CreateTagRequest withMavenSupport( boolean mavenSupport )
    {
        this.mavenSupport = mavenSupport;
        return this;
    }

    public CreateTagRequest withMavenIncludeAll( boolean mavenIncludeAll )
    {
        this.mavenIncludeAll = mavenIncludeAll;
        return this;
    }

    @Override
    public String toString()
    {
        return "CreateTagRequest{" +
                "tagName='" + tagName + '\'' +
                ", parent=" + parent +
                ", arches=" + arches +
                ", permission='" + permission + '\'' +
                ", locked=" + locked +
                ", mavenSupport=" + mavenSupport +
                ", mavenIncludeAll=" + mavenIncludeAll +
                '}';
    }
}
