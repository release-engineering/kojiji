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

import java.util.Objects;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.NPM_TYPE_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.REMOTE_SOURCES;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.REMOTE_SOURCE_FILE;

/**
 * Created by jbrazdil on 2021-04-08.
 */
@StructPart
@JsonInclude( Include.NON_NULL )
public class TypeInfoExtraInfo
{
    @JsonProperty( REMOTE_SOURCES )
    @DataKey( REMOTE_SOURCES )
    private RemoteSourcesExtraInfo remoteSourcesExtraInfo;

    @JsonProperty( REMOTE_SOURCE_FILE )
    @DataKey( REMOTE_SOURCE_FILE )
    private RemoteSourceFileExtraInfo remoteSourceFileExtraInfo;

    @JsonProperty( NPM_TYPE_INFO )
    @DataKey( NPM_TYPE_INFO )
    private NpmTypeInfoExtraInfo npmTypeInfoExtraInfo;

    public TypeInfoExtraInfo( @JsonProperty( REMOTE_SOURCES ) RemoteSourcesExtraInfo remoteSourcesExtraInfo )
    {
        this.remoteSourcesExtraInfo = remoteSourcesExtraInfo;
    }

    @JsonCreator
    public TypeInfoExtraInfo(
            @JsonProperty( REMOTE_SOURCE_FILE ) RemoteSourceFileExtraInfo sourceFileInfo,
            @JsonProperty( NPM_TYPE_INFO ) NpmTypeInfoExtraInfo npm )
    {
        this.remoteSourceFileExtraInfo = sourceFileInfo;
        this.npmTypeInfoExtraInfo = npm;
    }

    public TypeInfoExtraInfo( @JsonProperty( NPM_TYPE_INFO ) NpmTypeInfoExtraInfo npm )
    {
        this.npmTypeInfoExtraInfo = npm;
    }

    public TypeInfoExtraInfo()
    {
    }

    public RemoteSourcesExtraInfo getRemoteSourcesExtraInfo()
    {
        return remoteSourcesExtraInfo;
    }

    public void setRemoteSourcesExtraInfo( RemoteSourcesExtraInfo remoteSourcesExtraInfo )
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

    @Override
    public boolean equals( Object o )
    {
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        TypeInfoExtraInfo that = (TypeInfoExtraInfo) o;
        return Objects.equals( remoteSourcesExtraInfo, that.remoteSourcesExtraInfo ) && Objects.equals( remoteSourceFileExtraInfo, that.remoteSourceFileExtraInfo ) && Objects.equals( npmTypeInfoExtraInfo, that.npmTypeInfoExtraInfo );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( remoteSourcesExtraInfo, remoteSourceFileExtraInfo, npmTypeInfoExtraInfo );
    }

    @Override
    public String toString() {
        return "TypeInfoExtraInfo{remoteSourcesExtraInfo=" + remoteSourcesExtraInfo + ", remoteSourceFileExtraInfo=" + remoteSourceFileExtraInfo + ", npmTypeInfoExtraInfo=" + npmTypeInfoExtraInfo + '}';
    }
}
