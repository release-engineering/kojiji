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
import com.redhat.red.build.koji.model.converter.StringListConverter;
import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by dwalluck on 4/17/25.
 */
@StructPart
public class ImageExtraInfo
{
    @JsonProperty( "help" )
    @DataKey( "help" )
    private String help;

    @JsonProperty( "index" )
    @DataKey( "index" )
    private ImageIndexExtraInfo index;

    @JsonProperty( "isolated" )
    @DataKey( "isolated" )
    private Boolean isolated;

    @JsonProperty( "media_types" )
    @DataKey( "media_types" )
    @Converter( StringListConverter.class )
    private List<String> mediaTypes;

    @JsonProperty( "parent_build_id" )
    @DataKey( "parent_build_id" )
    private Integer parentBuildId;

    @JsonProperty( "parent_image_builds" )
    @DataKey( "parent_image_builds" )
    private Map<String, ImageParentImageBuildExtraInfo> parentImageBuilds;

    @JsonProperty( "parent_images" )
    @DataKey( "parent_images" )
    @Converter( StringListConverter.class )
    private List<String> parentImages;

    @JsonProperty( "remote_sources" )
    @DataKey( "remote_sources" )
    private List<RemoteSourcesExtraInfo> remoteSources;

    @JsonProperty( "yum_repourls" )
    @DataKey( "yum_repourls" )
    @Converter( StringListConverter.class )
    private List<String> yumRepourls;

    public ImageExtraInfo()
    {

    }

    @JsonCreator
    public ImageExtraInfo( @JsonProperty( "help" ) String help,
                           @JsonProperty( "index" ) ImageIndexExtraInfo index,
                           @JsonProperty( "isolated" ) Boolean isolated,
                           @JsonProperty( "media_types" ) List<String> mediaTypes,
                           @JsonProperty( "parent_build_id" ) Integer parentBuildId,
                           @JsonProperty( "parent_image_builds" ) Map<String, ImageParentImageBuildExtraInfo> parentImageBuilds,
                           @JsonProperty( "parent_images" ) List<String> parentImages,
                           @JsonProperty( "remote_sources" ) List<RemoteSourcesExtraInfo> remoteSources,
                           @JsonProperty( "yum_repourls" ) List<String> yumRepourls )
    {
        this.help = help;
        this.index = index;
        this.isolated = isolated;
        this.mediaTypes = mediaTypes;
        this.parentBuildId = parentBuildId;
        this.parentImageBuilds = parentImageBuilds;
        this.parentImages = parentImages;
        this.remoteSources = remoteSources;
        this.yumRepourls = yumRepourls;
    }

    public Object getHelp()
    {
        return help;
    }

    public void setHelp(String help )
    {
        this.help = help;
    }

    public ImageIndexExtraInfo getIndex()
    {
        return index;
    }

    public void setIndex( ImageIndexExtraInfo index )
    {
        this.index = index;
    }

    public Boolean getIsolated()
    {
        return isolated;
    }

    public void setIsolated( Boolean isolated )
    {
        this.isolated = isolated;
    }

    public List<String> getMediaTypes()
    {
        return mediaTypes;
    }

    public void setMediaTypes( List<String> mediaTypes )
    {
        this.mediaTypes = mediaTypes;
    }

    public Integer getParentBuildId()
    {
        return parentBuildId;
    }

    public void setParentBuildId( Integer parentBuildId )
    {
        this.parentBuildId = parentBuildId;
    }

    public Map<String, ImageParentImageBuildExtraInfo> getParentImageBuilds()
    {
        return parentImageBuilds;
    }

    public void setParentImageBuilds(Map<String, ImageParentImageBuildExtraInfo> parentImageBuilds )
    {
        this.parentImageBuilds = parentImageBuilds;
    }

    public List<String> getParentImages()
    {
        return parentImages;
    }

    public void setParentImages( List<String> parentImages )
    {
        this.parentImages = parentImages;
    }

    public List<RemoteSourcesExtraInfo> getRemoteSources()
    {
        return remoteSources;
    }

    public void setRemoteSources( List<RemoteSourcesExtraInfo> remoteSources )
    {
        this.remoteSources = remoteSources;
    }

    public List<String> getYumRepourls()
    {
        return yumRepourls;
    }

    public void setYumRepourls( List<String> yumRepourls )
    {
        this.yumRepourls = yumRepourls;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        ImageExtraInfo that = ( ImageExtraInfo ) o;
        return Objects.equals( help, that.help ) && Objects.equals( index, that.index ) && Objects.equals( isolated, that.isolated ) && Objects.equals( mediaTypes, that.mediaTypes ) && Objects.equals( parentBuildId, that.parentBuildId ) && Objects.equals( parentImageBuilds, that.parentImageBuilds ) && Objects.equals( parentImages, that.parentImages ) && Objects.equals( remoteSources, that.remoteSources ) && Objects.equals( yumRepourls, that.yumRepourls );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( help, index, isolated, mediaTypes, parentBuildId, parentImageBuilds, parentImages, remoteSources, yumRepourls );
    }

    @Override
    public String toString()
    {
        return "ImageExtraInfo{" +
                "help='" + help + '\'' +
                ", index=" + index +
                ", isolated=" + isolated +
                ", mediaTypes=" + mediaTypes +
                ", parentBuildId=" + parentBuildId +
                ", parentImageBuilds=" + parentImageBuilds +
                ", parentImages=" + parentImages +
                ", remoteSources=" + remoteSources +
                ", yumRepourls=" + yumRepourls +
                '}';
    }
}
