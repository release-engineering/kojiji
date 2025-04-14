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
package com.redhat.red.build.koji.config;

import org.commonjava.util.jhttpc.model.SiteConfig;

/**
 * Created by jdcasey on 1/12/16.
 */
public class SimpleKojiConfigBuilder
{
    private static final String DEFAULT_KOJI_SITE_ID = "koji";

    private String clientKeyCertificateFile;

    private String kojiClientCertificatePassword;

    private String serverCertificateFile;

    private Boolean trustSelfSigned;

    private String kojiSiteId = DEFAULT_KOJI_SITE_ID;

    private String kojiURL;

    private Integer timeout;

    private Integer connectionPoolTimeout;

    private Integer maxConnections;

    private String krbCCache;

    private String krbKeytab;

    private String krbPassword;

    private String krbPrincipal;

    private String krbService;

    private SiteConfig kojiSiteConfig;

    public SimpleKojiConfigBuilder()
    {

    }

    public SimpleKojiConfigBuilder( String kojiURL )
    {
        this.kojiURL = kojiURL;
    }

    public SimpleKojiConfig build()
    {
        return new SimpleKojiConfig( kojiSiteId, kojiURL, clientKeyCertificateFile, kojiClientCertificatePassword,
                                     serverCertificateFile, timeout, connectionPoolTimeout, trustSelfSigned,
                                     maxConnections, krbService, krbPrincipal, krbPassword, krbCCache, krbKeytab );
    }

    public String getClientKeyCertificateFile()
    {
        return clientKeyCertificateFile;
    }

    public SimpleKojiConfigBuilder withClientKeyCertificateFile( String clientKeyCertificateFile )
    {
        this.clientKeyCertificateFile = clientKeyCertificateFile;
        return this;
    }

    public String getKojiClientCertificatePassword()
    {
        return kojiClientCertificatePassword;
    }

    public SimpleKojiConfigBuilder withKojiClientCertificatePassword( String clientCertificatePassword )
    {
        this.kojiClientCertificatePassword = clientCertificatePassword;
        return this;
    }

    public String getServerCertificateFile()
    {
        return serverCertificateFile;
    }

    public SimpleKojiConfigBuilder withServerCertificateFile( String serverCertificateFile )
    {
        this.serverCertificateFile = serverCertificateFile;
        return this;
    }

    public Boolean getTrustSelfSigned()
    {
        return trustSelfSigned;
    }

    public SimpleKojiConfigBuilder withTrustSelfSigned( Boolean trustSelfSigned )
    {
        this.trustSelfSigned = trustSelfSigned;
        return this;
    }

    public String getKojiSiteId()
    {
        return kojiSiteId;
    }

    public SimpleKojiConfigBuilder withKojiSiteId( String id )
    {
        this.kojiSiteId = id;
        return this;
    }

    public String getKojiURL()
    {
        return kojiURL;
    }

    public SimpleKojiConfigBuilder withKojiURL( String kojiURL )
    {
        this.kojiURL = kojiURL;
        return this;
    }

    public Integer getTimeout()
    {
        return timeout;
    }

    public SimpleKojiConfigBuilder withTimeout( Integer timeout )
    {
        this.timeout = timeout;
        return this;
    }

    public Integer getConnectionPoolTimeout()
    {
        return connectionPoolTimeout;
    }

    public SimpleKojiConfigBuilder withConnectionPoolTimeout( Integer connectionPoolTimeout )
    {
        this.connectionPoolTimeout = connectionPoolTimeout;
        return this;
    }

    public Integer getMaxConnections()
    {
        return maxConnections;
    }

    public SimpleKojiConfigBuilder withMaxConnections( Integer maxConnections )
    {
        this.maxConnections = maxConnections;
        return this;
    }

    public String getKrbCCache()
    {
        return krbCCache;
    }

    public SimpleKojiConfigBuilder withKrbCCache( String krbCCache )
    {
        this.krbCCache = krbCCache;
        return this;
    }

    public String getKrbKeytab()
    {
        return krbKeytab;
    }

    public SimpleKojiConfigBuilder withKrbKeytab( String krbKeytab )
    {
        this.krbKeytab = krbKeytab;
        return this;
    }

    public String getKrbPassword()
    {
        return krbPassword;
    }

    public SimpleKojiConfigBuilder withKrbPassword( String krbPassword )
    {
        this.krbPassword = krbPassword;
        return this;
    }

    public String getKrbPrincipal()
    {
        return krbPrincipal;
    }

    public SimpleKojiConfigBuilder withKrbPrincipal( String krbPrincipal )
    {
        this.krbPrincipal = krbPrincipal;
        return this;
    }

    public String getKrbService()
    {
        return krbService;
    }

    public SimpleKojiConfigBuilder withKrbService( String krbService )
    {
        this.krbService = krbService;
        return this;
    }

    public SiteConfig getKojiSiteConfig()
    {
        return kojiSiteConfig;
    }

    public SimpleKojiConfigBuilder withKojiSiteConfig( SiteConfig kojiSiteConfig )
    {
        this.kojiSiteConfig = kojiSiteConfig;
        return this;
    }
}
