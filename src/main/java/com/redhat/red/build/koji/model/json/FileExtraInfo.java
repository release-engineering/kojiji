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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.MAVEN_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.NPM_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.TYPEINFO;

/**
 * Created by jdcasey on 9/15/16.
 */
@StructPart
@JsonInclude( Include.NON_NULL )
public class FileExtraInfo
{
    @JsonProperty( MAVEN_INFO )
    @DataKey( MAVEN_INFO )
    private MavenExtraInfo mavenExtraInfo;

    @JsonProperty( NPM_INFO )
    @DataKey( NPM_INFO )
    private NpmExtraInfo npmExtraInfo;

    @JsonProperty( TYPEINFO )
    @DataKey( TYPEINFO )
    private TypeInfoExtraInfo typeInfo;

    public FileExtraInfo( @JsonProperty( NPM_INFO ) NpmExtraInfo npmExtraInfo,
            @JsonProperty( TYPEINFO ) TypeInfoExtraInfo typeInfo )
    {
        this.npmExtraInfo = npmExtraInfo;
        this.typeInfo = typeInfo;
    }

    public FileExtraInfo( @JsonProperty( MAVEN_INFO ) MavenExtraInfo mavenExtraInfo )
    {
        this.mavenExtraInfo = mavenExtraInfo;
    }

    public FileExtraInfo( @JsonProperty( TYPEINFO ) TypeInfoExtraInfo typeInfo )
    {
        this.typeInfo = typeInfo;
    }

    public FileExtraInfo()
    {
    }

    public NpmExtraInfo getNpmExtraInfo()
    {
        return npmExtraInfo;
    }

    public void setNpmExtraInfo( NpmExtraInfo npmExtraInfo )
    {
        this.npmExtraInfo = npmExtraInfo;
    }

    public void setMavenExtraInfo( MavenExtraInfo mavenExtraInfo )
    {
        this.mavenExtraInfo = mavenExtraInfo;
    }

    public void setTypeInfo( TypeInfoExtraInfo typeInfo )
    {
        this.typeInfo = typeInfo;
    }

    public FileExtraInfo( ProjectVersionRef gav )
    {
        this.mavenExtraInfo = new MavenExtraInfo( gav );
    }

    public MavenExtraInfo getMavenExtraInfo()
    {
        return mavenExtraInfo;
    }

    public TypeInfoExtraInfo getTypeInfo()
    {
        return typeInfo;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof FileExtraInfo ) )
        {
            return false;
        }

        FileExtraInfo that = (FileExtraInfo) o;

        if ( getMavenExtraInfo() != null )
        {
            return getMavenExtraInfo().equals( that.getMavenExtraInfo() );
        }
        else if ( getNpmExtraInfo() != null )
        {
            return getNpmExtraInfo().equals( that.getNpmExtraInfo() );
        }
        else if ( getTypeInfo() != null )
        {
            return getTypeInfo().equals( that.getTypeInfo() );
        }
        else
        {
            return that.getMavenExtraInfo() == null && that.getNpmExtraInfo() == null;
        }
    }

    @Override
    public int hashCode()
    {
        int result = mavenExtraInfo != null ? mavenExtraInfo.hashCode() : 0;
        result = 31 * result + ( npmExtraInfo != null ? npmExtraInfo.hashCode() : 0 );
        result = 31 * result + ( typeInfo != null ? typeInfo.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString()
    {
        if ( getMavenExtraInfo() != null )
        {
            return "FileExtraInfo{" + "mavenExtraInfo=" + mavenExtraInfo + "}";
        }
        else if ( getNpmExtraInfo() != null )
        {
            return "FileExtraInfo{" + "npmExtraInfo=" + npmExtraInfo + "}";
        }
        else if ( getMavenExtraInfo() != null )
        {
            return "FileExtraInfo{" + "typeInfo=" + typeInfo + "}";
        }
        return "null";
    }
}
