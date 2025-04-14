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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jdcasey on 8/9/16.
 */
public class UntagBuildRequestTest
                extends AbstractKojiMessageTest
{

    private static final String TAG = "test-tag";

    private static final String NVR = "org.foo-bar-1.1-2";

    @Test
    public void verifyVsCapturedHttpRequest() throws Exception
    {
        UntagBuildRequest parsed = parseCapturedMessage( UntagBuildRequest.class, "untagBuild-request-0.xml" );
        assertUntagBuildRequest( parsed );
    }

    private void assertUntagBuildRequest( UntagBuildRequest parsed )
    {
        assertThat( parsed.getTag().getName(), equalTo( TAG ) );
        assertThat( parsed.getBuild().getName(), equalTo( NVR ) );
    }

    @Test
    public void roundTrip() throws Exception
    {
        UntagBuildRequest parsed = roundTrip( UntagBuildRequest.class, new UntagBuildRequest( TAG, NVR ) );
        assertUntagBuildRequest( parsed );
    }
}
