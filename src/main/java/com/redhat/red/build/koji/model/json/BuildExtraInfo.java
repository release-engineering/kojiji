/*
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
package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.atlas.npm.ident.ref.NpmPackageRef;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.BUILD_SYSTEM;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.EXTERNAL_BUILD_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.EXTERNAL_BUILD_URL;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.IMPORT_INITIATOR;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.MAVEN_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.NPM_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.SCM_TAG;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.TYPEINFO;

/**
 * Created by jdcasey on 9/15/16.
 */
@StructPart
@JsonInclude( Include.NON_NULL )
public class BuildExtraInfo
{
    @JsonProperty( MAVEN_INFO )
    @DataKey( MAVEN_INFO )
    private MavenExtraInfo mavenExtraInfo;

    @JsonProperty( NPM_INFO )
    @DataKey( NPM_INFO )
    private NpmExtraInfo npmExtraInfo;

    @JsonProperty( EXTERNAL_BUILD_ID )
    @DataKey( EXTERNAL_BUILD_ID )
    private String externalBuildId;

    @JsonProperty( BUILD_SYSTEM )
    @DataKey( BUILD_SYSTEM )
    private String buildSystem;

    @JsonProperty( EXTERNAL_BUILD_URL )
    @DataKey( EXTERNAL_BUILD_URL )
    private String externalBuildUrl;

    @JsonProperty( IMPORT_INITIATOR )
    @DataKey( IMPORT_INITIATOR )
    private String importInitiator;

    @JsonProperty( SCM_TAG )
    @DataKey( SCM_TAG )
    private String scmTag;

    @JsonProperty( TYPEINFO )
    @DataKey( TYPEINFO )
    private TypeInfoExtraInfo typeInfo;

    public BuildExtraInfo(){}

    public BuildExtraInfo( MavenExtraInfo mavenExtraInfo )
    {
        this.mavenExtraInfo = mavenExtraInfo;
    }

    public BuildExtraInfo( NpmExtraInfo npmExtraInfo )
    {
        this.npmExtraInfo = npmExtraInfo;
    }

    public BuildExtraInfo( ProjectVersionRef gav )
    {
        this.mavenExtraInfo = new MavenExtraInfo( gav );
    }

    public BuildExtraInfo( NpmPackageRef nv )
    {
        this.npmExtraInfo = new NpmExtraInfo( nv );
    }

    public MavenExtraInfo getMavenExtraInfo()
    {
        return mavenExtraInfo;
    }

    public void setMavenExtraInfo( MavenExtraInfo mavenExtraInfo )
    {
        this.mavenExtraInfo = mavenExtraInfo;
    }

    public NpmExtraInfo getNpmExtraInfo() {
        return npmExtraInfo;
    }

    public void setNpmExtraInfo(NpmExtraInfo npmExtraInfo) {
        this.npmExtraInfo = npmExtraInfo;
    }

    public String getExternalBuildId()
    {
        return externalBuildId;
    }

    public void setExternalBuildId( String externalBuildId )
    {
        this.externalBuildId = externalBuildId;
    }

    public String getBuildSystem() {
        return buildSystem;
    }

    public void setBuildSystem(String buildSystem) {
        this.buildSystem = buildSystem;
    }

    public String getExternalBuildUrl() {
        return externalBuildUrl;
    }

    public void setExternalBuildUrl(String externalBuildUrl) {
        this.externalBuildUrl = externalBuildUrl;
    }

    public String getImportInitiator()
    {
        return importInitiator;
    }

    public void setImportInitiator( String importInitiator )
    {
        this.importInitiator = importInitiator;
    }

    public String getScmTag()
    {
        return scmTag;
    }

    public void setScmTag( String scmTag )
    {
        this.scmTag = scmTag;
    }

    public TypeInfoExtraInfo getTypeInfo()
    {
        return typeInfo;
    }

    public void setTypeInfo( TypeInfoExtraInfo typeInfo )
    {
        this.typeInfo = typeInfo;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildExtraInfo ) )
        {
            return false;
        }

        BuildExtraInfo that = (BuildExtraInfo) o;

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
        else if ( getTypeInfo() != null )
        {
            return "FileExtraInfo{" + "typeInfo=" + typeInfo + "}";
        }
        return "null";
    }
}
