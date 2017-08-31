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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.GET_ARCHIVE_TYPE;

@Request( method = GET_ARCHIVE_TYPE )
public class GetArchiveTypeRequest
{
    @DataIndex( 0 )
    private String filename;

    @DataIndex( 1 )
    private String typeName;	

    @DataIndex( 2 )
    private int typeId;	

    public GetArchiveTypeRequest()
    {
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename( String filename )
    {
        this.filename = filename;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName( String typeName )
    {
        this.typeName = typeName;
    }

    public int getTypeId()
    {
        return typeId;
    }

    public void setTypeId( int typeId )
    {
        this.typeId = typeId;
    }
	
    public GetArchiveTypeRequest withFilename( String filename )
    {
        this.filename = filename;
        return this;
    }
	
    public GetArchiveTypeRequest withTypeName( String typeName )
    {
        this.typeName = typeName;
        return this;
    }

    public GetArchiveTypeRequest withTypeId( int typeId )
    {
        this.typeId = typeId;
	return this;
    }

    @Override
    public String toString()
    {
        return "GetArchiveTypeRequest{" +
                "filename='" + filename + '\'' +
                ", typeName='" + typeName + '\'' +
                ", typeId=" + typeId +
                '}';
    }}
