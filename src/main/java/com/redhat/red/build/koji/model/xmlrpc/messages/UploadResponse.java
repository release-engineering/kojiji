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

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.Response;

/**
 * Created by jdcasey on 2/8/16.
 */
@Response
public class UploadResponse
{
    @DataKey( "fileverify" )
    private String checksumProtocol;

    @DataKey( "hexdigest" )
    private String checksum;

    @DataKey( "offset" )
    private long offset;

    @DataKey( "size" )
    private long size;

    public UploadResponse()
    {
    }

    public String getChecksumProtocol()
    {
        return checksumProtocol;
    }

    public void setChecksumProtocol( String checksumProtocol )
    {
        this.checksumProtocol = checksumProtocol;
    }

    public String getChecksum()
    {
        return checksum;
    }

    public void setChecksum( String checksum )
    {
        this.checksum = checksum;
    }

    public long getOffset()
    {
        return offset;
    }

    public void setOffset( long offset )
    {
        this.offset = offset;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize( long size )
    {
        this.size = size;
    }
}
