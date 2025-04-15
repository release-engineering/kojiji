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
package com.redhat.red.build.koji.it;

import com.redhat.red.build.koji.config.SimpleKojiConfigBuilder;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * External IT tests access an external Koji server, issue command and check results. An VM parameter -Dkoji.config.dir is
 * needed to specify the koji configuration file (${koji.config.dir}/conf.d/koji.conf) and pem files. E.g.,
 * <pre>
 * [koji]
 * url=https://kojihub.foo.com/kojihub
 * client.pem.path=${koji.config.dir}/koji/client.pem
 * client.pem.password=xxx
 * server.pem.path=${koji.config.dir}/koji/server.pem
 * </pre>
 *
 * Until the external Koji server is ready, by default external IT tests are ignored (via @Ignore).
 */
public class ExternalAbstractIT extends AbstractIT
{
    final static String kojiConfigDir = System.getProperty("koji.config.dir");

    @Override
    protected SimpleKojiConfigBuilder getKojiConfigBuilder()
            throws Exception
    {
        if ( kojiConfigDir == null )
        {
            return null;
        }
        Properties props = new Properties();
        props.load(new FileInputStream(kojiConfigDir + "/conf.d/koji.conf"));

        SimpleKojiConfigBuilder builder = new SimpleKojiConfigBuilder( props.getProperty("url") )
                .withClientKeyCertificateFile( getCanonicalPath(props.getProperty("client.pem.path")) )
                .withKojiClientCertificatePassword( props.getProperty("client.pem.password") )
                .withServerCertificateFile( getCanonicalPath(props.getProperty("server.pem.path")) );

        return builder;
    }

    private String getCanonicalPath( String path )
    {
        return path.replaceFirst( "\\$\\{.+\\}", kojiConfigDir );
    }

}
