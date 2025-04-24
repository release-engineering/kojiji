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
package com.redhat.red.build.koji.it;

import com.redhat.red.build.koji.KojiClient;
import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiMavenRef;
import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import org.junit.Test;

/**
 * Created by ruhan on 9/14/17.
 */
public class ListArchivesIT
    extends AbstractIT
{

    @Test
    public void run()
            throws Exception
    {
        KojiClient client = newKojiClient();
        KojiSessionInfo session = client.login();

        KojiArchiveQuery q = new KojiArchiveQuery(  );
        q.setChecksum( "ABC" );
        KojiMavenRef r = new KojiMavenRef( "test", "test", "1.0" );
        q.setMavenRef( r );
        client.listArchives( q, session );
    }
}
