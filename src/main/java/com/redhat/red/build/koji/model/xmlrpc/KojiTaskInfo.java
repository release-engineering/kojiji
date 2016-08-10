package com.redhat.red.build.koji.model.xmlrpc;

import com.redhat.red.build.koji.model.util.TimestampValueBinder;
import org.commonjava.rwx.binding.anno.Contains;
import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.StructPart;

import java.util.Date;
import java.util.List;

/**
 * Created by jdcasey on 8/8/16.
 */
@StructPart
public class KojiTaskInfo
{
    @DataKey( "id" )
    private int taskId;

    @DataKey( "weight" )
    private double weight;

    @DataKey( "parent" )
    private int parentTaskId;

    @DataKey( "channel_id" )
    private int channelId;

    @DataKey( "start_time" )
    @Converter( TimestampValueBinder.class )
    private Date startTime;

    @DataKey( "label" )
    private String label;

    @DataKey( "priority" )
    private int priority;

    @DataKey( "completion_time" )
    private Date completionTime;

    @DataKey( "state" )
    private int state;

    @DataKey( "create_time" )
    private Date createTime;

    @DataKey( "owner_id" )
    private int ownerId;

    @DataKey( "host_id" )
    private int hostId;

    @DataKey( "method" )
    private String method;

    @DataKey( "arch" )
    private String arch;

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
}
