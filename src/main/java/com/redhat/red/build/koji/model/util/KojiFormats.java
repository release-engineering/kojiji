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
package com.redhat.red.build.koji.model.util;

import org.commonjava.atlas.maven.ident.ref.ProjectRef;

/**
 * Created by jdcasey on 9/14/16.
 */
public final class KojiFormats
{
    private KojiFormats(){}

    public static String toKojiName( ProjectRef ga )
    {
        return String.format( "%s-%s", ga.getGroupId(), ga.getArtifactId() );
    }

    public static String toKojiVersion( String version )
    {
        return version.replace( '-', '_' );
    }
}
