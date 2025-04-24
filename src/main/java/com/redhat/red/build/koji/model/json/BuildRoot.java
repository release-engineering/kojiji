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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.COMPONENTS;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.CONTAINER;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.CONTENT_GENERATOR;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.EXTRA_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.HOST;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.OUTPUT;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.TOOLS;
import static com.redhat.red.build.koji.model.json.util.Verifications.checkNull;

/**
 * Created by jdcasey on 2/10/16.
 */
@StructPart
public class BuildRoot
{
    @JsonProperty( ID )
    @DataKey( ID )
    private int id;

    @JsonProperty( HOST )
    @DataKey( HOST )
    private BuildHost buildHost;

    @JsonProperty( CONTENT_GENERATOR )
    @DataKey( CONTENT_GENERATOR )
    private BuildTool contentGenerator;

    @JsonProperty( CONTAINER )
    @DataKey( CONTAINER )
    private BuildContainer buildContainer;

    @JsonProperty( TOOLS )
    @DataKey( TOOLS )
    private List<BuildTool> buildTools;

    @JsonProperty( COMPONENTS )
    @DataKey( COMPONENTS )
    private List<BuildComponent> components;

    @JsonProperty( EXTRA_INFO )
    // FIXME: XML-RPC binding library doesn't handle free-form nesting of maps, so this is left out of xml-rpc inlining.
    private Map<String, Object> extraInfo;

    public BuildRoot(){}

    @JsonCreator
    public BuildRoot( @JsonProperty( ID ) int id, @JsonProperty( HOST ) BuildHost buildHost,
                      @JsonProperty( CONTENT_GENERATOR ) BuildTool contentGenerator,
                      @JsonProperty( CONTAINER ) BuildContainer buildContainer )
    {
        this.id = id;
        this.buildHost = buildHost;
        this.contentGenerator = contentGenerator;
        this.buildContainer = buildContainer;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public void setBuildHost( BuildHost buildHost )
    {
        this.buildHost = buildHost;
    }

    public void setContentGenerator( BuildTool contentGenerator )
    {
        this.contentGenerator = contentGenerator;
    }

    public void setBuildContainer( BuildContainer buildContainer )
    {
        this.buildContainer = buildContainer;
    }

    public void setBuildTools( List<BuildTool> buildTools )
    {
        this.buildTools = buildTools;
    }

    public void setComponents( List<BuildComponent> components )
    {
        this.components = components;
    }

    public int getId()
    {
        return id;
    }

    public BuildHost getBuildHost()
    {
        return buildHost;
    }

    public BuildTool getContentGenerator()
    {
        return contentGenerator;
    }

    public BuildContainer getBuildContainer()
    {
        return buildContainer;
    }

    public List<BuildTool> getBuildTools()
    {
        return buildTools;
    }

    public Map<String, Object> getExtraInfo()
    {
        return extraInfo;
    }

    public List<BuildComponent> getComponents() {
        return components;
    }

    public void setExtraInfo( Map<String, Object> extraInfo )
    {
        this.extraInfo = extraInfo;
    }

    public static final class Builder
            implements SectionBuilder<BuildRoot>, VerifiableBuilder<BuildRoot>
    {
        private BuildRoot target = new BuildRoot();

        private KojiImport.Builder parent;

        private Set<BuildComponent.Builder<? extends BuildComponent>> componentBuilders = new HashSet<>();

        public Builder( int id )
        {
            target.id = id;
        }

        public Builder( int id, KojiImport.Builder parent )
        {
            this.parent = parent;
            target.id = id;
        }

        public KojiImport.Builder parent()
        {
            return parent;
        }

        public Builder withContentGenerator( String name, String version )
        {
            BuildTool cg = new BuildTool( name, version );
            target.contentGenerator = cg;

            return this;
        }

        public Builder withTools( Collection<BuildTool> tools )
        {
            initTools().addAll( tools );

            return this;
        }

        public Builder withTool( BuildTool tool )
        {
            initTools().add( tool );

            return this;
        }

        public Builder withTool( String name, String version )
        {
            BuildTool tool = new BuildTool( name, version );
            initTools().add( tool );

            return this;
        }

        private List<BuildTool> initTools()
        {
            if ( target.buildTools == null )
            {
                target.buildTools = new ArrayList<>();
            }

            return target.buildTools;
        }

        public Builder withExtraInfo( String key, Object value )
        {
            initExtraInfo().put( key, value );
            return this;
        }

        private Map<String, Object> initExtraInfo()
        {
            if ( target.extraInfo == null )
            {
                target.extraInfo = new HashMap<>();
            }

            return target.extraInfo;
        }

        public Builder withContentGenerator( BuildTool contentGenerator )
        {
            target.contentGenerator = contentGenerator;

            return this;
        }

        public Builder withContainer( String type, StandardArchitecture arch )
        {
            BuildContainer container = new BuildContainer( type, arch.name() );
            target.buildContainer = container;

            return this;
        }

        public Builder withContainer( String type, String arch )
        {
            BuildContainer container = new BuildContainer( type, arch );
            target.buildContainer = container;

            return this;
        }

        public Builder withContainer( BuildContainer container )
        {
            target.buildContainer = container;

            return this;
        }

        public Builder withHost( String os, StandardArchitecture arch )
        {
            BuildHost host = new BuildHost( os, arch.name() );
            target.buildHost = host;

            return this;
        }

        public Builder withHost( String os, String arch )
        {
            BuildHost host = new BuildHost( os, arch );
            target.buildHost = host;

            return this;
        }

        public Builder withHost( BuildHost host )
        {
            target.buildHost = host;

            return this;
        }

        public RPMBuildComponent.Builder withRPMComponent( String name )
        {
            RPMBuildComponent.Builder componentBuilder = new RPMBuildComponent.Builder( name, this );
            componentBuilders.add( componentBuilder );
            return componentBuilder;
        }

        public FileBuildComponent.Builder withFileComponent( String filename )
        {
            FileBuildComponent.Builder componentBuilder = new FileBuildComponent.Builder( filename, this );
            componentBuilders.add( componentBuilder );
            return componentBuilder;
        }

        @Override
        public BuildRoot build()
                throws VerificationException
        {
            Set<String> missing = new HashSet<>();
            findMissingProperties( "%s", missing );
            if ( !missing.isEmpty() )
            {
                throw new VerificationException( missing );
            }

            return unsafeBuild();
        }

        @Override
        public BuildRoot unsafeBuild()
        {
            target.components = componentBuilders.stream()
                    .map( b -> (BuildComponent) b.unsafeBuild() )
                    .collect( Collectors.toList() );
            return target;
        }

        @Override
        public void findMissingProperties( String propertyFormat, Set<String> missingProperties )
        {
            checkNull( target.buildContainer, missingProperties, propertyFormat, CONTAINER );
            checkNull( target.buildHost, missingProperties, propertyFormat, HOST );
            checkNull( target.contentGenerator, missingProperties, propertyFormat, CONTENT_GENERATOR );

            if ( target.id < 1 )
            {
                missingProperties.add( String.format( propertyFormat, ID ) );
            }

            for ( BuildComponent.Builder componentBuilder : componentBuilders )
            {
                componentBuilder.findMissingProperties(  OUTPUT + "[" + componentBuilder.getIdentifier() + "].%s", missingProperties );
            }
        }

        public int getId()
        {
            return target.getId();
        }
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildRoot ) )
        {
            return false;
        }

