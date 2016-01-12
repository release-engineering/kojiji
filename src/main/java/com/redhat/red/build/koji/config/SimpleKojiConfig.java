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

    private String clientKeyCertificateFile;

    private String clientCertificatePassword;

    private String serverCertificateFile;

    private Boolean trustSelfSigned;

    private String id = DEFAULT_KOJI_SITE_ID;

    private String kojiURL;

    private Integer timeout;

    private SiteConfig kojiSiteConfig;

    public SimpleKojiConfig( String id, String kojiURL, String clientKeyCertificateFile, String clientCertificatePassword,
                             String serverCertificateFile, Integer timeout, Boolean trustSelfSigned )
    {
        this.clientKeyCertificateFile = clientKeyCertificateFile;
        this.clientCertificatePassword = clientCertificatePassword;
        this.serverCertificateFile = serverCertificateFile;
        this.timeout = timeout;
        this.trustSelfSigned = trustSelfSigned;
        this.id = id;
        this.kojiURL = kojiURL;
    }

    @Override
    public synchronized SiteConfig getKojiSiteConfig()
            throws IOException
    {

        if ( kojiSiteConfig == null )
        {
            SiteConfigBuilder builder = new SiteConfigBuilder( getId(), getKojiURL() );
            File keyCert = new File( getClientKeyCertificateFile() );
            if ( keyCert.exists() )
            {
                builder.withKeyCertPem( FileUtils.readFileToString( keyCert ) );
            }

            File serverCert = new File( getServerCertificateFile() );
            if ( serverCert.exists() )
            {
                builder.withServerCertPem( FileUtils.readFileToString( serverCert ) );
            }

            if ( getTrustSelfSigned() )
            {
                builder.withTrustType( SiteTrustType.TRUST_SELF_SIGNED );
            }

            builder.withRequestTimeoutSeconds( getTimeout() );

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

    public String getClientCertificatePassword()
    {
        return clientCertificatePassword;
    }

    public String getServerCertificateFile()
    {
        return serverCertificateFile;
    }

    public Boolean getTrustSelfSigned()
    {
        return trustSelfSigned;
    }

    public String getId()
    {
        return id;
    }

    public Integer getTimeout()
    {
        return timeout;
    }
}
