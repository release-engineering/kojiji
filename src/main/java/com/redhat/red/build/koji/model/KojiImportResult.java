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
package com.redhat.red.build.koji.model;

import com.redhat.red.build.koji.KojijiErrorInfo;
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

    private Map<String, KojijiErrorInfo> uploadErrors;

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

    public Map<String, KojijiErrorInfo> getUploadErrors()
    {
        return uploadErrors;
    }

    public KojiImportResult withUploadErrors( Map<String, KojijiErrorInfo> errors )
    {
        this.uploadErrors = errors;
        return this;
    }
}
