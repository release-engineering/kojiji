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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.json.KojiImport;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.CG_IMPORT;

/**
 * Calls CGImport with the metadata inlined as a struct in param 0.
 *
 * Created by jdcasey on 2/19/16.
 */
@Request( method = CG_IMPORT )
public class CGInlinedImportRequest
{
    @DataIndex( 0 )
    private KojiImport importMetadata;

    @DataIndex( 1 )
    private String dirname;

    public CGInlinedImportRequest( KojiImport importMetadata, String dirname )
    {
        this.importMetadata = importMetadata;
        this.dirname = dirname;
    }

    public CGInlinedImportRequest()
    {
    }

    public KojiImport getImportMetadata()
    {
        return importMetadata;
    }

    public void setImportMetadata( KojiImport importMetadata )
    {
        this.importMetadata = importMetadata;
    }

    public String getDirname()
    {
        return dirname;
    }

    public void setDirname( String dirname )
    {
        this.dirname = dirname;
    }
}
