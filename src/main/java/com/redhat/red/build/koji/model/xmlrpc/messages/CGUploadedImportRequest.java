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

import com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.CG_IMPORT;

/**
 * Calls CGImport with the metadata.json file uploaded previously (and a reference to the file is included here).
 * Created by jdcasey on 2/19/16.
 */
@Request( method = CG_IMPORT )
public class CGUploadedImportRequest
{
    @DataIndex( 0 )
    private String metadataFilename = KojiXmlRpcConstants.METADATA_JSON_FILE;

    @DataIndex( 1 )
    private String dirname;

    public CGUploadedImportRequest( String dirname )
    {
        this.dirname = dirname;
    }

    public CGUploadedImportRequest()
    {
    }

    public String getMetadataFilename()
    {
        return metadataFilename;
    }

    public void setMetadataFilename( String metadataFilename )
    {
        this.metadataFilename = metadataFilename;
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
