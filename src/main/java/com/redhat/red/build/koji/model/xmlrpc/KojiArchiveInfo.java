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
package com.redhat.red.build.koji.model.xmlrpc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.redhat.red.build.koji.model.converter.KojiBtypeConverter;
import com.redhat.red.build.koji.model.converter.KojiChecksumTypeConverter;
import com.redhat.red.build.koji.model.converter.StringListConverter;
import com.redhat.red.build.koji.model.util.ExternalizableUtils;

import org.commonjava.atlas.maven.ident.ref.ArtifactRef;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.atlas.maven.ident.ref.SimpleArtifactRef;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jdcasey on 8/5/16.
 */
@StructPart
public class KojiArchiveInfo
    implements Externalizable
{
    private static final int VERSION = 2;

    private static final long serialVersionUID = -2526545088406954594L;

    @DataKey( "id" )
    @JsonProperty( "id" )
    private Integer archiveId;

    @Converter( KojiBtypeConverter.class )
    @DataKey( "btype" )
    @JsonProperty( "btype" )
    private KojiBtype buildType;

    @DataKey( "btype_id" )
    @JsonProperty( "btype_id" )
    private Integer buildTypeId;

    @DataKey( "group_id" )
    @JsonProperty( "group_id" )
    private String groupId;

    @DataKey( "artifact_id" )
    @JsonProperty( "artifact_id" )
    private String artifactId;

    @DataKey( "version" )
    private String version;

    @JsonIgnore
    private transient String classifier;

    @DataKey( "relpath" )
    @JsonProperty( "relpath" )
    private String relPath;

    @DataKey( "platforms" )
    @Converter( StringListConverter.class )
    private List<String> platforms;

    @DataKey( "flags" )
    @Converter( StringListConverter.class )
    private List<String> flags;

    @DataKey ( "arch" )
    private String arch;

    @DataKey ( "rootid" )
    @JsonProperty( "rootid" )
    private Boolean rootId;

    @DataKey( "type_extensions" )
    @JsonProperty( "type_extensions" )
    private String typeExtensions;

    @DataKey( "filename" )
    private String filename;

    @DataKey( "build_id" )
    @JsonProperty( "build_id" )
    private Integer buildId;

    @DataKey( "type_name" )
    @JsonProperty( "type_name" )
    private String extension;

    @DataKey( "type_id" )
    @JsonProperty( "type_id" )
    private Integer typeId;

    @DataKey( "checksum" )
    private String checksum;

    @Converter( KojiChecksumTypeConverter.class )
    @DataKey( "checksum_type" )
    @JsonProperty( "checksum_type" )
    private KojiChecksumType checksumType;

    @DataKey( "type_description" )
    @JsonProperty( "type_description" )
    private String typeDescription;

    @DataKey( "metadata_only" )
    @JsonProperty( "metadata_only" )
    private Boolean metadataOnly;

    @DataKey( "buildroot_id" )
    @JsonProperty( "buildroot_id" )
    private Integer buildrootId;

    @DataKey( "size" )
    private Integer size;

    @DataKey( "extra" )
    Map<String, Object> extra;

    public KojiArchiveInfo()
    {

    }

    public KojiBtype getBuildType()
    {
        return buildType;
    }

    public void setBuildType( KojiBtype buildType )
    {
        this.buildType = buildType;
    }

    public Integer getBuildTypeId()
    {
        return buildTypeId;
    }

    public void setBuildTypeId( Integer buildTypeId )
    {
        this.buildTypeId = buildTypeId;
    }

    public Integer getArchiveId()
    {
        return archiveId;
    }

    public void setArchiveId( Integer archiveId )
    {
        this.archiveId = archiveId;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public synchronized String getClassifier()
    {
        if ( classifier == null && filename != null )
        {
            String fnameRegex = String.format( "%s-%s-(.+).%s", artifactId, version, getExtension() );
            Matcher matcher = Pattern.compile( fnameRegex ).matcher( filename );
            if ( matcher.matches() )
            {
                classifier = matcher.group( 1 );
            }
        }

        return classifier;
    }

    public ProjectVersionRef asGAV()
    {
        return new SimpleProjectVersionRef( groupId, artifactId, version );
    }

    public ArtifactRef asArtifact()
    {
        return new SimpleArtifactRef( groupId, artifactId, version, getExtension(), getClassifier() );
    }

    public void setClassifier( String classifier )
    {
        this.classifier = classifier;
    }

    public String getRelPath()
    {
        return relPath;
    }

    public void setRelPath( String relPath )
    {
        this.relPath = relPath;
    }

    public List<String> getPlatforms()
    {
        return platforms;
    }

    public void setPlatforms( List<String> platforms )
    {
        this.platforms = platforms;
    }

    public List<String> getFlags()
    {
        return flags;
    }

    public void setFlags( List<String> flags )
    {
        this.flags = flags;
    }

    public String getArch()
    {
        return arch;
    }

    public void setArch( String arch )
    {
        this.arch = arch;
    }

    public Boolean getRootId()
    {
        return rootId;
    }

    public void setRootId( Boolean rootId )
    {
        this.rootId = rootId;
    }

    public String getTypeExtensions()
    {
        return typeExtensions;
    }

    public void setTypeExtensions( String typeExtensions )
    {
        this.typeExtensions = typeExtensions;
    }

    public Integer getBuildId()
    {
        return buildId;
    }

    public void setBuildId( Integer buildId )
    {
        this.buildId = buildId;
    }

    @JsonIgnore
    public String getExtension()
    {
        return extension;
    }

    public void setExtension( String extension )
    {
        this.extension = extension;
    }

    @JsonIgnore
    public String getTypeName()
    {
        return extension;
    }

    public void setTypeName( String typeName )
    {
        this.extension = typeName;
    }

    public Integer getTypeId()
    {
        return typeId;
    }

    public void setTypeId( Integer typeId )
    {
        this.typeId = typeId;
    }

    public String getChecksum()
    {
        return checksum;
    }

    public void setChecksum( String checksum )
    {
        this.checksum = checksum;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename( String filename )
    {
        this.filename = filename;
    }

    public String getTypeDescription()
    {
        return typeDescription;
    }

    public void setTypeDescription( String typeDescription )
    {
        this.typeDescription = typeDescription;
    }

    public Boolean getMetadataOnly()
    {
        return metadataOnly;
    }

    public void setMetadataOnly( Boolean metadataOnly )
    {
        this.metadataOnly = metadataOnly;
    }

    public KojiChecksumType getChecksumType()
    {
        return checksumType;
    }

    public void setChecksumType( KojiChecksumType checksumType )
    {
        this.checksumType = checksumType;
    }

    public Integer getBuildrootId()
    {
        return buildrootId;
    }

    public void setBuildrootId( Integer buildrootId )
    {
        this.buildrootId = buildrootId;
    }

    public Map<String, Object> getExtra()
    {
        return extra;
    }

    public void setExtra( Map<String, Object> extra )
    {
        this.extra = extra;
    }

    public Integer getSize()
    {
        return size;
    }

    public void setSize( Integer size )
    {
        this.size = size;
    }

    public void addMavenArchiveInfo( KojiMavenArchiveInfo mavenArchiveInfo )
    {
        setArchiveId( mavenArchiveInfo.getArchiveId() );
        setArtifactId( mavenArchiveInfo.getArtifactId() );
        setGroupId( mavenArchiveInfo.getGroupId() );
        setVersion( mavenArchiveInfo.getVersion() );
    }

    public void addImageArchiveInfo( KojiImageArchiveInfo imageArchiveInfo )
    {
        setArchiveId( imageArchiveInfo.getArchiveId() );
        setArch( imageArchiveInfo.getArch() );
        setRootId( imageArchiveInfo.getRootId() );
    }

    public void addWinArchiveInfo( KojiWinArchiveInfo winArchiveInfo )
    {
        setArchiveId( winArchiveInfo.getArchiveId() );
        setRelPath( winArchiveInfo.getRelPath() );
        setPlatforms( winArchiveInfo.getPlatforms() );
        setFlags( winArchiveInfo.getFlags() );
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof KojiArchiveInfo ) )
        {
            return false;
        }

        KojiArchiveInfo that = (KojiArchiveInfo) o;

        return Objects.equals(getArchiveId(), that.getArchiveId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(getArchiveId());
    }

    @Override
    public String toString()
    {
        return "KojiArchiveInfo{" +
                "archiveId=" + archiveId +
                ", buildType='" + buildType + '\'' +
                ", buildTypeId='" + buildTypeId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", classifier='" + classifier + '\'' +
                ", relPath='" + relPath + '\'' +
                ", platforms=" + platforms +
                ", flags=" + flags +
                ", arch='" + arch + '\'' +
                ", rootId='" + rootId + '\'' +
                ", typeExtensions='" + typeExtensions + '\'' +
                ", filename='" + filename + '\'' +
                ", buildId=" + buildId +
                ", typeName (extension)='" + extension + '\'' +
                ", typeId=" + typeId +
                ", checksum='" + checksum + '\'' +
                ", checksumType=" + checksumType +
                ", typeDescription='" + typeDescription + '\'' +
                ", metadataOnly=" + metadataOnly +
                ", buildrootId=" + buildrootId +
                ", size=" + size +
                ", extra=" + extra +
                '}';
    }

    @Override
    public void writeExternal( ObjectOutput out )
            throws IOException
    {
        out.writeInt( VERSION );
        out.writeObject( archiveId );

        if ( VERSION == 2 )
        {
            out.writeObject( buildType );
        }
        else
        {
            ExternalizableUtils.writeUTF( out, buildType.name() );
        }

        out.writeObject( buildTypeId );
        ExternalizableUtils.writeUTF( out, groupId );
        ExternalizableUtils.writeUTF( out, artifactId );
        ExternalizableUtils.writeUTF( out, version );
        ExternalizableUtils.writeUTF( out, relPath );
        out.writeObject( platforms );
        out.writeObject( flags );
        ExternalizableUtils.writeUTF( out, arch );
        out.writeObject( rootId );
        ExternalizableUtils.writeUTF( out, typeExtensions );
        ExternalizableUtils.writeUTF( out, filename );
        out.writeObject( buildId );
        ExternalizableUtils.writeUTF( out, extension );
        out.writeObject( typeId );
        ExternalizableUtils.writeUTF( out, checksum );
        out.writeObject( checksumType );
        ExternalizableUtils.writeUTF( out, typeDescription );
        out.writeObject( metadataOnly );
        out.writeObject( buildrootId );
        out.writeObject( size );
        out.writeObject( extra );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public void readExternal( ObjectInput in )
            throws IOException, ClassNotFoundException
    {
        int version = in.readInt();

        if ( version <= 0 || version > 2 )
        {
            throw new IOException( "Invalid version: " + version );
        }

        this.archiveId = (Integer) in.readObject();

        if ( version == 2 )
        {
            this.buildType = (KojiBtype) in.readObject();
        }
        else
        {
            this.buildType = KojiBtype.fromString( ExternalizableUtils.readUTF( in ) );
        }

        this.buildTypeId = (Integer) in.readObject();
        this.groupId = ExternalizableUtils.readUTF( in );
        this.artifactId = ExternalizableUtils.readUTF( in );
        this.version = ExternalizableUtils.readUTF( in );
        this.relPath = ExternalizableUtils.readUTF( in );
        this.platforms = (List<String>) in.readObject();
        this.flags = (List<String>) in.readObject();
        this.arch = ExternalizableUtils.readUTF( in );
        this.rootId = (Boolean) in.readObject();
        this.typeExtensions = ExternalizableUtils.readUTF( in );
        this.filename = ExternalizableUtils.readUTF( in );
        this.buildId = (Integer) in.readObject();
        this.extension = ExternalizableUtils.readUTF( in );
        this.typeId = (Integer) in.readObject();
        this.checksum = ExternalizableUtils.readUTF( in );
        this.checksumType = (KojiChecksumType) in.readObject();
        this.typeDescription = ExternalizableUtils.readUTF( in );
        this.metadataOnly = (Boolean) in.readObject();
        this.buildrootId = (Integer) in.readObject();
        this.size = (Integer) in.readObject();
        this.extra = (Map<String, Object>) in.readObject();
    }
}
