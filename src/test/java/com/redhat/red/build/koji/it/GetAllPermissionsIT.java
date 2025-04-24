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
import com.redhat.red.build.koji.model.xmlrpc.KojiPermission;
import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.List;

/**
 * Created by jdcasey on 11/13/15.
 */
public class GetAllPermissionsIT
    extends AbstractIT
{

    @Test
    public void getTestUserInfo()
            throws Exception
    {
        KojiClient client = newKojiClient();
        KojiSessionInfo session = client.login();
        List<KojiPermission> permissions = client.getAllPermissions( session );
        System.out.println( StringUtils.join( permissions, "\n" ) );
    }
}
