/**
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
package com.redhat.red.build.koji.model.xmlrpc;

import java.util.List;

import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import com.redhat.red.build.koji.model.converter.StringListConverter;

@StructPart
public class KojiRpmQuery
        extends KojiQuery
{
    @DataKey( "buildID" )
    private Integer buildId;

    @DataKey( "buildrootID" )
    private Integer buildrootId;

    @DataKey( "imageID" )
    private Integer imageId;

    @DataKey( "componentBuildrootID" )
    private Integer componentBuildrootId;

    @DataKey( "hostID" )
    private Integer hostId;

    @DataKey( "arches" )
    @Converter( StringListConverter.class )
    private List<String> arches;

    public KojiRpmQuery()
    {

    }

    public Integer getBuildId()
    {
        return buildId;
    }

    public void setBuildId( Integer buildId )
    {
        this.buildId = buildId;
    }

    public KojiRpmQuery withBuildId( Integer buildId )
    {
        this.buildId = buildId;
        return this;
    }

    public Integer getBuildrootId()
    {
        return buildrootId;
    }

    public void setBuildrootId( Integer buildrootId )
    {
        this.buildrootId = buildrootId;
    }

    public KojiRpmQuery withBuildrootId( Integer buildrootId )
    {
        this.buildrootId = buildrootId;
        return this;
    }

    public Integer getImageId()
    {
        return imageId;
    }

    public void setImageId( Integer imageId )
    {
        this.imageId = imageId;
    }

    public KojiRpmQuery withImageId( Integer imageId )
    {
        this.imageId = imageId;
        return this;
    }

    public Integer getComponentBuildrootId()
    {
        return componentBuildrootId;
    }

    public void setComponentBuildrootId( Integer componentBuildrootId )
    {
        this.componentBuildrootId = componentBuildrootId;
    }

    public KojiRpmQuery withComponentBuildrootId( Integer componentBuildrootId )
    {
        this.componentBuildrootId = componentBuildrootId;
        return this;
    }
    public Integer getHostId()
    {
        return hostId;
    }

    public void setHostId( Integer hostId )
    {
        this.hostId = hostId;
    }

    public KojiRpmQuery withHostId( Integer hostId )
    {
        this.hostId = hostId;
        return this;
    }

    public List<String> getArches()
    {
        return arches;
    }

    public void setArches( List<String> arches )
    {
        this.arches = arches;
    }

    public KojiRpmQuery withArches( List<String> arches )
    {
        this.arches = arches;
        return this;
    }

    @Override
    public String toString() {
        return "KojiRpmQuery{" +
                "buildId=" + buildId +
                ", buildrootId=" + buildrootId +
                ", imageId=" + imageId +
                ", componentBuildrootId=" + componentBuildrootId +
                ", hostId=" + hostId +
                ", arches=" + arches +
                ", " + super.toString() +
                "}";
    }
}
