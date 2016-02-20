package com.redhat.red.build.koji.model.xmlrpc;

import com.redhat.red.build.koji.KojiClientException;
import com.redhat.red.build.koji.model.ImportFile;
import com.redhat.red.build.koji.model.xmlrpc.messages.UploadResponse;

/**
 * Created by jdcasey on 2/19/16.
 */
public final class KojiUploaderResult
{
    private ImportFile importFile;

    private KojiClientException error;

    private UploadResponse response;

    public KojiUploaderResult( ImportFile importFile )
    {
        this.importFile = importFile;
    }

    public ImportFile getImportFile()
    {
        return importFile;
    }

    public KojiClientException getError()
    {
        return error;
    }

    public void setError( KojiClientException error )
    {
        this.error = error;
    }

    public UploadResponse getResponse()
    {
        return response;
    }

    public void setResponse( UploadResponse response )
    {
        this.response = response;
    }
}
