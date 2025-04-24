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

import com.redhat.red.build.koji.model.converter.TimestampConverter;
import com.redhat.red.build.koji.model.util.ExternalizableUtils;

import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

/**
 * Created by jdcasey on 8/8/16.
 */
@StructPart
public class KojiTaskInfo
    implements Externalizable
{
    private static final long serialVersionUID = -4033517639092179002L;

    private static final int VERSION = 1;

    @DataKey( "id" )
    private int taskId;

    @DataKey( "weight" )
    private double weight;

    @DataKey( "parent" )
    private int parentTaskId;

    @DataKey( "channel_id" )
    private int channelId;

    @DataKey( "start_time" )
    @Converter( TimestampConverter.class )
    private Date startTime;

    @DataKey( "label" )
    private String label;

    @DataKey( "priority" )
    private int priority;

    @DataKey( "completion_time" )
    @Converter( TimestampConverter.class )
    private Date completionTime;

    @DataKey( "state" )
    private int state;

    @DataKey( "create_time" )
    @Converter( TimestampConverter.class )
    private Date createTime;

    @DataKey( "owner_id" )
    private int ownerId;

    @DataKey( "host_id" )
    private int hostId;

    @DataKey( "method" )
    private String method;

    @DataKey( "arch" )
    private String arch;

    @DataKey( "request" )
    private List<Object> request;

    public KojiTaskInfo()
    {

    }

    public int getTaskId()
    {
        return taskId;
    }

    public void setTaskId( int taskId )
    {
        this.taskId = taskId;
    }

    public double getWeight()
    {
        return weight;
    }

    public void setWeight( double weight )
    {
        this.weight = weight;
    }

    public int getParentTaskId()
    {
        return parentTaskId;
    }

    public void setParentTaskId( int parentTaskId )
    {
        this.parentTaskId = parentTaskId;
    }

    public int getChannelId()
    {
        return channelId;
    }

    public void setChannelId( int channelId )
    {
        this.channelId = channelId;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime( Date startTime )
    {
        this.startTime = startTime;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel( String label )
    {
        this.label = label;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority( int priority )
    {
        this.priority = priority;
    }

    public Date getCompletionTime()
    {
        return completionTime;
    }

    public void setCompletionTime( Date completionTime )
    {
        this.completionTime = completionTime;
    }

    public int getState()
    {
        return state;
    }

    public void setState( int state )
    {
        this.state = state;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime( Date createTime )
    {
        this.createTime = createTime;
    }

    public int getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId( int ownerId )
    {
        this.ownerId = ownerId;
    }

    public int getHostId()
    {
        return hostId;
    }

    public void setHostId( int hostId )
    {
        this.hostId = hostId;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod( String method )
    {
        this.method = method;
    }

    public String getArch()
    {
        return arch;
    }

    public void setArch( String arch )
    {
        this.arch = arch;
    }

    public List<Object> getRequest()
    {
        return request;
    }

    public void setRequest( List<Object> request )
    {
        this.request = request;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }

        if ( !( o instanceof KojiTaskInfo ) )
        {
            return false;
        }

        KojiTaskInfo that = (KojiTaskInfo) o;

        return getTaskId() == that.getTaskId();

    }

    @Override
    public int hashCode()
    {
        return Integer.valueOf( getTaskId() ).hashCode();
    }

    @Override
    public void writeExternal( ObjectOutput out )
            throws IOException

    {
        out.writeInt( VERSION );
        out.writeInt( taskId );
        out.writeDouble( weight );
        out.writeInt( parentTaskId );
        out.writeInt( channelId );
        out.writeObject( startTime );
        ExternalizableUtils.writeUTF( out, label );
        out.writeInt( priority );
        out.writeObject( completionTime );
        out.writeInt( state );
        out.writeObject( createTime );
        out.writeInt( ownerId );
        out.writeInt( hostId );
        ExternalizableUtils.writeUTF( out, method );
        ExternalizableUtils.writeUTF( out, arch );
        out.writeObject( request );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public void readExternal( ObjectInput in )
            throws IOException, ClassNotFoundException
    {
        int version = in.readInt();

        if ( version != 1 )
        {
            throw new IOException( "Invalid version: " + version );
        }

        this.taskId = in.readInt();
        this.weight = in.readDouble();
        this.parentTaskId = in.readInt();
        this.channelId = in.readInt();
        this.startTime = (Date) in.readObject();
        this.label = ExternalizableUtils.readUTF( in );
        this.priority = in.readInt();
        this.completionTime = (Date) in.readObject();
        this.state = in.readInt();
        this.createTime = (Date) in.readObject();
        this.ownerId = in.readInt();
        this.hostId = in.readInt();
        this.method = ExternalizableUtils.readUTF( in );
        this.arch = ExternalizableUtils.readUTF( in );
        this.request = (List<Object>) in.readObject();
    }

    @Override
    public String toString() {
        return "KojiTaskInfo{" +
                "taskId=" + taskId +
                ", weight=" + weight +
                ", parentTaskId=" + parentTaskId +
                ", channelId=" + channelId +
                ", startTime=" + startTime +
                ", label='" + label + '\'' +
                ", priority=" + priority +
                ", completionTime=" + completionTime +
                ", state=" + state +
                ", createTime=" + createTime +
                ", ownerId=" + ownerId +
                ", hostId=" + hostId +
                ", method='" + method + '\'' +
                ", arch='" + arch + '\'' +
                ", request=" + request +
                '}';
    }
}
