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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.atlas.npm.ident.ref.NpmPackageRef;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.Objects;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.BUILD_SYSTEM;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.CONTAINER_KOJI_TASK_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.EXTERNAL_BUILD_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.EXTERNAL_BUILD_URL;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.FILESYSTEM_KOJI_TASK_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.IMAGE_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.IMPORT_INITIATOR;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.MAVEN_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.NPM_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.OSBS_BUILD;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.SCM_TAG;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.SUBMITTER;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.TYPEINFO;

/**
 * Created by jdcasey on 9/15/16.
 */
@StructPart
@JsonInclude( Include.NON_NULL )
public class BuildExtraInfo
{
    @JsonProperty(  CONTAINER_KOJI_TASK_ID  )
    @DataKey( CONTAINER_KOJI_TASK_ID )
    private Integer containerKojiTaskId;

    @JsonProperty( FILESYSTEM_KOJI_TASK_ID )
    @DataKey( FILESYSTEM_KOJI_TASK_ID )
    private Integer filesystemKojiTaskId;

    @JsonProperty( MAVEN_INFO )
    @DataKey( MAVEN_INFO )
    private MavenExtraInfo mavenExtraInfo;

    @JsonProperty( NPM_INFO )
    @DataKey( NPM_INFO )
    private NpmExtraInfo npmExtraInfo;

    @JsonProperty( IMAGE_INFO )
    @DataKey( IMAGE_INFO )
    private ImageExtraInfo imageExtraInfo;

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

    @JsonProperty( OSBS_BUILD )
    @DataKey( OSBS_BUILD )
    private OsbsBuildExtraInfo osbsBuild;

    @JsonProperty( SUBMITTER )
    @DataKey( SUBMITTER )
    private String submitter;

    @JsonProperty( TYPEINFO )
    @DataKey( TYPEINFO )
    private TypeInfoExtraInfo typeInfo;

    public BuildExtraInfo(){}

    public BuildExtraInfo( ImageExtraInfo imageExtraInfo )
    {
        this.imageExtraInfo = imageExtraInfo;
    }

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

    public ImageExtraInfo getImageExtraInfo()
    {
        return imageExtraInfo;
    }

    public void setImageExtraInfo( ImageExtraInfo imageExtraInfo )
    {
        this.imageExtraInfo = imageExtraInfo;
    }

    public MavenExtraInfo getMavenExtraInfo()
    {
        return mavenExtraInfo;
    }

    public void setMavenExtraInfo( MavenExtraInfo mavenExtraInfo )
    {
        this.mavenExtraInfo = mavenExtraInfo;
    }

    public NpmExtraInfo getNpmExtraInfo()
    {
        return npmExtraInfo;
    }

    public void setNpmExtraInfo( NpmExtraInfo npmExtraInfo )
    {
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

    public String getBuildSystem()
    {
        return buildSystem;
    }

    public void setBuildSystem( String buildSystem )
    {
        this.buildSystem = buildSystem;
    }

    public String getExternalBuildUrl()
    {
        return externalBuildUrl;
    }

    public void setExternalBuildUrl( String externalBuildUrl )
    {
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

    public void setContainerKojiTaskId( Integer containerKojiTaskId )
    {
        this.containerKojiTaskId = containerKojiTaskId;
    }

    public Integer getContainerKojiTaskId()
    {
        return containerKojiTaskId;
    }

    public void setFilesystemKojiTaskId( Integer filesystemKojiTaskId )
    {
        this.filesystemKojiTaskId = filesystemKojiTaskId;
    }

    public Integer getFilesystemKojiTaskId()
    {
        return filesystemKojiTaskId;
    }

    public OsbsBuildExtraInfo getOsbsBuild()
    {
        return osbsBuild;
    }

    public void setOsbsBuild( OsbsBuildExtraInfo osbsBuild )
    {
        this.osbsBuild = osbsBuild;
    }

    public String getSubmitter()
    {
        return submitter;
    }

    public void setSubmitter( String submitter )
    {
        this.submitter = submitter;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        BuildExtraInfo that = (BuildExtraInfo ) o;
        return Objects.equals( mavenExtraInfo, that.mavenExtraInfo ) && Objects.equals( npmExtraInfo, that.npmExtraInfo ) && Objects.equals( imageExtraInfo, that.imageExtraInfo ) && Objects.equals( externalBuildId, that.externalBuildId ) && Objects.equals( buildSystem, that.buildSystem ) && Objects.equals( externalBuildUrl, that.externalBuildUrl ) && Objects.equals( importInitiator, that.importInitiator ) && Objects.equals( scmTag, that.scmTag ) && Objects.equals( typeInfo, that.typeInfo ) && Objects.equals( containerKojiTaskId, that.containerKojiTaskId ) && Objects.equals( filesystemKojiTaskId, that.filesystemKojiTaskId ) && Objects.equals( osbsBuild, that.osbsBuild ) && Objects.equals( submitter, that.submitter );
    }

    @Override
    public int hashCode() {
        return Objects.hash( mavenExtraInfo, npmExtraInfo, imageExtraInfo, externalBuildId, buildSystem, externalBuildUrl, importInitiator, scmTag, typeInfo, containerKojiTaskId, filesystemKojiTaskId, osbsBuild, submitter );
    }

    @Override
    public String toString()
    {
        return "BuildExtraInfo{" +
                "containerKojiTaskId=" + containerKojiTaskId +
                ", filesystemKojiTaskId=" + filesystemKojiTaskId +
                ", mavenExtraInfo=" + mavenExtraInfo +
                ", npmExtraInfo=" + npmExtraInfo +
                ", imageExtraInfo=" + imageExtraInfo +
                ", externalBuildId='" + externalBuildId + '\'' +
                ", buildSystem='" + buildSystem + '\'' +
                ", externalBuildUrl='" + externalBuildUrl + '\'' +
                ", importInitiator='" + importInitiator + '\'' +
                ", scmTag='" + scmTag + '\'' +
                ", typeInfo=" + typeInfo +
                ", osbsBuild=" + osbsBuild +
                ", submitter='" + submitter + '\'' +
                '}';
    }
}
