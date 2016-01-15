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
package com.redhat.red.build.koji.model.messages;

import com.redhat.red.build.koji.model.KojiIdOrName;
import com.redhat.red.build.koji.model.util.StringListValueBinder;
import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.Request;

import java.util.List;

/**
 * Created by jdcasey on 1/11/16.
 */
@Request( method="createTag" )
public class CreateTagRequest
{
    @DataIndex( 0 )
    private String tagName;

    @DataIndex( 1 )
    private KojiIdOrName parent;

    @DataIndex( 2 )
    @Converter( StringListValueBinder.class )
    private List<String> arches;

    @DataIndex( 3 )
    private String permission;

    @DataIndex( 4 )
    private boolean locked;

    @DataIndex( 5 )
    private boolean mavenSupport = true;

    @DataIndex( 6 )
    private boolean mavenIncludeAll = true;

    public CreateTagRequest(){}

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

    public void setLocked( boolean locked )
    {
        this.locked = locked;
    }

    public boolean isMavenSupport()
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
