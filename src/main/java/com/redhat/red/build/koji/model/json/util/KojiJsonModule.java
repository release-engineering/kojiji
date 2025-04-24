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
package com.redhat.red.build.koji.model.json.util;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.redhat.red.build.koji.model.json.BuildExtraInfo;
import com.redhat.red.build.koji.model.json.BuildSource;
import com.redhat.red.build.koji.model.json.FileExtraInfo;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.atlas.maven.ident.ref.SimpleProjectVersionRef;

import java.util.Date;

/**
 * Created by jdcasey on 2/10/16.
 */
public class KojiJsonModule
        extends SimpleModule
{
    public KojiJsonModule(){
        addSerializer( ProjectVersionRef.class, new MavenGAVSerializer( ProjectVersionRef.class ) );
        addSerializer( SimpleProjectVersionRef.class, new MavenGAVSerializer( SimpleProjectVersionRef.class ) );
        addDeserializer( ProjectVersionRef.class, new MavenGAVDeserializer() );
        addSerializer( BuildSource.class, new BuildSourceSerializer() );
        addDeserializer( BuildSource.class, new BuildSourceDeserializer() );
        addSerializer( Date.class, new SecondsSinceEpochSerializer() );
        addDeserializer( Date.class, new SecondsSinceEpochDeserializer() );

        addSerializer( BuildExtraInfo.class, new BuildExtraInfoSerializer( BuildExtraInfo.class ) );
        addDeserializer( BuildExtraInfo.class, new BuildExtraInfoDeserializer() );

        addSerializer( FileExtraInfo.class, new FileExtraInfoSerializer( FileExtraInfo.class ) );
        addDeserializer( FileExtraInfo.class, new FileExtraInfoDeserializer() );

    }
}
