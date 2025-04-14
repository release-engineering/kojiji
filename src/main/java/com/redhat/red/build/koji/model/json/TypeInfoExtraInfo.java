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

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.NPM_TYPE_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.REMOTE_SOURCE_FILE;

/**
 * Created by jbrazdil on 2021-04-08.
 */
@StructPart
@JsonInclude( Include.NON_NULL )
public class TypeInfoExtraInfo
{
    @JsonProperty( REMOTE_SOURCE_FILE )
    @DataKey( REMOTE_SOURCE_FILE )
    private RemoteSourceFileExtraInfo remoteSourceFileExtraInfo;

    @JsonProperty( NPM_TYPE_INFO )
    @DataKey( NPM_TYPE_INFO )
    private NpmTypeInfoExtraInfo npmTypeInfoExtraInfo;


    @JsonCreator
    public TypeInfoExtraInfo(
            @JsonProperty(REMOTE_SOURCE_FILE) RemoteSourceFileExtraInfo sourceFileInfo,
            @JsonProperty(NPM_TYPE_INFO) NpmTypeInfoExtraInfo npm)
    {
        this.remoteSourceFileExtraInfo = sourceFileInfo;
        this.npmTypeInfoExtraInfo = npm;
    }

    public TypeInfoExtraInfo( @JsonProperty(NPM_TYPE_INFO) NpmTypeInfoExtraInfo npm )
    {
        this.npmTypeInfoExtraInfo = npm;
    }

    public TypeInfoExtraInfo()
    {
    }

    public RemoteSourceFileExtraInfo getRemoteSourceFileExtraInfo()
    {
        return remoteSourceFileExtraInfo;
    }

    public void setRemoteSourceFileExtraInfo(RemoteSourceFileExtraInfo remoteSourceFileExtraInfo)
    {
        this.remoteSourceFileExtraInfo = remoteSourceFileExtraInfo;
    }

    public NpmTypeInfoExtraInfo getNpmTypeInfoExtraInfo()
    {
        return npmTypeInfoExtraInfo;
    }

    public void setNpmTypeInfoExtraInfo(NpmTypeInfoExtraInfo npmTypeInfoExtraInfo)
    {
        this.npmTypeInfoExtraInfo = npmTypeInfoExtraInfo;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof TypeInfoExtraInfo) )
        {
            return false;
        }

        TypeInfoExtraInfo that = (TypeInfoExtraInfo) o;

        return getRemoteSourceFileExtraInfo() != null ? getRemoteSourceFileExtraInfo().equals( that.getRemoteSourceFileExtraInfo() ) : that.getRemoteSourceFileExtraInfo() == null
                && getNpmTypeInfoExtraInfo() != null ? getNpmTypeInfoExtraInfo().equals( that.getNpmTypeInfoExtraInfo() ) : that.getNpmTypeInfoExtraInfo() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getRemoteSourceFileExtraInfo() == null ? 0 : getRemoteSourceFileExtraInfo().hashCode();
        result = 31 * result + (getNpmTypeInfoExtraInfo() == null ? 0 : getNpmTypeInfoExtraInfo().hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        return "TypeInfoExtraInfo{"
                + "remoteSourceFileExtraInfo='" + remoteSourceFileExtraInfo + "', "
                + "npmTypeInfoExtraInfo='" + npmTypeInfoExtraInfo + "'}";
    }
}
