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

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExternalGetBuildExtraInfoIT
    extends ExternalAbstractIT
{
    /**
     $ koji buildinfo rhel-server-docker-6.9-49
     BUILD: rhel-server-docker-6.9-49 [537666]
     State: COMPLETE
     Built by: osbs
     Source: git://pkgs.devel.redhat.com/rpms/rhel-server-docker#7afb0c2a4007cef7ae4e0deb3e058d98c0c49647
     Volume: DEFAULT
     Task: none
     Finished: Fri, 10 Feb 2017 01:54:44 CET
     Tags: guest-rhel-6.9-candidate
     Extra: {'image': {}, 'container_koji_task_id': 12522523, 'filesystem_koji_task_id': 12522529}
     */
    @Ignore
    @Test
    public void run()
            throws Exception
    {
        KojiClient client = newKojiClient();

        KojiSessionInfo session = client.login();
        KojiBuildInfo info = client.getBuildInfo( "rhel-server-docker-6.9-49", session );
        client.logout( session );

        assertThat( info, notNullValue() );
        assertThat( info.getExtra().get( "image" ), instanceOf( HashMap.class ) );
        assertThat( info.getExtra().get( "container_koji_task_id" ), equalTo( 12522523 ) );
    }
}
