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

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ICM_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.IMAGE_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.NPM_TYPE_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.REMOTE_SOURCES;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.REMOTE_SOURCE_FILE;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.RPM;

/**
 * Created by jbrazdil on 2021-04-08.
 */
@StructPart
@JsonInclude( Include.NON_NULL )
public class TypeInfoExtraInfo
{
    @JsonProperty( REMOTE_SOURCES )
    @DataKey( REMOTE_SOURCES )
    private List<RemoteSourcesExtraInfo> remoteSourcesExtraInfo;

    @JsonProperty( REMOTE_SOURCE_FILE )
    @DataKey( REMOTE_SOURCE_FILE )
    private RemoteSourceFileExtraInfo remoteSourceFileExtraInfo;

    @JsonProperty( NPM_TYPE_INFO )
    @DataKey( NPM_TYPE_INFO )
    private NpmTypeInfoExtraInfo npmTypeInfoExtraInfo;

    @JsonProperty( RPM )
    @DataKey( RPM )
    private RpmTypeInfoExtraInfo rpmTypeInfoExtraInfo;

    @JsonProperty( IMAGE_INFO )
    @DataKey( IMAGE_INFO )
    private ImageExtraInfo imageExtraInfo;

    @JsonProperty( ICM_INFO )
    @DataKey( ICM_INFO )
    private IcmExtraInfo icmExtraInfo;

    @JsonCreator
    public TypeInfoExtraInfo( @JsonProperty( REMOTE_SOURCE_FILE ) RemoteSourceFileExtraInfo sourceFileInfo,
                              @JsonProperty( NPM_TYPE_INFO ) NpmTypeInfoExtraInfo npm,
                              @JsonProperty( REMOTE_SOURCES ) List<RemoteSourcesExtraInfo> remoteSourcesExtraInfo )
    {
        this.remoteSourceFileExtraInfo = sourceFileInfo;
        this.npmTypeInfoExtraInfo = npm;
        this.remoteSourcesExtraInfo = remoteSourcesExtraInfo;
    }


    public TypeInfoExtraInfo( @JsonProperty( REMOTE_SOURCES ) List<RemoteSourcesExtraInfo> sourcesExtraInfo )
    {
        this.remoteSourcesExtraInfo = sourcesExtraInfo;
    }

    public TypeInfoExtraInfo( @JsonProperty( NPM_TYPE_INFO ) NpmTypeInfoExtraInfo npm )
    {
        this.npmTypeInfoExtraInfo = npm;
    }

    public TypeInfoExtraInfo()
    {
    }

    public List<RemoteSourcesExtraInfo> getRemoteSourcesExtraInfo()
    {
        return remoteSourcesExtraInfo;
    }

    public void setRemoteSourcesExtraInfo(List<RemoteSourcesExtraInfo> remoteSourcesExtraInfo )
    {
        this.remoteSourcesExtraInfo = remoteSourcesExtraInfo;
    }

    public RemoteSourceFileExtraInfo getRemoteSourceFileExtraInfo()
    {
        return remoteSourceFileExtraInfo;
    }

    public void setRemoteSourceFileExtraInfo( RemoteSourceFileExtraInfo remoteSourceFileExtraInfo )
    {
        this.remoteSourceFileExtraInfo = remoteSourceFileExtraInfo;
    }

    public NpmTypeInfoExtraInfo getNpmTypeInfoExtraInfo()
    {
        return npmTypeInfoExtraInfo;
    }

    public void setNpmTypeInfoExtraInfo( NpmTypeInfoExtraInfo npmTypeInfoExtraInfo )
    {
        this.npmTypeInfoExtraInfo = npmTypeInfoExtraInfo;
    }

    public RpmTypeInfoExtraInfo getRpmTypeInfoExtraInfo()
    {
        return rpmTypeInfoExtraInfo;
    }

    public void setRpmTypeInfoExtraInfo( RpmTypeInfoExtraInfo rpmTypeInfoExtraInfo )
    {
        this.rpmTypeInfoExtraInfo = rpmTypeInfoExtraInfo;
    }

    public ImageExtraInfo getImageExtraInfo()
    {
        return imageExtraInfo;
    }

    public void setImageExtraInfo( ImageExtraInfo imageExtraInfo )
    {
        this.imageExtraInfo = imageExtraInfo;
    }

    public IcmExtraInfo getIcmExtraInfo()
    {
        return icmExtraInfo;
    }

    public void setIcmExtraInfo( IcmExtraInfo icmExtraInfo )
    {
        this.icmExtraInfo = icmExtraInfo;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        TypeInfoExtraInfo that = (TypeInfoExtraInfo) o;
        return Objects.equals( remoteSourcesExtraInfo, that.remoteSourcesExtraInfo ) && Objects.equals( remoteSourceFileExtraInfo, that.remoteSourceFileExtraInfo ) && Objects.equals( npmTypeInfoExtraInfo, that.npmTypeInfoExtraInfo ) && Objects.equals( rpmTypeInfoExtraInfo, that.rpmTypeInfoExtraInfo )&& Objects.equals( imageExtraInfo, that.imageExtraInfo ) && Objects.equals( icmExtraInfo, that.icmExtraInfo );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( remoteSourcesExtraInfo, remoteSourceFileExtraInfo, npmTypeInfoExtraInfo, rpmTypeInfoExtraInfo, imageExtraInfo, icmExtraInfo );
    }

    @Override
    public String toString() {
        return "TypeInfoExtraInfo{remoteSourcesExtraInfo=" + remoteSourcesExtraInfo + ", remoteSourceFileExtraInfo=" + remoteSourceFileExtraInfo + ", npmTypeInfoExtraInfo=" + npmTypeInfoExtraInfo + ", rpmTypeInfoExtraInfo=" + rpmTypeInfoExtraInfo + ", imageExtraInfo=" + imageExtraInfo + ", icmExtraInfo=" + icmExtraInfo + '}';
    }
}
