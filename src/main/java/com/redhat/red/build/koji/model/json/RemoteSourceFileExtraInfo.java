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
package com.redhat.red.build.koji.model.json;

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.CHECKSUM;

/**
 * Created by jbrazdil on 2021-04-08.
 */
@StructPart
public class RemoteSourceFileExtraInfo
{
    @JsonProperty( CHECKSUM )
    @DataKey( CHECKSUM )
    private String checksum;


    @JsonCreator
    public RemoteSourceFileExtraInfo(@JsonProperty( CHECKSUM ) String checksum)
    {
        this.checksum = checksum;
    }

    public RemoteSourceFileExtraInfo()
    {
    }

    public String getChecksum()
    {
        return checksum;
    }

    public void setChecksum( String checksum )
    {
        this.checksum = checksum;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof RemoteSourceFileExtraInfo) )
        {
            return false;
        }

        RemoteSourceFileExtraInfo that = (RemoteSourceFileExtraInfo) o;

        return getChecksum() != null ? getChecksum().equals( that.getChecksum() ) : that.getChecksum() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getChecksum() != null ? getChecksum().hashCode() : 0;
        return result;
    }

    @Override
    public String toString()
    {
        return "RemoteSourceFileExtraInfo{" + "checksum='" + checksum + "'}";
    }
}
