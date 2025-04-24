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
package com.redhat.red.build.koji.config;

import org.commonjava.util.jhttpc.model.SiteConfig;

import java.io.IOException;

/**
 * Created by jdcasey on 1/12/16.
 */
public interface KojiConfig
{
    SiteConfig getKojiSiteConfig() throws IOException;

    String getKojiURL();

    String getKojiClientCertificatePassword();

    String getKojiSiteId();

    String getKrbCCache();

    String getKrbKeytab();

    String getKrbPassword();

    String getKrbPrincipal();

    String getKrbService();
}
