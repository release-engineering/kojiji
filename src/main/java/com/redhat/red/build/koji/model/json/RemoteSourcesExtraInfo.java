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
import java.util.Objects;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ARCHIVES;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.NAME;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.URL;

/**
 * Created by dwalluck on 2025-04-09.
 */
@StructPart
public class RemoteSourcesExtraInfo
{
    @JsonProperty( ARCHIVES )
    @DataKey( ARCHIVES )
    @Converter( StringListConverter.class )
    private List<String> archives;

    @JsonProperty( NAME )
    @DataKey( NAME )
    private String name;

    @JsonProperty( URL )
    @DataKey( URL )
    private String url;

    public RemoteSourcesExtraInfo()
    {

    }

    @JsonCreator
    public RemoteSourcesExtraInfo( List<String> archives, String name, String url )
    {
        this.archives = archives;
        this.name = name;
        this.url = url;
    }

    public List<String> getArchives()
    {
        return archives;
    }

    public void setArchives( List<String> archives )
    {
        this.archives = archives;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        RemoteSourcesExtraInfo that = (RemoteSourcesExtraInfo) o;
        return Objects.equals( archives, that.archives ) && Objects.equals( name, that.name ) && Objects.equals( url, that.url );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( archives, name, url );
    }

    @Override
    public String toString()
    {
        return "RemoteSourcesExtraInfo{archives=" + archives + ", name='" + name + '\'' + ", url='" + url + "'}";
    }
}
