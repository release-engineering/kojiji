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
import com.redhat.red.build.koji.model.converter.StringListConverter;
import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.List;
import java.util.Objects;

@StructPart
public class OsbsBuildExtraInfo
{
    @JsonProperty( "engine" )
    @DataKey( "engine" )
    private String engine;

    @JsonProperty( "kind" )
    @DataKey( "kind" )
    private String kind;

    @JsonProperty( "subtypes" )
    @DataKey( "subtypes" )
    @Converter( StringListConverter.class )
    private List<String> subtypes;

    public String getEngine()
    {
        return engine;
    }

    public void setEngine( String engine )
    {
        this.engine = engine;
    }

    public String getKind()
    {
        return kind;
    }

    public void setKind( String kind )
    {
        this.kind = kind;
    }

    public List<String> getSubtypes()
    {
        return subtypes;
    }

    public void setSubtypes( List<String> subtypes )
    {
        this.subtypes = subtypes;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        OsbsBuildExtraInfo that = (OsbsBuildExtraInfo) o;
        return Objects.equals( engine, that.engine ) && Objects.equals( kind, that.kind ) && Objects.equals( subtypes, that.subtypes );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( engine, kind, subtypes );
    }

    @Override
    public String toString()
    {
        return "OsbsBuildExtraInfo{" +
                "engine='" + engine + '\'' +
                ", kind='" + kind + '\'' +
                ", subtypes=" + subtypes +
                '}';
    }
}
