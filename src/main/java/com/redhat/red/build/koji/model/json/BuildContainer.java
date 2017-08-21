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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ARCH;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.TYPE;

/**
 * Created by jdcasey on 2/15/16.
 */
@StructPart
public class BuildContainer
{
    @JsonProperty( TYPE )
    @DataKey( TYPE )
    private String type;

    @JsonProperty( ARCH )
    @DataKey( ARCH )
    private String arch;

    @JsonCreator
    public BuildContainer( @JsonProperty( TYPE ) String type, @JsonProperty( ARCH ) String arch )
    {
        this.type = type;
        this.arch = arch;
    }

    public BuildContainer()
    {
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public void setArch( String arch )
    {
        this.arch = arch;
    }

    public String getArch()
    {
        return arch;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildContainer ) )
        {
            return false;
        }

        BuildContainer that = (BuildContainer) o;

        if ( getType() != null ? !getType().equals( that.getType() ) : that.getType() != null )
        {
            return false;
        }
        return !( getArch() != null ? !getArch().equals( that.getArch() ) : that.getArch() != null );

    }

    @Override
    public int hashCode()
    {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + ( getArch() != null ? getArch().hashCode() : 0 );
        return result;
    }
}
