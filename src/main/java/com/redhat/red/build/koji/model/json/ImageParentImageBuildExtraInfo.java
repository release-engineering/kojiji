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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.Objects;

@StructPart
public class ImageParentImageBuildExtraInfo
{
    @DataKey( "id" )
    @JsonProperty( "id" )
    private Integer id;

    @DataKey( "nvr" )
    @JsonProperty( "nvr" )
    private String nvr;

    public ImageParentImageBuildExtraInfo()
    {

    }

    @JsonCreator
    public ImageParentImageBuildExtraInfo( @JsonProperty( "id" ) Integer id, @JsonProperty( "nvr" ) String nvr )
    {
        this.id = id;
        this.nvr = nvr;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getNvr()
    {
        return nvr;
    }

    public void setNvr( String nvr )
    {
        this.nvr = nvr;
    }

    @Override
    public boolean equals(Object o)
    {
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        ImageParentImageBuildExtraInfo that = (ImageParentImageBuildExtraInfo) o;
        return Objects.equals( id, that.id ) && Objects.equals( nvr, that.nvr );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( id, nvr );
    }

    @Override
    public String toString()
    {
        return "ImageParentImageBuildExtraInfo{" +
                "id=" + id +
                ", nvr='" + nvr + '\'' +
                '}';
    }
}
