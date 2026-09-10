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
package com.redhat.red.build.koji.model.xmlrpc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.redhat.red.build.koji.model.util.RWXUtil.getObjFromList;
import static com.redhat.red.build.koji.model.util.RWXUtil.getObjFromMap;
import static com.redhat.red.build.koji.model.util.RWXUtil.isBlankObj;
import static com.redhat.red.build.koji.model.util.RWXUtil.toBoolean;
import static com.redhat.red.build.koji.model.util.RWXUtil.toStringList;
import static com.redhat.red.build.koji.model.util.RWXUtil.toStringMap;

public class KojiMavenBuildRequest
                extends KojiBuildRequest
{
    private String patches;

    private String specfile;

    private List<String> goals;

    private List<String> profiles;

    private List<String> packages;

    private List<String> jvmOptions;

    private List<String> mavenOptions;

    private Map<String, String> properties;

    private Map<String, String> envs;

    private boolean scratch;

    private boolean skipTag;

    private Integer repoId;

    private List<KojiIdOrName> deps;

    public KojiMavenBuildRequest( List<Object> request )
    {
        super( request );

        Map<?, ?> map = getObjFromList( request, 2, Map.class );

        if ( map == null )
        {
            return;
        }

        patches = getObjFromMap( map, "patches", String.class );
        specfile = getObjFromMap( map, "specfile", String.class );
        goals = toStringList( map.get( "goals" ) );
        profiles = toStringList( map.get( "profiles" ) );
        packages = toStringList( map.get( "packages" ) );
        jvmOptions = toStringList( map.get( "jvm_options" ) );
        mavenOptions = toStringList( map.get( "maven_options" ) );
        properties = toStringMap( map.get( "properties" ) );
        envs = toStringMap( map.get( "envs" ) );
        scratch = toBoolean( map.get( "scratch" ) );
        skipTag = toBoolean( map.get( "skip_tag" ) );
        repoId = getObjFromMap( map, "repo_id", Integer.class );
        deps = toIdOrNameList( map.get( "deps" ) );
    }

    private static List<KojiIdOrName> toIdOrNameList( Object xmlrpcObj )
    {
        return !( xmlrpcObj instanceof List<?> ) ? null : ( (List<?>) xmlrpcObj ).stream().filter( dep -> !isBlankObj( dep ) ).map( KojiIdOrName::getFor ).collect( Collectors.toList() );
    }

    public String getScmUrl()
    {
        return getSource();
    }

    public void setScmUrl( String scmUrl )
    {
        setSource( scmUrl );
    }

    public String getPatches()
    {
        return patches;
    }

    public void setPatches( String patches )
    {
        this.patches = patches;
    }

    public String getSpecfile()
    {
        return specfile;
    }

    public void setSpecfile( String specfile )
    {
        this.specfile = specfile;
    }

    public List<String> getGoals()
    {
        if ( goals == null )
        {
            goals = Collections.emptyList();
        }

        return Collections.unmodifiableList( goals );
    }

    public void setGoals( List<String> goals )
    {
        this.goals = goals;
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

    public List<String> getPackages()
    {
        if ( packages == null )
        {
            packages = Collections.emptyList();
        }

        return Collections.unmodifiableList( packages );
    }

    public void setPackages( List<String> packages )
    {
        this.packages = packages;
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

    public List<String> getMavenOptions()
    {
        if ( mavenOptions == null )
        {
            mavenOptions = Collections.emptyList();
        }

        return Collections.unmodifiableList( mavenOptions );
    }

    public void setMavenOptions( List<String> mavenOptions )
    {
        this.mavenOptions = mavenOptions;
    }

    public Map<String, String> getProperties()
    {
        if ( properties == null )
        {
            properties = Collections.emptyMap();
        }

        return Collections.unmodifiableMap( properties );
    }

    public void setProperties( Map<String, String> properties )
    {
        this.properties = properties;
    }

    public Map<String, String> getEnvs()
    {
        if ( envs == null )
        {
            envs = Collections.emptyMap();
        }

        return Collections.unmodifiableMap( envs );
    }

    public void setEnvs( Map<String, String> envs )
    {
        this.envs = envs;
    }

    public boolean isScratch()
    {
        return scratch;
    }

    public void setScratch( boolean scratch )
    {
        this.scratch = scratch;
    }

    public boolean isSkipTag()
    {
        return skipTag;
    }

    public void setSkipTag( boolean skipTag )
    {
        this.skipTag = skipTag;
    }

    public Integer getRepoId()
    {
        return repoId;
    }

    public void setRepoId( Integer repoId )
    {
        this.repoId = repoId;
    }

    public List<KojiIdOrName> getDeps()
    {
        if ( deps == null )
        {
            deps = Collections.emptyList();
        }

        return Collections.unmodifiableList( deps );
    }

    public void setDeps( List<KojiIdOrName> deps )
    {
        this.deps = deps;
    }

    @Override
    public String toString()
    {
        return "KojiMavenBuildRequest{" +
                "source='" + source + '\'' +
                ", target=" + target +
                ", patches='" + patches + '\'' +
                ", specfile='" + specfile + '\'' +
                ", goals=" + goals +
                ", profiles=" + profiles +
                ", packages=" + packages +
                ", jvmOptions=" + jvmOptions +
                ", mavenOptions=" + mavenOptions +
                ", properties=" + properties +
                ", envs=" + envs +
                ", scratch=" + scratch +
                ", skipTag=" + skipTag +
                ", repoId=" + repoId +
                ", deps=" + deps +
                '}';
    }
}
