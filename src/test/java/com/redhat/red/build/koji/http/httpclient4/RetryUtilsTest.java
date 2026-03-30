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
package com.redhat.red.build.koji.http.httpclient4;

import com.redhat.red.build.koji.KojiClientUtils;
import com.redhat.red.build.koji.model.xmlrpc.messages.AllPermissionsRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ApiVersionRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.CGInlinedImportRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.CGUploadedImportRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.CheckPermissionRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.Constants;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetBuildTypeRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListBuildTypesRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoginRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoginResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.LogoutRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.MultiCallRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.QueryRpmSigsRequest;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.commonjava.rwx.vocab.Nil.NIL_VALUE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RetryUtilsTest
{
    @Test
    public void testIsSafeToRetry()
    {
        assertThat( RetryUtils.isSafeToRetry( new AllPermissionsRequest() ), is( true ) );
        assertThat( RetryUtils.isSafeToRetry( new ApiVersionRequest() ), is( true ) );
        assertThat( RetryUtils.isSafeToRetry( new CheckPermissionRequest() ), is( true ) );
        assertThat( RetryUtils.isSafeToRetry( new GetBuildTypeRequest() ), is( true ) );
        assertThat( RetryUtils.isSafeToRetry( new ListBuildTypesRequest() ), is( true ) );
        assertThat( RetryUtils.isSafeToRetry( new QueryRpmSigsRequest() ), is( true ) );
    }

    @Test
    public void testIsNotSafeToRetry()
    {
        assertThat( RetryUtils.isSafeToRetry( new CGInlinedImportRequest() ), is( false ) );
        assertThat( RetryUtils.isSafeToRetry( new CGUploadedImportRequest() ), is( false ) );
        assertThat( RetryUtils.isSafeToRetry( new LoginRequest() ), is( false ) );
        assertThat( RetryUtils.isSafeToRetry( new LogoutRequest() ), is( false ) );
        assertThat( RetryUtils.isSafeToRetry( new LoginResponse() ), is( false ) );
    }

    @Test
    public void testRetryWithMultiCall()
    {
        List<Object> args = Collections.singletonList( NIL_VALUE );
        MultiCallRequest req1 = KojiClientUtils.buildMultiCallRequest( Constants.GET_API_VERSION, args );
        assertThat( RetryUtils.isSafeToRetry( req1 ), is( true ) );
        MultiCallRequest req2 = KojiClientUtils.buildMultiCallRequest( Constants.LOGOUT, args );
        assertThat( RetryUtils.isSafeToRetry( req2 ), is( false ) );

    }
}
