package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.maven.atlas.ident.ref.ArtifactRef;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleArtifactRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.StructPart;

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

    @DataKey( "type_extensions" )
    private String extension;

    @DataKey( "filename" )
    private String filename;

    @DataKey( "build_id" )
    private Integer buildId;

    @DataKey( "type_name" )
    private String typeName;

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
            String fnameRegex = String.format( "%s-%s-(.+).%s", artifactId, version, extension );
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
        return new SimpleArtifactRef( groupId, artifactId, version, extension, getClassifier(), false );
    }

    public void setClassifier( String classifier )
    {
        this.classifier = classifier;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension( String extension )
    {
        this.extension = extension;
    }

    public Integer getBuildId()
    {
        return buildId;
    }

    public void setBuildId( Integer buildId )
    {
        this.buildId = buildId;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName( String typeName )
    {
        this.typeName = typeName;
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
}
