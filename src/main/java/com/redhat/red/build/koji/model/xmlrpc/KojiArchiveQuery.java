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

import com.redhat.red.build.koji.model.util.ProjectVersionRefValueBinder;
import org.commonjava.maven.atlas.ident.ref.ArtifactRef;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.StructPart;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Created by jdcasey on 5/6/16.
 */
@StructPart
public class KojiArchiveQuery
    extends KojiQuery
{
    @DataKey( value = "type" )
    private String type = "maven";

    @DataKey( value="typeInfo" )
    @Converter( value = ProjectVersionRefValueBinder.class )
    private ProjectVersionRef gav;

    @DataKey( value="filename" )
    private String filename;

    public KojiArchiveQuery(){}

    public KojiArchiveQuery( ProjectVersionRef gav )
    {
        this.gav = gav;
        ArtifactRef ar;
        if ( gav instanceof ArtifactRef )
        {
            ar = (ArtifactRef) gav;
        }
        else
        {
            ar = gav.asJarArtifact();
        }

        StringBuilder sb = new StringBuilder();
        sb.append( ar.getArtifactId() ).append('-').append( ar.getVersionString() );
        String classifier = ( ar ).getClassifier();
        if ( isNotEmpty( classifier ) )
        {
            sb.append( '-' ).append( classifier );
        }
        sb.append( '.' ).append( ( ar ).getType() );

        filename = sb.toString();
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public ProjectVersionRef getGav()
    {
        return gav;
    }

    public void setGav( ProjectVersionRef gav )
    {
        this.gav = gav;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename( String filename )
    {
        this.filename = filename;
    }
}
