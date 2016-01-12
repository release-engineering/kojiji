/**
 * Copyright (C) 2015 Red Hat, Inc. (jdcasey@commonjava.org)
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
package com.redhat.red.build.koji.model;

import com.redhat.red.build.koji.model.util.StringListValueBinder;
import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.KeyRefs;
import org.commonjava.rwx.binding.anno.StructPart;

import java.util.List;

/**
 * Created by jdcasey on 1/6/16.
 */
@StructPart
public class KojiTagInfo
{

    @DataKey( "_starstar" )
    private boolean useKojiKeywords = true;

    @DataKey( "id" )
    private int id;

    @DataKey( "name" )
    private String name;

    @DataKey( "perm" )
    private String permission;

    @DataKey( "perm_id" )
    private int permissionId;

    @DataKey( "arches" )
    @Converter( StringListValueBinder.class )
    private List<String> arches;

    @DataKey( "locked" )
    private boolean locked;

    @DataKey( "maven_support" )
    private boolean mavenSupport;

    @DataKey( "maven_include_all" )
    private boolean mavenIncludeAll;

    @KeyRefs( {"id", "name", "perm", "perm_id", "arches", "locked", "maven_support", "maven_include_all" } )
    public KojiTagInfo( int id, String name, String permission, int permissionId, List<String> arches, boolean locked,
                        boolean mavenSupport, boolean mavenIncludeAll )
    {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.permissionId = permissionId;
        this.arches = arches;
        this.locked = locked;
        this.mavenSupport = mavenSupport;
        this.mavenIncludeAll = mavenIncludeAll;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPermission()
    {
        return permission;
    }

    public int getPermissionId()
    {
        return permissionId;
    }

    public List<String> getArches()
    {
        return arches;
    }

    public boolean isLocked()
    {
        return locked;
    }

    public boolean isMavenSupport()
    {
        return mavenSupport;
    }

    public boolean isMavenIncludeAll()
    {
        return mavenIncludeAll;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setPermission( String permission )
    {
        this.permission = permission;
    }

    public void setPermissionId( int permissionId )
    {
        this.permissionId = permissionId;
    }

    public void setArches( List<String> arches )
    {
        this.arches = arches;
    }

    public void setLocked( boolean locked )
    {
        this.locked = locked;
    }

    public void setMavenSupport( boolean mavenSupport )
    {
        this.mavenSupport = mavenSupport;
    }

    public void setMavenIncludeAll( boolean mavenIncludeAll )
    {
        this.mavenIncludeAll = mavenIncludeAll;
    }

    public boolean isUseKojiKeywords()
    {
        return useKojiKeywords;
    }

    public void setUseKojiKeywords( boolean useKojiKeywords )
    {
        this.useKojiKeywords = useKojiKeywords;
    }
}
