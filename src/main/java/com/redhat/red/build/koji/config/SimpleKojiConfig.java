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
package com.redhat.red.build.koji.config;

import org.apache.commons.io.FileUtils;
import org.commonjava.util.jhttpc.model.SiteConfig;
import org.commonjava.util.jhttpc.model.SiteConfigBuilder;
import org.commonjava.util.jhttpc.model.SiteTrustType;

import java.io.File;
import java.io.IOException;

/**
 * Created by jdcasey on 1/12/16.
 */
public class SimpleKojiConfig
    implements KojiConfig
{
    private static final String DEFAULT_KOJI_SITE_ID = "koji";

    private static final int DEFAULT_TIMEOUT_SECONDS = 30;

    private String clientKeyCertificateFile;

    private String clientCertificatePassword;

    private String serverCertificateFile;

    private Boolean trustSelfSigned;

    private String id = DEFAULT_KOJI_SITE_ID;

    private String kojiURL;

    private Integer maxConnections;

    private Integer timeout;

    private SiteConfig kojiSiteConfig;

    public SimpleKojiConfig( String id, String kojiURL, String clientKeyCertificateFile, String clientCertificatePassword,
                             String serverCertificateFile, Integer timeout, Boolean trustSelfSigned, Integer maxConnections )
    {
        this.clientKeyCertificateFile = clientKeyCertificateFile;
        this.clientCertificatePassword = clientCertificatePassword;
        this.serverCertificateFile = serverCertificateFile;
        this.timeout = timeout;
        this.trustSelfSigned = trustSelfSigned;
        this.id = id;
        this.kojiURL = kojiURL;
        this.maxConnections = maxConnections;
    }

    @Override
    public synchronized SiteConfig getKojiSiteConfig()
            throws IOException
    {

        if ( kojiSiteConfig == null )
        {
            SiteConfigBuilder builder = new SiteConfigBuilder( getKojiSiteId(), getKojiURL() );

            if ( getClientKeyCertificateFile() != null )
            {
                File keyCert = new File( getClientKeyCertificateFile() );

                if ( keyCert.exists() )
                {
                    builder.withKeyCertPem( FileUtils.readFileToString( keyCert ) );
                }
            }

            if ( getServerCertificateFile() != null )
            {
                File serverCert = new File( getServerCertificateFile() );

                if ( serverCert.exists() )
                {
                    builder.withServerCertPem( FileUtils.readFileToString( serverCert ) );
                }
            }

            if ( getTrustSelfSigned() )
            {
                builder.withTrustType( SiteTrustType.TRUST_SELF_SIGNED );
            }

            builder.withRequestTimeoutSeconds( getTimeout() );
            builder.withMaxConnections( getMaxConnections() );

            kojiSiteConfig = builder.build();
        }

        return kojiSiteConfig;
    }

    @Override
    public String getKojiURL()
    {
        return kojiURL;
    }

    public String getClientKeyCertificateFile()
    {
        return clientKeyCertificateFile;
    }

    public String getKojiClientCertificatePassword()
    {
        return clientCertificatePassword;
    }

    public String getServerCertificateFile()
    {
        return serverCertificateFile;
    }

    public Boolean getTrustSelfSigned()
    {
        return trustSelfSigned == null ? false : trustSelfSigned;
    }

    public String getKojiSiteId()
    {
        return id;
    }

    public Integer getTimeout()
    {
        return timeout == null ? DEFAULT_TIMEOUT_SECONDS : timeout;
    }

    public Integer getMaxConnections()
    {
        return maxConnections == null ? SiteConfig.DEFAULT_MAX_CONNECTIONS : maxConnections;
    }
}
