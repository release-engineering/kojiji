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

/**
 * Created by jdcasey on 2/19/16.
 */
public final class KojiXmlRpcConstants
{
    public static final String SSL_LOGIN_PATH = "ssllogin";

    public static final String ACCEPT_ENCODING_HEADER = "Accept-Encoding";

    public static final String IDENTITY_ENCODING_VALUE = "identity";

    public static final String SESSION_ID_PARAM = "session-id";

    public static final String SESSION_KEY_PARAM = "session-key";

    public static final String CALL_NUMBER_PARAM = "callnum";

    public static final String UPLOAD_DIR_PARAM = "filepath";

    public static final String UPLOAD_CHECKSUM_TYPE_PARAM = "fileverify";

    public static final String ADLER_32_CHECKSUM = "adler32";

    public static final String UPLOAD_FILENAME_PARAM = "filename";

    public static final String UPLOAD_OFFSET_PARAM = "offset";

    public static final String UPLOAD_OVERWRITE_PARAM = "overwrite";

    public static final String EMBEDDED_ERROR_PARAM = "ERROR";

    public static final String METADATA_JSON_FILE = "metadata.json";

    private KojiXmlRpcConstants(){}

}
