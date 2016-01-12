package com.redhat.red.build.koji.config;

import org.commonjava.util.jhttpc.model.SiteConfig;

/**
 * Created by jdcasey on 1/12/16.
 */
public class SimpleKojiConfigBuilder
{
    private static final String DEFAULT_KOJI_SITE_ID = "koji";

    private String clientKeyCertificateFile;

    private String clientCertificatePassword;

    private String serverCertificateFile;

    private Boolean trustSelfSigned;

    private String id = DEFAULT_KOJI_SITE_ID;

    private String kojiURL;

    private Integer timeout;

    private SiteConfig kojiSiteConfig;

    public SimpleKojiConfigBuilder( String kojiURL )
    {
        this.kojiURL = kojiURL;
    }

    public SimpleKojiConfig build()
    {
        return new SimpleKojiConfig( id, kojiURL, clientKeyCertificateFile, clientCertificatePassword, serverCertificateFile, timeout, trustSelfSigned );
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

    public String getClientCertificatePassword()
    {
        return clientCertificatePassword;
    }

    public SimpleKojiConfigBuilder withClientCertificatePassword( String clientCertificatePassword )
    {
        this.clientCertificatePassword = clientCertificatePassword;
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

    public String getId()
    {
        return id;
    }

    public SimpleKojiConfigBuilder withId( String id )
    {
        this.id = id;
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
