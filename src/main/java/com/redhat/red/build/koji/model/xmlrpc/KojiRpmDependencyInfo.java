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
package com.redhat.red.build.koji.model.xmlrpc;

import java.util.Objects;

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

@StructPart
public class KojiRpmDependencyInfo
{
    @DataKey( "name" )
    private String name;

    @DataKey( "version" )
    private String version;

    @DataKey( "flags" )
    private Integer flags;

    @DataKey( "type" )
    private Integer type;

    public KojiRpmDependencyInfo()
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

    public void setFlags( Integer flags )
    {
        this.flags = flags;
    }

    public Integer getFlags()
    {
        return flags;
    }

    public void setType( Integer type )
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( super.equals( obj ) )
        {
            return true;
        }

        if ( !( obj instanceof KojiRpmDependencyInfo ) )
        {
            return false;
        }

        KojiRpmDependencyInfo that = (KojiRpmDependencyInfo) obj;

        return Objects.equals( this.name, that.name ) && Objects.equals( this.version, that.version ) && Objects.equals( this.flags, that.flags ) && Objects.equals( this.type, that.type );
    }

    @Override
    public String toString()
    {
        return "KojiRpmDependencyInfo{" +
                "name='" + name +
                "', version='" + version +
                "', flags=" + flags
                + ", type=" + type +
                "}";
    }
}