        BuildRoot buildRoot = (BuildRoot) o;

        if ( getId() != buildRoot.getId() )
        {
            return false;
        }
        if ( getBuildHost() != null ?
                !getBuildHost().equals( buildRoot.getBuildHost() ) :
                buildRoot.getBuildHost() != null )
        {
            return false;
        }
        if ( getContentGenerator() != null ?
                !getContentGenerator().equals( buildRoot.getContentGenerator() ) :
                buildRoot.getContentGenerator() != null )
        {
            return false;
        }
        if ( getBuildContainer() != null ?
                !getBuildContainer().equals( buildRoot.getBuildContainer() ) :
                buildRoot.getBuildContainer() != null )
        {
            return false;
        }
        if ( getBuildTools() != null ?
                !getBuildTools().equals( buildRoot.getBuildTools() ) :
                buildRoot.getBuildTools() != null )
        {
            return false;
        }
        return !( getExtraInfo() != null ?
                !getExtraInfo().equals( buildRoot.getExtraInfo() ) :
                buildRoot.getExtraInfo() != null );

    }

    @Override
    public int hashCode()
    {
        int result = getId();
        result = 31 * result + ( getBuildHost() != null ? getBuildHost().hashCode() : 0 );
        result = 31 * result + ( getContentGenerator() != null ? getContentGenerator().hashCode() : 0 );
        result = 31 * result + ( getBuildContainer() != null ? getBuildContainer().hashCode() : 0 );
        result = 31 * result + ( getBuildTools() != null ? getBuildTools().hashCode() : 0 );
        result = 31 * result + ( getExtraInfo() != null ? getExtraInfo().hashCode() : 0 );
        return result;
    }
}
