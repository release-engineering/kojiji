/**
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
package com.redhat.red.build.koji.model.json;

/**
 * Created by jdcasey on 2/16/16.
 */
public final class KojiJsonConstants
{
    public static final String ARCH = "arch";

    public static final String TYPE = "type";

    public static final String OS = "os";

    public static final String BUILDROOT_ID = "buildroot_id";

    public static final String FILENAME = "filename";

    public static final String FILESIZE = "filesize";

    public static final String CHECKSUM_TYPE = "checksum_type";

    public static final String CHECKSUM = "checksum";

    public static final String NAME = "name";

    public static final String VERSION = "version";

    public static final String RELEASE = "release";

    public static final String SOURCE = "source";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String ID = "id";

    public static final String HOST = "host";

    public static final String CONTENT_GENERATOR = "content_generator";

    public static final String CONTAINER = "container";

    public static final String TOOLS = "tools";

    public static final String COMPONENTS = "components";

    public static final String OUTPUT = "output";

    public static final String EXTRA_INFO = "extra";

    public static final String BUILD = "build";

    public static final String BUILDROOTS = "buildroots";

    public static final String METADATA_VERSION = "metadata_version";

    public static final int DEFAULT_METADATA_VERSION = 0;

    public static final String MD5_CHECKSUM_TYPE = "md5";

    public static final String LOG_OUTPUT_TYPE = "log";

    public static final String MAVEN_OUTPUT_TYPE = "maven";

    public static final String EPOCH = "epoch";

    public static final String SIGMD5 = "sigmd5";

    public static final String SIGNATURE = "signature";

    public static final String URL = "url";

    public static final String REVISION = "revision";

    public static final String RPM = "rpm";

    public static final String FILE = "file";

    public static final String GROUP_ID = "group_id";

    public static final String ARTIFACT_ID = "artifact_id";

    public static final String MAVEN_INFO = "maven";

    public static final String EXTERNAL_BUILD_ID = "external_build_id";

    private KojiJsonConstants(){}

}
