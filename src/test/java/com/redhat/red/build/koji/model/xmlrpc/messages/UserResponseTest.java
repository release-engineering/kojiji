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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiAuthType;
import com.redhat.red.build.koji.model.xmlrpc.KojiUserInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiUserStatus;
import com.redhat.red.build.koji.model.xmlrpc.KojiUserType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by jdcasey on 12/3/15.
 */
public class UserResponseTest
                extends AbstractKojiMessageTest
{
    private static final String USER_NAME = "newcastle";

    private static final int USER_ID = 2982;

    private static final KojiUserStatus STATUS = KojiUserStatus.NORMAL;

    private static final KojiUserType USER_TYPE = KojiUserType.NORMAL;

    private static final String KRB_PRINCIPAL = "me@MYCO.COM";

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        UserResponse parsed = parseCapturedMessage( UserResponse.class, "user-krbPrincipal-response.xml" );
        assertUserResponse( parsed, true );
    }

    @Test
    public void verifyVsCapturedHttpRequest_NilKerberosPrincipal() throws Exception
    {
        UserResponse parsed = parseCapturedMessage( UserResponse.class, "user-noKrbPrincipal-response.xml" );
        assertUserResponse( parsed, false );
    }

    @Test
    public void roundTrip() throws Exception
    {
        UserResponse parsed = roundTrip( UserResponse.class, newResponse( true ) );
        assertNotNull( parsed );
    }

    private void assertUserResponse( UserResponse parsed, boolean enableKerb )
    {
        UserResponse expected = newResponse( enableKerb );
        assertEquals( expected.getUserInfo().toString(), parsed.getUserInfo().toString() );
    }

    private UserResponse newResponse( boolean enableKerberosPrincipal )
    {
        return new UserResponse( new KojiUserInfo( STATUS, enableKerberosPrincipal ? KojiAuthType.KERB : KojiAuthType.NORMAL, USER_TYPE, USER_ID, USER_NAME,
                                                   enableKerberosPrincipal ? KRB_PRINCIPAL : null ) );
    }

}
