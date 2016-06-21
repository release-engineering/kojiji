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
import com.redhat.red.build.koji.model.json.util.ExtraInfoHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ARCH;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.BUILDROOT_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.CHECKSUM;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.CHECKSUM_TYPE;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.EXTRA_INFO;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.FILENAME;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.FILESIZE;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.MD5_CHECKSUM_TYPE;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.TYPE;
import static com.redhat.red.build.koji.model.json.util.Verifications.checkString;

/**
 * Created by jdcasey on 2/15/16.
 */
public class BuildOutput
{
    @JsonProperty(BUILDROOT_ID)
    private int buildrootId;

    @JsonProperty(FILENAME)
    private String filename;

    @JsonProperty(FILESIZE)
    private long fileSize;

    @JsonProperty(ARCH)
    private String arch;

    @JsonProperty(CHECKSUM_TYPE)
    private String checksumType;

    @JsonProperty(CHECKSUM)
    private String checksum;

    @JsonProperty(TYPE)
    private String outputType;

    @JsonProperty(EXTRA_INFO)
    private Map<String, Object> extraInfo;

    private BuildOutput(){}

    @JsonCreator
    public BuildOutput( @JsonProperty( BUILDROOT_ID ) int buildrootId, @JsonProperty( FILENAME ) String filename,
                        @JsonProperty( FILESIZE ) long fileSize, @JsonProperty( ARCH ) String arch,
                        @JsonProperty( CHECKSUM_TYPE ) String checksumType, @JsonProperty( CHECKSUM ) String checksum,
                        @JsonProperty( TYPE ) String outputType, @JsonProperty(EXTRA_INFO) Map<String, Object> extraInfo )
    {
        this.buildrootId = buildrootId;
        this.filename = filename;
        this.fileSize = fileSize;
        this.arch = arch;
        this.checksumType = checksumType;
        this.checksum = checksum;
        this.outputType = outputType;
        this.extraInfo = extraInfo;
    }

    public int getBuildrootId()
    {
        return buildrootId;
    }

    public String getFilename()
    {
        return filename;
    }

    public long getFileSize()
    {
        return fileSize;
    }

    public String getArch()
    {
        return arch;
    }

    public String getChecksumType()
    {
        return checksumType;
    }

    public String getChecksum()
    {
        return checksum;
    }

    public String getOutputType()
    {
        return outputType;
    }

    public Map<String, Object> getExtraInfo()
    {
        return extraInfo;
    }

    public static final class Builder
            implements SectionBuilder<BuildOutput>, VerifiableBuilder<BuildOutput>
    {
        private BuildOutput target = new BuildOutput();

        private KojiImport.Builder parent;

        public Builder( int buildrootId, String filename )
        {
            target.buildrootId = buildrootId;
            target.filename = filename;
            target.arch = StandardArchitecture.noarch.name();
        }

        public Builder( int buildrootId, String filename, KojiImport.Builder parent )
        {
            this.parent = parent;
            target.buildrootId = buildrootId;
            target.filename = filename;
            target.arch = StandardArchitecture.noarch.name();
        }

        public KojiImport.Builder parent()
        {
            return parent;
        }

        public Builder withChecksum( String type, String checksum )
        {
            target.checksumType = type;
            target.checksum = checksum;

            return this;
        }

        public Builder withFileSize( long size )
        {
            target.fileSize = size;

            return this;
        }

        public Builder withArch( StandardArchitecture arch )
        {
            target.arch = arch.name();

            return this;
        }

        public Builder withArch( String arch )
        {
            target.arch = arch;

            return this;
        }

        public Builder withOutputType( StandardOutputType type )
        {
            target.outputType = type.name();

            return this;
        }

        public Builder withOutputType( String type )
        {
            target.outputType = type;

            return this;
        }

        public Builder withFile( File file )
                throws IOException
        {
            target.fileSize = file.length();
            target.checksumType = MD5_CHECKSUM_TYPE;
            target.checksum = DigestUtils.md5Hex( FileUtils.readFileToByteArray( file ) );

            return this;
        }

        public Builder withMavenInfoAndType( ProjectVersionRef gav )
        {
            target.outputType = StandardOutputType.maven.name();
            ExtraInfoHelper.addMavenInfo( gav, initExtraInfo() );

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

        @Override
        public BuildOutput build()
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
        public BuildOutput unsafeBuild()
        {
            return target;
        }

        @Override
        public void findMissingProperties( String propertyFormat, Set<String> missing )
        {
            checkString( target.outputType, missing, propertyFormat, TYPE );
            checkString( target.arch, missing, propertyFormat, ARCH );
            checkString( target.checksum, missing, propertyFormat, CHECKSUM );
            checkString( target.checksumType, missing, propertyFormat, CHECKSUM_TYPE );
            checkString( target.filename, missing, propertyFormat, FILENAME );
            checkString( target.outputType, missing, propertyFormat, TYPE );

            if ( target.buildrootId < 1 )
            {
                missing.add( String.format( propertyFormat, BUILDROOT_ID ) );
            }

            if ( target.fileSize < 1 )
            {
                missing.add( String.format( propertyFormat, FILESIZE ) );
            }

        }

        public String getFilename()
        {
            return target.getFilename();
        }
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildOutput ) )
        {
            return false;
        }

        BuildOutput that = (BuildOutput) o;

        if ( getBuildrootId() != that.getBuildrootId() )
        {
            return false;
        }
        if ( getFileSize() != that.getFileSize() )
        {
            return false;
        }
        if ( getFilename() != null ? !getFilename().equals( that.getFilename() ) : that.getFilename() != null )
        {
            return false;
        }
        if ( getArch() != null ? !getArch().equals( that.getArch() ) : that.getArch() != null )
        {
            return false;
        }
        if ( getChecksumType() != null ?
                !getChecksumType().equals( that.getChecksumType() ) :
                that.getChecksumType() != null )
        {
            return false;
        }
        if ( getChecksum() != null ? !getChecksum().equals( that.getChecksum() ) : that.getChecksum() != null )
        {
            return false;
        }
        if ( getOutputType() != null ? !getOutputType().equals( that.getOutputType() ) : that.getOutputType() != null )
        {
            return false;
        }
        return !( getExtraInfo() != null ?
                !getExtraInfo().equals( that.getExtraInfo() ) :
                that.getExtraInfo() != null );

    }

    @Override
    public int hashCode()
    {
        int result = getBuildrootId();
        result = 31 * result + ( getFilename() != null ? getFilename().hashCode() : 0 );
        result = 31 * result + (int) ( getFileSize() ^ ( getFileSize() >>> 32 ) );
        result = 31 * result + ( getArch() != null ? getArch().hashCode() : 0 );
        result = 31 * result + ( getChecksumType() != null ? getChecksumType().hashCode() : 0 );
        result = 31 * result + ( getChecksum() != null ? getChecksum().hashCode() : 0 );
        result = 31 * result + ( getOutputType() != null ? getOutputType().hashCode() : 0 );
        result = 31 * result + ( getExtraInfo() != null ? getExtraInfo().hashCode() : 0 );
        return result;
    }
}
