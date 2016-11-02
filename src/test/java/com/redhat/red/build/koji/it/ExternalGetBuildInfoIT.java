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
package com.redhat.red.build.koji.it;

import com.redhat.red.build.koji.KojiClient;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class ExternalGetBuildInfoIT
    extends ExternalAbstractIT
{
    @Ignore
    @Test
    public void run()
            throws Exception
    {
        KojiClient client = newKojiClient();
        int apiVersion = client.getApiVersion();
        assertThat( apiVersion, equalTo( 1 ) );

        KojiSessionInfo session = client.login();
        KojiBuildInfo info = client.getBuildInfo("org.jboss.resteasy-tjws-3.0.17.Final-1", session);
        client.logout(session);

        assertThat(info, notNullValue());
        assertThat(info.getGAV(), notNullValue());
        assertThat(info.getMavenGroupId(), equalTo("org.jboss.resteasy"));
        assertThat(info.getMavenArtifactId(), equalTo("tjws"));
        assertThat(info.getMavenVersion(), equalTo("3.0.17.Final"));
    }

}
