/*
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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.GET_TASK_INFO;

/**
 * Created by jdcasey on 1/29/16.
 */
@Request( method = GET_TASK_INFO )
public class GetTaskRequest
{
    @DataIndex( 0 )
    private int taskId;

    @DataIndex( 1 )
    private boolean request;

    public GetTaskRequest()
    {
    }

    public GetTaskRequest( int taskId )
    {
        this.taskId = taskId;
    }

    public GetTaskRequest( int taskId, boolean request )
    {
        this.taskId = taskId;
        this.request = request;
    }

    public void setTaskId( int taskId )
    {
        this.taskId = taskId;
    }

    public boolean isRequest()
    {
        return request;
    }

    public void setRequest( boolean request )
    {
        this.request = request;
    }

    public int getTaskId()
    {
        return taskId;
    }

    public boolean getRequest()
    {
        return request;
    }
}
