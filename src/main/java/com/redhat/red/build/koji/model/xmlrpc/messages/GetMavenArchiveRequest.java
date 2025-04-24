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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.GET_MAVEN_ARCHIVE;

@Request( method = GET_MAVEN_ARCHIVE )
public class GetMavenArchiveRequest
{
    @DataIndex( 0 )
    private Integer archiveId;

    public GetMavenArchiveRequest() {}

    public GetMavenArchiveRequest( Integer archiveId )
    {
        this.archiveId = archiveId;
    }

    public void setArchiveId( Integer archiveId )
    {
        this.archiveId = archiveId;
    }

    public Integer getArchiveId()
    {
        return archiveId;
    }
}
