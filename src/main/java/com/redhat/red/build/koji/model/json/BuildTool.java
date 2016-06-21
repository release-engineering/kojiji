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

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jdcasey on 2/15/16.
 */
public class BuildTool
{
    @JsonProperty(NAME)
    private String name;

    @JsonProperty(VERSION)
    private String version;

    @JsonCreator
    public BuildTool( @JsonProperty(NAME) String name, @JsonProperty(VERSION) String version )
    {
        this.name = name;
        this.version = version;
    }

    public String getName()
    {
        return name;
    }

    public String getVersion()
    {
        return version;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildTool ) )
        {
            return false;
        }

        BuildTool that = (BuildTool) o;

        if ( getName() != null ? !getName().equals( that.getName() ) : that.getName() != null )
        {
            return false;
        }
        return !( getVersion() != null ? !getVersion().equals( that.getVersion() ) : that.getVersion() != null );

    }

    @Override
    public int hashCode()
    {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + ( getVersion() != null ? getVersion().hashCode() : 0 );
        return result;
    }
}
