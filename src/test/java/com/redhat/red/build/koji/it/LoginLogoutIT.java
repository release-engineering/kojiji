/**
 * Copyright (C) 2015 Red Hat, Inc. (jdcasey@commonjava.org)
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

import com.redhat.red.build.koji.KojiBindery;
import com.redhat.red.build.koji.KojiClient;
import com.redhat.red.build.koji.config.KojiConfig;
import com.redhat.red.build.koji.model.KojiSessionInfo;
import org.commonjava.util.jhttpc.HttpFactory;
import org.commonjava.util.jhttpc.auth.MemoryPasswordManager;
import org.commonjava.util.jhttpc.auth.PasswordManager;
import org.commonjava.util.jhttpc.auth.PasswordType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by jdcasey on 11/13/15.
 */
public class LoginLogoutIT
    extends AbstractIT
{

    @Test
    public void login_logout()
            throws Exception
    {
        KojiClient client = newKojiClient();
        KojiSessionInfo sessionInfo = client.login();
        client.logout( sessionInfo );
        System.out.println(sessionInfo);
    }
}
