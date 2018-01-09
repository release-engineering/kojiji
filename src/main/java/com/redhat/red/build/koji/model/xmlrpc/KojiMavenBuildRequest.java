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
package com.redhat.red.build.koji.model.xmlrpc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.redhat.red.build.koji.model.util.RWXUtil.isBlankObj;

public class KojiMavenBuildRequest
                extends KojiBuildRequest
{
    private List<String> jvmOptions;

    private List<String> profiles;

    private List<String> deps;

    private Map<String, String> properties;

    public KojiMavenBuildRequest( List<Object> request )
    {
        super( request );

        if ( request == null || request.isEmpty() )
        {
            return;
        }

        if ( request.size() >= 3 )
        {
            if ( request.get( 2 ) instanceof Map<?, ?> )
            {
                Map<?, ?> map = (Map) request.get( 2 );
                Object obj = map.get( "jvm_options" );
                if ( !isBlankObj( obj ) )
                {
                    jvmOptions = (List) obj;
                }
                obj = map.get( "profiles" );
                if ( !isBlankObj( obj ) )
                {
                    profiles = (List) obj;
                }
                obj = map.get( "deps" );
                if ( !isBlankObj( obj ) )
                {
                    deps = (List) obj;
                }
                obj = map.get( "properties" );
                if ( !isBlankObj( obj ) )
                {
                    properties = ( (Map<String, Object>) obj ).entrySet()
                                                              .stream()
                                                              .filter( et -> !isBlankObj( et.getValue() ) )
                                                              .collect( Collectors.toMap( et -> et.getKey(),
                                                                                          et -> (String) et.getValue() ) );
                }
            }
        }
    }

    public String getScmUrl()
    {
        return getSource();
    }

    public void setScmUrl( String scmUrl )
    {
        setSource( scmUrl );
    }

    public List<String> getJvmOptions()
    {
        if ( jvmOptions == null )
        {
            jvmOptions = Collections.emptyList();
        }

        return Collections.unmodifiableList( jvmOptions );
    }

    public void setJvmOptions( List<String> options )
    {
        this.jvmOptions = options;
    }

    public List<String> getProfiles()
    {
        if ( profiles == null )
        {
            profiles = Collections.emptyList();
        }

        return Collections.unmodifiableList( profiles );
    }

    public void setProfiles( List<String> profiles )
    {
        this.profiles = profiles;
    }

    public List<String> getDeps()
    {
        if ( deps == null )
        {
            deps = Collections.emptyList();
        }

        return Collections.unmodifiableList( deps );
    }

    public void setDeps( List<String> deps )
    {
        this.deps = deps;
    }

    public Map<String, String> getProperties()
    {
        if ( properties == null )
        {
            properties = Collections.emptyMap();
        }

        return Collections.unmodifiableMap( properties );
    }

    public void setProperties( Map<String,String> properties )
    {
        this.properties = properties;
    }

    @Override
    public String toString()
    {
        return "KojiMavenBuildRequest{source=" + source + ", target=" + target + ", options=" + jvmOptions + "}";
    }
}
