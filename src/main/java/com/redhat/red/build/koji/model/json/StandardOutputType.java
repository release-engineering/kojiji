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

/**
 * Created by jdcasey on 2/16/16.
 */
public enum StandardOutputType
{
    log("log"),
    maven("maven"),
    remote_sources("remote-sources"),
    REMOTE_SOURCE_FILE("remote-source-file"),
    npm("npm"),
    rpm("rpm");
    private String name;

    StandardOutputType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
