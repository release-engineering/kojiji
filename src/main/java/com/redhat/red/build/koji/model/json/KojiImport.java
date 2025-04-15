/*
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.redhat.red.build.koji.model.xmlrpc.KojiNVR;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * See: <a href="http://fedoraproject.org/wiki/Koji/ContentGeneratorMetadata">http://fedoraproject.org/wiki/Koji/ContentGeneratorMetadata</a>
 * <p>
 * Created by jdcasey on 2/10/16.
 */
@StructPart
public class KojiImport
{
    @JsonProperty( METADATA_VERSION )
    @DataKey( METADATA_VERSION )
    private int metadataVersion;

    @JsonProperty( BUILD )
    @DataKey( BUILD )
    private BuildDescription build;

    @JsonProperty( BUILDROOTS )
    @DataKey( BUILDROOTS )
    private List<BuildRoot> buildRoots;

    @JsonProperty( OUTPUT )
    @DataKey( OUTPUT )
    private List<BuildOutput> outputs;

    public KojiImport( @JsonProperty( METADATA_VERSION ) int metadataVersion,
                       @JsonProperty( BUILD ) BuildDescription build,
                       @JsonProperty( BUILDROOTS ) List<BuildRoot> buildRoots,
                       @JsonProperty( OUTPUT ) List<BuildOutput> outputs )
    {
        this.metadataVersion = metadataVersion;
        this.build = build;
        this.buildRoots = buildRoots;
        this.outputs = outputs;
    }

    public KojiImport( BuildDescription build,
                       List<BuildRoot> buildRoots,
                       List<BuildOutput> outputs )
    {
        this.metadataVersion = DEFAULT_METADATA_VERSION;
        this.build = build;
        this.buildRoots = buildRoots;
        this.outputs = outputs;
    }

    public KojiImport()
    {
    }

    public void setMetadataVersion( int metadataVersion )
    {
        this.metadataVersion = metadataVersion;
    }

    public void setBuild( BuildDescription build )
    {
        this.build = build;
    }

    public void setBuildRoots( List<BuildRoot> buildRoots )
    {
        this.buildRoots = buildRoots;
    }

    public void setOutputs( List<BuildOutput> outputs )
    {
        this.outputs = outputs;
    }

    public int getMetadataVersion()
    {
        return metadataVersion;
    }

    public BuildDescription getBuild()
    {
        return build;
    }

    public List<BuildRoot> getBuildRoots()
    {
        return buildRoots;
    }

    public List<BuildOutput> getOutputs()
    {
        return outputs;
    }

    @JsonIgnore
    public KojiNVR getBuildNVR()
    {
        BuildDescription build = getBuild();
        return new KojiNVR( build.getName(), build.getVersion(), build.getRelease() );
    }

    public static final class Builder
            implements SectionBuilder<KojiImport>
    {
        private int metadataVersion = DEFAULT_METADATA_VERSION;

        private BuildDescription.Builder descBuilder;

        private Set<BuildRoot.Builder> rootBuilders = new HashSet<>();

        private Set<BuildOutput.Builder> outputBuilders = new HashSet<>();

        public Builder()
        {
        }

        public KojiImport build()
                throws VerificationException
        {
            Set<String> missing = new HashSet<>();
            if ( descBuilder == null )
            {
                missing.add( BUILD );
            }
            else
            {
                descBuilder.findMissingProperties( BUILD + ".%s", missing );
            }

            if ( rootBuilders.isEmpty() )
            {
                missing.add( BUILDROOTS );
            }
            else
            {
                for( BuildRoot.Builder rootBuilder: rootBuilders)
                {
                    rootBuilder.findMissingProperties( BUILDROOTS + "[" + rootBuilder.getId() + "].%s", missing );
                }
            }

            if ( outputBuilders.isEmpty() )
            {
                missing.add( OUTPUT );
            }
            else
            {
                for( BuildOutput.Builder outputBuilder: outputBuilders)
                {
                    outputBuilder.findMissingProperties( OUTPUT + "[" + outputBuilder.getFilename() + "].%s", missing );
                }
            }

            if ( missing.isEmpty() )
            {
                BuildDescription desc = descBuilder.build();
                List<BuildRoot> buildRoots =
                        rootBuilders.stream().map( ( builder ) -> builder.unsafeBuild() ).collect( Collectors.toList() );

                List<BuildOutput> buildOutputs =
                        outputBuilders.stream().map( ( builder ) -> builder.unsafeBuild() ).collect( Collectors.toList() );

                return new KojiImport( metadataVersion, desc, buildRoots, buildOutputs );
            }

            throw new VerificationException( missing );
        }

        public Builder withMetadataVersion( int metadataVersion )
        {
            this.metadataVersion = metadataVersion;

            return this;
        }

        public BuildDescription.Builder withNewBuildDescription( ProjectVersionRef gav )
        {
            this.descBuilder = new BuildDescription.Builder( gav, this );

            return descBuilder;
        }

        public BuildDescription.Builder withNewBuildDescription( String name, String version, String release )
        {
            this.descBuilder = new BuildDescription.Builder( name, version, release, this );

            return descBuilder;
        }

        public BuildRoot.Builder withNewBuildRoot( int id )
        {
            BuildRoot.Builder builder = new BuildRoot.Builder( id, this );
            rootBuilders.add( builder );

            return builder;
        }

        public BuildOutput.Builder withNewOutput( int buildrootId, String filename )
        {
            BuildOutput.Builder builder = new BuildOutput.Builder( buildrootId, filename, this );
            outputBuilders.add( builder );

            return builder;
        }
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof KojiImport ) )
        {
            return false;
        }

        KojiImport that = (KojiImport) o;

        if ( getMetadataVersion() != that.getMetadataVersion() )
        {
            return false;
        }
        if ( getBuild() != null ? !getBuild().equals( that.getBuild() ) : that.getBuild() != null )
        {
            return false;
        }
        if ( getBuildRoots() != null ? !getBuildRoots().equals( that.getBuildRoots() ) : that.getBuildRoots() != null )
        {
            return false;
        }
        return !( getOutputs() != null ? !getOutputs().equals( that.getOutputs() ) : that.getOutputs() != null );

    }

    @Override
    public int hashCode()
    {
        int result = getMetadataVersion();
        result = 31 * result + ( getBuild() != null ? getBuild().hashCode() : 0 );
        result = 31 * result + ( getBuildRoots() != null ? getBuildRoots().hashCode() : 0 );
        result = 31 * result + ( getOutputs() != null ? getOutputs().hashCode() : 0 );
        return result;
    }
}
