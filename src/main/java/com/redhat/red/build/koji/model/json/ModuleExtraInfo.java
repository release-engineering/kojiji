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
package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.Objects;

/**
 * Represents the {@code extra.typeinfo.module} structure set by the Module Build Service (MBS)
 * when importing a module build into Koji via the Content Generator API.
 *
 * @see <a href="https://forge.fedoraproject.org/koji/mbs">The Module Build Service (MBS) for Modularity</a>
 */
@StructPart
public class ModuleExtraInfo
{
    @JsonProperty( "module_build_service_id" )
    @DataKey( "module_build_service_id" )
    private Integer moduleBuildServiceId;

    @JsonProperty( "content_koji_tag" )
    @DataKey( "content_koji_tag" )
    private String contentKojiTag;

    @JsonProperty( "modulemd_str" )
    @DataKey( "modulemd_str" )
    private String modulemdStr;

    @JsonProperty( "name" )
    @DataKey( "name" )
    private String name;

    @JsonProperty( "stream" )
    @DataKey( "stream" )
    private String stream;

    @JsonProperty( "version" )
    @DataKey( "version" )
    private String version;

    @JsonProperty( "context" )
    @DataKey( "context" )
    private String context;

    public ModuleExtraInfo()
    {
    }

    public Integer getModuleBuildServiceId()
    {
        return moduleBuildServiceId;
    }

    public void setModuleBuildServiceId( Integer moduleBuildServiceId )
    {
        this.moduleBuildServiceId = moduleBuildServiceId;
    }

    public String getContentKojiTag()
    {
        return contentKojiTag;
    }

    public void setContentKojiTag( String contentKojiTag )
    {
        this.contentKojiTag = contentKojiTag;
    }

    public String getModulemdStr()
    {
        return modulemdStr;
    }

    public void setModulemdStr( String modulemdStr )
    {
        this.modulemdStr = modulemdStr;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getStream()
    {
        return stream;
    }

    public void setStream( String stream )
    {
        this.stream = stream;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public String getContext()
    {
        return context;
    }

    public void setContext( String context )
    {
        this.context = context;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        ModuleExtraInfo that = (ModuleExtraInfo) o;
        return Objects.equals( moduleBuildServiceId, that.moduleBuildServiceId ) && Objects.equals( contentKojiTag, that.contentKojiTag ) && Objects.equals( modulemdStr, that.modulemdStr ) && Objects.equals( name, that.name ) && Objects.equals( stream, that.stream ) && Objects.equals( version, that.version ) && Objects.equals( context, that.context );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( moduleBuildServiceId, contentKojiTag, modulemdStr, name, stream, version, context );
    }

    @Override
    public String toString()
    {
        return "ModuleExtraInfo{moduleBuildServiceId=" + moduleBuildServiceId + ", contentKojiTag='" + contentKojiTag + '\'' + ", modulemdStr='" + modulemdStr + '\'' + ", name='" + name + '\'' + ", stream='" + stream + '\'' + ", version='" + version + '\'' + ", context='" + context + "'}";
    }
}
