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

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import com.redhat.red.build.koji.model.converter.TimestampIntConverter;

@StructPart
public class KojiRpmInfo
{
    @DataKey( "id" )
    private Integer id;

    @DataKey( "name" )
    private String name;

    @DataKey( "version" )
    private String version;

    @DataKey( "release" )
    private String release;

    @DataKey( "nvr" )
    private String nvr;

    @DataKey( "arch" )
    private String arch;

    @DataKey( "epoch" )
    private String epoch;

    @DataKey( "payloadhash" )
    private String payloadhash;

    @DataKey( "size" )
    private Long size;

    @DataKey( "buildtime")
    @Converter( TimestampIntConverter.class )
    private Date buildtime;

    @DataKey( "build_id" )
    private Integer buildId;

    @DataKey( "buildroot_id" )
    private Integer buildrootId;

    @DataKey( "external_repo_id")
    private Integer externalRepoId;

    @DataKey( "external_repo_name")
    private String externalRepoName;

    @DataKey( "metadata_only" )
    private Boolean metadataOnly;

    @DataKey( "extra" )
    Map<String, Object> extra;

    @DataKey( "component_buildroot_id" )
    private Integer componentBuildrootId;

    @DataKey( "is_update" )
    private Boolean isUpdate;

    public KojiRpmInfo()
    {

    }

    public KojiRpmInfo( int id, int buildId, String name, String version, String release, String arch )
    {
        this.id = id;
        this.buildId = buildId;
        this.name = name;
        this.version = version;
        this.release = release;
        this.arch = arch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
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

    public String getRelease()
    {
        return release;
    }

    public void setNvr( String nvr )
    {
        this.nvr = nvr;
    }

    public String getNvr()
    {
        return nvr;
    }

    public void setArch( String arch )
    {
        this.arch = arch;
    }

    public String getArch()
    {
        return arch;
    }

    public void setRelease( String release )
    {
        this.release = release;
    }

    public String getEpoch()
    {
        return epoch;
    }

    public void setEpoch( String epoch )
    {
        this.epoch = epoch;
    }

    public String getPayloadhash()
    {
        return payloadhash;
    }

    public void setPayloadhash( String payloadhash )
    {
        this.payloadhash = payloadhash;
    }

    public Long getSize()
    {
        return size;
    }

    public void setSize( Long size )
    {
        this.size = size;
    }

    public Date getBuildtime()
    {
        return buildtime;
    }

    public void setBuildtime( Date buildtime )
    {
        this.buildtime = buildtime;
    }

    public Integer getBuildId()
    {
        return buildId;
    }

    public void setBuildId( Integer buildId )
    {
        this.buildId = buildId;
    }

    public Integer getBuildrootId()
    {
        return buildrootId;
    }

    public void setBuildrootId( Integer buildrootId )
    {
        this.buildrootId = buildrootId;
    }

    public Integer getExternalRepoId()
    {
        return externalRepoId;
    }

    public void setExternalRepoId( Integer externalRepoId )
    {
        this.externalRepoId = externalRepoId;
    }

    public String getExternalRepoName()
    {
        return externalRepoName;
    }

    public void setExternalRepoName( String externalRepoName )
    {
        this.externalRepoName = externalRepoName;
    }

    public Boolean getMetadataOnly()
    {
        return metadataOnly;
    }

    public void setMetadataOnly( Boolean metadataOnly )
    {
        this.metadataOnly = metadataOnly;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra( Map<String, Object> extra ) {
        this.extra = extra;
    }

    public Integer getComponentBuildrootId()
    {
        return componentBuildrootId;
    }

    public void setComponentBuildrootId( Integer componentBuildrootId )
    {
        this.componentBuildrootId = componentBuildrootId;
    }

    public Boolean getIsUpdate()
    {
        return isUpdate;
    }

    public void setIsUpdate( Boolean isUpdate )
    {
        this.isUpdate = isUpdate;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( super.equals( obj ) )
        {
            return true;
        }

        if ( !( obj instanceof KojiRpmInfo ) )
        {
            return false;
        }

        KojiRpmInfo that = (KojiRpmInfo) obj;

        return Objects.equals(this.id, that.id);
    }

    @Override
    public String toString()
    {
        return "KojiRpmBuildInfo{" +
               "id=" + id +
               ", name='" + name +
               "', version='" + version +
               "', release='" + release +
               "', nvr='" + nvr +
               "', epoch='" + epoch +
               "', payloadhash='" + payloadhash +
               "', size=" + size +
               ", buildtime=" + buildtime +
               ", buildId=" + buildId +
               ", buildrootId=" + buildrootId +
               ", externalRepoId=" + externalRepoId +
               ", externalRepoName='" + externalRepoName +
               "', metadataOnly=" + metadataOnly +
               ", extra=" + extra +
               ", componentBuildrootId=" + componentBuildrootId +
               ", isUpdate=" + isUpdate +
               "}";
    }
}
