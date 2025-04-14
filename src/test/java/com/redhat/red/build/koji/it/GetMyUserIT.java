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

import com.redhat.red.build.koji.KojiClient;
import com.redhat.red.build.koji.model.xmlrpc.KojiUserInfo;
import org.junit.Test;

/**
 * Created by jdcasey on 11/13/15.
 */
public class GetMyUserIT
    extends AbstractIT
{

    @Test
    public void getKojiAdminUserInfo()
            throws Exception
    {
        KojiClient client = newKojiClient();
        KojiUserInfo userInfo = client.getLoggedInUserInfo( "kojiadmin" );
        System.out.println(userInfo);
    }
}
