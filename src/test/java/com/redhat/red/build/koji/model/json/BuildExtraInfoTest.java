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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.red.build.koji.model.json.util.KojiObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

public class BuildExtraInfoTest
{
    private static final ObjectMapper MAPPER = new KojiObjectMapper();

    @Test
    public void testParsing() throws IOException {
        URL url = BuildExtraInfoTest.class.getResource( "/extra.json" );
        assertThat( url, notNullValue() );
        BuildExtraInfo extra = MAPPER.readValue( url, BuildExtraInfo.class );
        assertThat( extra, notNullValue() );
        assertThat( extra.getContainerKojiTaskId(), equalTo( 65888573 ) );
        ImageExtraInfo image = extra.getImageExtraInfo();
        assertThat( image, notNullValue() );
        assertThat( image.getHelp(), nullValue() );
        checkIndex( image.getIndex() );
        assertThat( image.getIsolated(), equalTo( false ) );
        assertThat( image.getMediaTypes(), hasItems( "application/vnd.docker.distribution.manifest.list.v2+json", "application/vnd.docker.distribution.manifest.v1+json", "application/vnd.docker.distribution.manifest.v2+json" ) );
        assertThat( image.getParentBuildId(), equalTo( 3388882 ) );
        checkParentImageBuilds( image.getParentImageBuilds() );
        assertThat( image.getParentImages(), hasItems( "openshift/golang-builder:v1.20.12-202410181045.g92d4921.el8", "openshift/ose-base:v4.14.0.20241113.044138" ) );
        assertThat( image.getRemoteSources(), hasItems( new RemoteSourcesExtraInfo( null, "cachito-gomod-with-deps","https://cachito/api/v1/requests/1755373" ) ) );
        assertThat( image.getYumRepourls(), hasItems( "https://pkgs/cgit/containers/ose-cluster-cloud-controller-manager-operator/plain/.oit/signed.repo?h=rhaos-4.14-rhel-8&id=8a4559dd22257ed70022838ef0df242622da92c5" ) );
        checkOsbsBuild( extra.getOsbsBuild() );
        assertThat( extra.getSubmitter(), equalTo( "osbs-brew-access" ) );
        checkTypeInfo(  extra.getTypeInfo() );
    }

    private static void checkTypeInfo( TypeInfoExtraInfo typeInfo )
    {
        assertThat( typeInfo, notNullValue() );
        List<RemoteSourcesExtraInfo> remoteSourcesExtraInfo = typeInfo.getRemoteSourcesExtraInfo();
        assertThat( remoteSourcesExtraInfo, hasItems( new RemoteSourcesExtraInfo( Arrays.asList( "remote-source-cachito-gomod-with-deps.json", "remote-source-cachito-gomod-with-deps.tar.gz", "remote-source-cachito-gomod-with-deps.env.json", "remote-source-cachito-gomod-with-deps.config.json" ), "cachito-gomod-with-deps", "https://cachito/api/v1/requests/1755373" ) ) );
        IcmExtraInfo icm = typeInfo.getIcmExtraInfo();
        assertThat( icm, notNullValue() );
        assertThat( icm.getArchives(), hasItems(  "icm-s390x.json", "icm-ppc64le.json", "icm-x86_64.json", "icm-aarch64.json" ) );
        assertThat( icm.getName(), equalTo( "icm" ) );
    }

    private static void checkOsbsBuild( OsbsBuildExtraInfo osbsBuild )
    {
        assertThat( osbsBuild, notNullValue() );
        assertThat( osbsBuild.getEngine(), equalTo( "podman" ) );
        assertThat( osbsBuild.getKind(), equalTo( "container_build" ) );
        assertThat( osbsBuild.getSubtypes(), hasSize( 0 ));
    }

    private static void checkParentImageBuilds( Map<String, ImageParentImageBuildExtraInfo> parentImageBuilds )
    {
        assertThat( parentImageBuilds, aMapWithSize( 2 ) );
        assertThat( parentImageBuilds, hasEntry( "registry/rh-osbs/openshift-golang-builder:v1.20.12-202410181045.g92d4921.el8", new ImageParentImageBuildExtraInfo( 3347950, "openshift-golang-builder-container-v1.20.12-202410181045.g92d4921.el8" )  ) );
        assertThat( parentImageBuilds, hasEntry( "registry/rh-osbs/openshift-ose-base:v4.14.0.20241113.044138", new ImageParentImageBuildExtraInfo( 3388882, "openshift-enterprise-base-container-v4.14.0-202411130434.p0.g03e5f40.assembly.stream.el8" )  ) );
    }

    private static void checkIndex( ImageIndexExtraInfo index )
    {
        assertThat( index.getDigests(), aMapWithSize( 1 ) );
        assertThat( index.getDigests(),  hasEntry(  "application/vnd.docker.distribution.manifest.list.v2+json", "sha256:4a0c89af8c0cbaafd369b505b7783eab15cd6f5af1c5ffcf0f424aacadc4dca9" ) );
        assertThat( index.getFloatingTags(), hasItems( "assembly.stream", "v4.14.0", "v4.14", "v4.14.0.20241113.044138" ) );
        assertThat( index.getPull(), hasItems( "registry/rh-osbs/openshift-ose-cluster-cloud-controller-manager-operator@sha256:4a0c89af8c0cbaafd369b505b7783eab15cd6f5af1c5ffcf0f424aacadc4dca9", "registry/rh-osbs/openshift-ose-cluster-cloud-controller-manager-operator:v4.14.0-202411130434.p0.ga0b9c0d.assembly.stream.el8" ) );
        assertThat( index.getTags(), hasItems( "v4.14.0-202411130434.p0.ga0b9c0d.assembly.stream.el8" ) );
        assertThat( index.getUniqueTags(), hasItems( "rhaos-4.14-rhel-8-containers-candidate-74394-20241113062440" ) );
    }
}
