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

import com.redhat.red.build.koji.KojiClientException;
import org.commonjava.atlas.maven.ident.util.JoinString;

import java.util.Set;

/**
 * Created by jdcasey on 2/16/16.
 */
public class VerificationException
        extends KojiClientException
{
    private Set<String> missingProperties;

    public VerificationException( Set<String> missing )
    {
        super( "The following properties were missing:\n  -%s", new JoinString( "\n  -", missing ) );
        this.missingProperties = missing;
    }

    public Set<String> getMissingProperties()
    {
        return missingProperties;
    }
}
