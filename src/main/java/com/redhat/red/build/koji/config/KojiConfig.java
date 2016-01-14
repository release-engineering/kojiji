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
}
