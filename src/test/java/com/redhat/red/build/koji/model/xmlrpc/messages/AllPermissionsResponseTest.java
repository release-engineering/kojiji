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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiPermission;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jdcasey on 12/3/15.
 */
public class AllPermissionsResponseTest
                extends AbstractKojiMessageTest
{

    private static final String filename = "getAllPerms-response.xml";

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        AllPermissionsResponse response = parseCapturedMessage( AllPermissionsResponse.class, filename );
        AllPermissionsResponse expected = newResponse();

        assertAllPermissionsResponse( expected, response );
    }

    @Test
    public void roundTrip() throws Exception
    {
        AllPermissionsResponse response = newResponse();
        AllPermissionsResponse rounded = roundTrip( AllPermissionsResponse.class, response );
        assertAllPermissionsResponse( rounded, response );
    }

    private void assertAllPermissionsResponse( AllPermissionsResponse expected, AllPermissionsResponse response )
    {
        assertEquals( expected.getPermissions().size(), response.getPermissions().size() );

        List<KojiPermission> permissions_expected = expected.getPermissions();
        List<KojiPermission> permissions_response = response.getPermissions();

        assertEquals( permissions_expected.get( 0 ).getId(), permissions_response.get( 0 ).getId() );
        assertEquals( permissions_expected.get( 0 ).getName(), permissions_response.get( 0 ).getName() );
        assertEquals( permissions_expected.get( 1 ).getId(), permissions_response.get( 1 ).getId() );
        assertEquals( permissions_expected.get( 1 ).getName(), permissions_response.get( 1 ).getName() );
        assertEquals( permissions_expected.get( 2 ).getId(), permissions_response.get( 2 ).getId() );
        assertEquals( permissions_expected.get( 2 ).getName(), permissions_response.get( 2 ).getName() );
    }

    private AllPermissionsResponse newResponse()
    {
        return new AllPermissionsResponse(
                        Arrays.asList( new KojiPermission( 1, "admin" ), new KojiPermission( 2, "build" ),
                                       new KojiPermission( 3, "repo" ) ) );
    }

}
