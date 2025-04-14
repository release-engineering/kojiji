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
package com.redhat.red.build.koji.model.xmlrpc;

import java.util.List;

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

@StructPart
public class KojiGetRpmHeadersParams
                extends KojiParams
{
    @DataKey( "rpmID" )
    private Integer rpmId;

    @DataKey( "taskID" )
    private Integer taskId;

    @DataKey( "filepath" )
    private String filepath;

    @DataKey( "headers" )
    private List<String> headers;

    public KojiGetRpmHeadersParams()
    {

    }

    public Integer getRpmId()
    {
        return rpmId;
    }

    public void setRpmId( Integer rpmId )
    {
        this.rpmId = rpmId;
    }

    public Integer getTaskId()
    {
        return taskId;
    }

    public void setTaskId( Integer taskId )
    {
        this.taskId = taskId;
    }

    public String getFilepath()
    {
        return filepath;
    }

    public void setFilepath( String filepath )
    {
        this.filepath = filepath;
    }

    public List<String> getHeaders()
    {
        return headers;
    }

    public void setHeaders( List<String> headers )
    {
        this.headers = headers;
    }

    public KojiGetRpmHeadersParams withRpmId( Integer rpmId )
    {
        this.rpmId = rpmId;
        return this;
    }

    public KojiGetRpmHeadersParams withTaskId( Integer taskId )
    {
        this.taskId = taskId;
        return this;
    }

    public KojiGetRpmHeadersParams withFilepath( String filepath )
    {
        this.filepath = filepath;
        return this;
    }

    public KojiGetRpmHeadersParams withHeaders( List<String> headers )
    {
        this.headers = headers;
        return this;
    }

    @Override
    public String toString()
    {
        return "KojiGetRpmHeadersParams{" +
                "rpmId=" + rpmId +
                "taskId=" + taskId +
                "filepath='" + filepath + '\'' +
                ", headers=" + headers +
                ", " + super.toString() +
                "}";
    }
}
