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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redhat.red.build.koji.model.util.StringListValueBinder;

import org.commonjava.maven.atlas.ident.ref.ArtifactRef;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleArtifactRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.StructPart;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jdcasey on 8/5/16.
 */
@StructPart
public class KojiArchiveInfo
{
    @DataKey( "id" )
    private Integer archiveId;

    @DataKey( "group_id" )
    private String groupId;

    @DataKey( "artifact_id" )
    private String artifactId;

    @DataKey( "version" )
    private String version;

    private transient String classifier;

    @DataKey( "relpath" )
    @JsonProperty( "relpath")
    private String relPath;

    @DataKey( "platforms" )
    @Converter( StringListValueBinder.class )
    private List<String> platforms;

    @DataKey( "flags" )
    @Converter( StringListValueBinder.class )
    private List<String> flags;

    @DataKey ( "arch" )
    private String arch;

    @DataKey( "type_extensions" )
    private String typeExtensions;

    @DataKey( "filename" )
    private String filename;

    @DataKey( "build_id" )
    private Integer buildId;

    @DataKey( "type_name" )
    private String extension;

    @DataKey( "type_id" )
    private Integer typeId;

    @DataKey( "checksum" )
    private String checksum;

    @DataKey( "checksum_type" )
    private Integer checksumType;

    @DataKey( "type_description" )
    private String typeDescription;

    @DataKey( "metadata_only" )
    private Boolean metadataOnly;

    @DataKey( "buildroot_id" )
    private Integer buildrootId;

    @DataKey( "size" )
    private Integer size;

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

    public String getExtension()
    {
        return extension;
    }

    public void setExtension( String extension )
    {
        this.extension = extension;
    }

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

    public Integer getChecksumType()
    {
        return checksumType;
    }

    public void setChecksumType( Integer checksumType )
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

    public Integer getSize()
    {
        return size;
    }

    public void setSize( Integer size )
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "KojiArchiveInfo{" +
                "archiveId=" + archiveId +
                ", groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", classifier='" + classifier + '\'' +
                ", relPath='" + relPath + '\'' +
                ", platforms=" + platforms +
                ", flags=" + flags +
                ", arch='" + arch + '\'' +
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
                '}';
    }
}
