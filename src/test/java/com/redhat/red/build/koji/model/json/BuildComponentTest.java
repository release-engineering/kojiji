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

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jdcasey on 2/17/16.
 */
public class BuildComponentTest
        extends AbstractJsonTest {

    @Test
    public void jsonRoundTripFile() throws VerificationException, JsonProcessingException, IOException {
        FileBuildComponent src = new FileBuildComponent.Builder("foo.txt")
                .withFileSize(42)
                .withChecksum("md5", "2a4b3cd54e6f")
                .build();

        String json = mapper.writeValueAsString(src);
        System.out.println(json);

        BuildComponent out = mapper.readValue(json, BuildComponent.class);

        assertThat(out, equalTo(src));
    }

    @Test
    public void jsonRoundTripRPM() throws VerificationException, JsonProcessingException, IOException {
        RPMBuildComponent src = new RPMBuildComponent.Builder("foo")
                .withVersion("1.0")
                .withRelease("1")
                .withArch(StandardArchitecture.noarch)
                .withSigmd5("1a2c3c4d5e6f")
                .build();

        String json = mapper.writeValueAsString(src);
        System.out.println(json);

        BuildComponent out = mapper.readValue(json, BuildComponent.class);

        assertThat(out, equalTo(src));
    }

}
