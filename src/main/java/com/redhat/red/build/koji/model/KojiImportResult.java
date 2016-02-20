package com.redhat.red.build.koji.model;

import com.redhat.red.build.koji.KojiClientException;
import com.redhat.red.build.koji.model.json.KojiImport;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;

import java.util.Map;

/**
 * Created by jdcasey on 2/17/16.
 */
public class KojiImportResult
{

    private KojiBuildInfo buildInfo;

    private KojiImport importInfo;

    private Map<String, KojiClientException> uploadErrors;

    public KojiImportResult( KojiImport importInfo )
    {
        this.importInfo = importInfo;
    }

    public KojiBuildInfo getBuildInfo()
    {
        return buildInfo;
    }

    public KojiImportResult withBuildInfo( KojiBuildInfo buildInfo )
    {
        this.buildInfo = buildInfo;
        return this;
    }

    public KojiImport getImportInfo()
    {
        return importInfo;
    }

    public Map<String, KojiClientException> getUploadErrors()
    {
        return uploadErrors;
    }

    public KojiImportResult withUploadErrors( Map<String, KojiClientException> errors )
    {
        this.uploadErrors = errors;
        return this;
    }
}
