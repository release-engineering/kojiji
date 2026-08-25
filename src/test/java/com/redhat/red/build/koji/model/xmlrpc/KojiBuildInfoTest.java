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

import com.redhat.red.build.koji.model.json.BuildExtraInfo;
import com.redhat.red.build.koji.model.json.ImageExtraInfo;
import com.redhat.red.build.koji.model.json.ImageIndexExtraInfo;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class KojiBuildInfoTest
{
    private static final String PULL_SPEC = "registry-proxy.engineering.redhat.com/rh-osbs/openshift-ose-ovn-kubernetes-rhel9@sha256:66a790bc5a63f647a6d36d241f0260a5f1b619ae009a7c73daf17bab6f1445d4";

    @Test
    public void testGetExtraInfoValid()
    {
        Map<String, Object> extra = Map.of( "image", Map.of( "index", Map.of( "pull", List.of( PULL_SPEC ) ) ) );
        KojiBuildInfo buildInfo = new KojiBuildInfo();
        buildInfo.setId( 123 );
        buildInfo.setExtra( extra );
        Optional<List<String>> pull = buildInfo.getExtraInfo()
                                               .map( BuildExtraInfo::getImageExtraInfo )
                                               .map( ImageExtraInfo::getIndex )
                                               .map( ImageIndexExtraInfo::getPull );
        assertThat( pull, equalTo( Optional.of( List.of( PULL_SPEC ) ) ) );
    }

    @Test
    public void testGetExtraInfoInvalid()
    {
        KojiBuildInfo buildInfo = new KojiBuildInfo();
        buildInfo.setId( 123 );
        buildInfo.setExtra( null );
        assertThat( buildInfo.getExtraInfo(), equalTo( Optional.empty() ) );
        buildInfo.setExtra( Map.of() );
        assertThat( buildInfo.getExtraInfo(), equalTo( Optional.empty() ) );
        buildInfo.setExtra( Map.of( "image", "extra" ) );
        assertThat( buildInfo.getExtraInfo(), equalTo( Optional.empty() ) );
    }
}
