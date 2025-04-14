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
package com.redhat.red.build.koji.model.xmlrpc;

import com.redhat.red.build.koji.model.ImportFile;
import com.redhat.red.build.koji.model.xmlrpc.messages.UploadResponse;

/**
 * Created by jdcasey on 2/19/16.
 */
public final class KojiUploaderResult
{
    private String uploadFilePath;

    private long uploadSize;

    private Exception error;

    private boolean temporaryError;

    private UploadResponse response;

    public KojiUploaderResult( ImportFile importFile )
    {
        this.uploadFilePath = importFile.getFilePath();
        this.uploadSize = importFile.getSize();
    }

    public Exception getError()
    {
        return error;
    }

    public boolean isTemporaryError()
    {
        return temporaryError;
    }

    public void setError( Exception error, boolean temporaryError )
    {
        this.error = error;
        this.temporaryError = temporaryError;
    }

    public UploadResponse getResponse()
    {
        return response;
    }

    public void setResponse( UploadResponse response )
    {
        this.response = response;
    }

    public String getUploadFilePath()
    {
        return uploadFilePath;
    }

    public long getUploadSize()
    {
        return uploadSize;
    }
}
