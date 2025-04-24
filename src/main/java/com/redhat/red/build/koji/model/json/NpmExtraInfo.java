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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.commonjava.atlas.npm.ident.ref.NpmPackageRef;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.NAME;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.VERSION;

/**
 * Created by ruhan on 2/26/18.
 */
@StructPart
public class NpmExtraInfo
{
    @JsonProperty( NAME )
    @DataKey( NAME )
    private String name;

    @JsonProperty( VERSION )
    @DataKey( VERSION )
    private String version;

    @JsonCreator
    public NpmExtraInfo( @JsonProperty( NAME ) String name,
                         @JsonProperty( VERSION ) String version )
    {
        this.name = name;
        this.version = version;
    }

    public NpmExtraInfo( NpmPackageRef nv )
    {
        this.name = nv.getName();
        this.version = nv.getVersion().toString();
    }

    public NpmExtraInfo()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        NpmExtraInfo that = (NpmExtraInfo) o;

        if ( !name.equals( that.name ) ) {
            return false;
        }
        return version.equals( that.version );

    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "NpmExtraInfo{" + "name='" + name + '\'' + ", version='" + version + '\'' + '}';
    }
}
