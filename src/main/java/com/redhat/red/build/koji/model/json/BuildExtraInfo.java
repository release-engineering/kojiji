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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.BUILD_SYSTEM;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.EXTERNAL_BUILD_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.EXTERNAL_BUILD_URL;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.IMPORT_INITIATOR;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.MAVEN_INFO;

/**
 * Created by jdcasey on 9/15/16.
 */
@StructPart
public class BuildExtraInfo
{
    @JsonProperty( MAVEN_INFO )
    @DataKey( MAVEN_INFO )
    private MavenExtraInfo mavenExtraInfo;

    @JsonProperty( EXTERNAL_BUILD_ID )
    @DataKey( EXTERNAL_BUILD_ID )
    private String externalBuildId;

    @JsonProperty( BUILD_SYSTEM )
    @DataKey( BUILD_SYSTEM )
    private String buildSystem;

    @JsonProperty( EXTERNAL_BUILD_URL )
    @DataKey( EXTERNAL_BUILD_URL )
    private String externalBuildUrl;

    @JsonProperty( IMPORT_INITIATOR )
    @DataKey( IMPORT_INITIATOR )
    private String importInitiator;

    public BuildExtraInfo(){}

    public BuildExtraInfo( MavenExtraInfo mavenExtraInfo )
    {
        this.mavenExtraInfo = mavenExtraInfo;
    }

    public BuildExtraInfo( ProjectVersionRef gav )
    {
        this.mavenExtraInfo = new MavenExtraInfo( gav );
    }

    public MavenExtraInfo getMavenExtraInfo()
    {
        return mavenExtraInfo;
    }

    public void setMavenExtraInfo( MavenExtraInfo mavenExtraInfo )
    {
        this.mavenExtraInfo = mavenExtraInfo;
    }

    public String getExternalBuildId()
    {
        return externalBuildId;
    }

    public void setExternalBuildId( String externalBuildId )
    {
        this.externalBuildId = externalBuildId;
    }

    public String getBuildSystem() {
        return buildSystem;
    }

    public void setBuildSystem(String buildSystem) {
        this.buildSystem = buildSystem;
    }

    public String getExternalBuildUrl() {
        return externalBuildUrl;
    }

    public void setExternalBuildUrl(String externalBuildUrl) {
        this.externalBuildUrl = externalBuildUrl;
    }

    public String getImportInitiator()
    {
        return importInitiator;
    }

    public void setImportInitiator( String importInitiator )
    {
        this.importInitiator = importInitiator;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildExtraInfo ) )
        {
            return false;
        }

        BuildExtraInfo that = (BuildExtraInfo) o;

        return getMavenExtraInfo() != null ?
                getMavenExtraInfo().equals( that.getMavenExtraInfo() ) :
                that.getMavenExtraInfo() == null;

    }

    @Override
    public int hashCode()
    {
        return getMavenExtraInfo() != null ? getMavenExtraInfo().hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return "BuildMavenInfo{" +
                "mavenExtraInfo=" + mavenExtraInfo +
                '}';
    }
}
