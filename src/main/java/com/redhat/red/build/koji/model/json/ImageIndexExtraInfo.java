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
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@StructPart
public class ImageIndexExtraInfo
{
    @JsonProperty( "digests" )
    @DataKey( "digests" )
    private Map<String, String> digests;

    @JsonProperty( "floating_tags" )
    @DataKey( "floating_tags" )
    private List<String> floatingTags;

    @JsonProperty( "pull" )
    @DataKey( "pull" )
    private List<String> pull;

    @JsonProperty( "tags" )
    @DataKey( "tags" )
    private List<String> tags;

    @JsonProperty( "unique_tags" )
    @DataKey( "unique_tags" )
    private List<String> uniqueTags;

    public ImageIndexExtraInfo()
    {

    }

    @JsonCreator
    public ImageIndexExtraInfo( @JsonProperty( "digests" ) Map<String, String> digests,  @JsonProperty( "floating_tags" ) List<String> floatingTags,  @JsonProperty( "pull" ) List<String> pull,  @JsonProperty( "tags" ) List<String> tags, @JsonProperty( "unique_tags" ) List<String> uniqueTags )
    {
        this.digests = digests;
        this.floatingTags = floatingTags;
        this.pull = pull;
        this.tags = tags;
        this.uniqueTags = uniqueTags;
    }

    public Map<String, String> getDigests()
    {
        return digests;
    }

    public void setDigests( Map<String, String> digests )
    {
        this.digests = digests;
    }

    public List<String> getFloatingTags()
    {
        return floatingTags;
    }

    public void setFloatingTags( List<String> floatingTags )
    {
        this.floatingTags = floatingTags;
    }

    public List<String> getPull()
    {
        return pull;
    }

    public void setPull( List<String> pull )
    {
        this.pull = pull;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags( List<String> tags )
    {
        this.tags = tags;
    }

    public List<String> getUniqueTags()
    {
        return uniqueTags;
    }

    public void setUniqueTags( List<String> uniqueTags )
    {
        this.uniqueTags = uniqueTags;
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        ImageIndexExtraInfo that = (ImageIndexExtraInfo) o;
        return Objects.equals( digests, that.digests ) && Objects.equals( floatingTags, that.floatingTags ) && Objects.equals( pull, that.pull ) && Objects.equals( tags, that.tags ) && Objects.equals( uniqueTags, that.uniqueTags );
    }

    @Override
    public int hashCode() {
        return Objects.hash(digests, floatingTags, pull, tags, uniqueTags);
    }

    @Override
    public String toString()
    {
        return "ImageIndexExtraInfo{" +
                "digests=" + digests +
                ", floatingTags=" + floatingTags +
                ", pull=" + pull +
                ", tags=" + tags +
                ", uniqueTags=" + uniqueTags +
                '}';
    }
}
