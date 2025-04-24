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
package com.redhat.red.build.koji.model.json;

import com.redhat.red.build.koji.model.converter.KojiBuildSourceConverter;
import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.REVISION;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.URL;

/**
 * Created by jdcasey on 2/10/16.
 */
@StructPart
@Converter( KojiBuildSourceConverter.class )
public class BuildSource
{
    @DataKey( URL )
    private String url;

    @DataKey( REVISION )
    private String revision;

    public BuildSource( String url )
    {
        this.url = url;
    }

    public BuildSource()
    {
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getRevision()
    {
        return revision;
    }

    public void setRevision( String revision )
    {
        this.revision = revision;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildSource ) )
        {
            return false;
        }

        BuildSource that = (BuildSource) o;

        if ( getUrl() != null ? !getUrl().equals( that.getUrl() ) : that.getUrl() != null )
        {
            return false;
        }
        return !( getRevision() != null ? !getRevision().equals( that.getRevision() ) : that.getRevision() != null );

    }

    @Override
    public int hashCode()
    {
        int result = getUrl() != null ? getUrl().hashCode() : 0;
        result = 31 * result + ( getRevision() != null ? getRevision().hashCode() : 0 );
        return result;
    }
}
