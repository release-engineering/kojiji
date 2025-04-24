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

import com.redhat.red.build.koji.model.converter.KojiBuildStateConverter;
import com.redhat.red.build.koji.model.converter.TimestampConverter;
import org.commonjava.atlas.maven.ident.ref.ProjectRef;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.Date;

/**
 * Created by jdcasey on 5/6/16.
 */
@StructPart
public class KojiBuildQuery extends KojiQuery
{
    @DataKey( "type" )
    private String type;

    @DataKey( "typeInfo" )
    private KojiMavenRef mavenRef;

    @DataKey( "packageID" )
    private Integer packageId;

    @DataKey( "userID" )
    private Integer userId;

    @DataKey( "taskID" )
    private Integer taskId;

    @DataKey( "prefix" )
    private String prefix;

    @Converter( KojiBuildStateConverter.class )
    @DataKey( "state" )
    private KojiBuildState state;

    @DataKey( "volumeID" )
    private Integer volumeId;

    @Converter( TimestampConverter.class )
    @DataKey( "createdBefore" )
    private Date createdBefore;

    @Converter( TimestampConverter.class )
    @DataKey( "createdAfter" )
    private Date createdAfter;

    @Converter( TimestampConverter.class )
    @DataKey( "completedBefore" )
    private Date completedBefore;

    @Converter( TimestampConverter.class )
    @DataKey( "completedAfter" )
    private Date completedAfter;

    public KojiBuildQuery()
    {
    }

    public KojiBuildQuery( ProjectRef ga )
    {
        this.mavenRef = new KojiMavenRef( ga );
        this.type = "maven";
    }

    public KojiBuildQuery( KojiMavenRef gav )
    {
        this.mavenRef = gav;
        this.type = "maven";
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public KojiMavenRef getMavenRef()
    {
        return mavenRef;
    }

    public void setMavenRef( KojiMavenRef mavenRef )
    {
        this.mavenRef = mavenRef;
        this.type = "maven";
    }

    public Integer getPackageId()
    {
        return packageId;
    }

    public void setPackageId( Integer packageId )
    {
        this.packageId = packageId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    public Integer getTaskId()
    {
        return taskId;
    }

    public void setTaskId( Integer taskId )
    {
        this.taskId = taskId;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix( String prefix )
    {
        this.prefix = prefix;
    }

    public KojiBuildState getState()
    {
        return state;
    }

    public void setState( KojiBuildState state )
    {
        if ( state == null || state.getValue() == null )
        {
            this.state = null;
        }
        else
        {
            this.state = state;
        }
    }

    public Integer getVolumeId()
    {
        return volumeId;
    }

    public void setVolumeId( Integer volumeId )
    {
        this.volumeId = volumeId;
    }

    public Date getCreatedBefore()
    {
        return createdBefore;
    }

    public void setCreatedBefore( Date createdBefore )
    {
        this.createdBefore = createdBefore;
    }

    public Date getCreatedAfter()
    {
        return createdAfter;
    }

    public void setCreatedAfter( Date createdAfter )
    {
        this.createdAfter = createdAfter;
    }

    public Date getCompletedBefore()
    {
        return completedBefore;
    }

    public void setCompletedBefore( Date completedBefore )
    {
        this.completedBefore = completedBefore;
    }

    public Date getCompletedAfter()
    {
        return completedAfter;
    }

    public void setCompletedAfter( Date completedAfter )
    {
        this.completedAfter = completedAfter;
    }

    public KojiBuildQuery withType( String type )
    {
        this.type = type;
        return this;
    }

    public KojiBuildQuery withMavenRef( ProjectVersionRef gav )
    {
        this.mavenRef = new KojiMavenRef( gav );
        this.type = "maven";
        return this;
    }

    public KojiBuildQuery withMavenRef( KojiMavenRef mavenRef )
    {
        this.mavenRef = mavenRef;
        this.type = "maven";
        return this;
    }

    public KojiBuildQuery withPackageId( Integer packageId )
    {
        this.packageId = packageId;
        return this;
    }

    public KojiBuildQuery withUserId( Integer userId )
    {
        this.userId = userId;
        return this;
    }

    public KojiBuildQuery withTaskId( Integer taskId )
    {
        this.taskId = taskId;
        return this;
    }

    public KojiBuildQuery withPrefix( String prefix )
    {
        this.prefix = prefix;
        return this;
    }

    public KojiBuildQuery withState( KojiBuildState state )
    {
        setState( state );
        return this;
    }

    public KojiBuildQuery withVolumeId( Integer volumeId )
    {
        this.volumeId = volumeId;
        return this;
    }

    public KojiBuildQuery withCreatedBefore( Date createdBefore )
    {
        this.createdBefore = createdBefore;
        return this;
    }

    public KojiBuildQuery withCreatedAfter( Date createdAfter )
    {
        this.createdAfter = createdAfter;
        return this;
    }

    public KojiBuildQuery withCompletedBefore( Date completedBefore )
    {
        this.completedBefore = completedBefore;
        return this;
    }

    public KojiBuildQuery withCompletedAfter( Date completedAfter )
    {
        this.completedAfter = completedAfter;
        return this;
    }

    @Override
    public String toString()
    {
        return "KojiBuildQuery{type='" + type + "', mavenRef=" + mavenRef + ", packageId=" + packageId + ", userId="
                + userId + ", taskId=" + taskId + ", prefix='" + prefix + "', state=" + state + ", volumeId=" + volumeId
                + ", createdBefore=" + createdBefore + ", createdAfter=" + createdAfter + ", completedBefore="
                + completedBefore + ", completedAfter=" + completedAfter + "}";
    }
}
