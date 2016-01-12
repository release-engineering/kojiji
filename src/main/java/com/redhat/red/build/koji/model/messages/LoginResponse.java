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
package com.redhat.red.build.koji.model.messages;

import com.redhat.red.build.koji.model.KojiSessionInfo;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Response;

/**
 * Created by jdcasey on 11/17/15.
 */
@Response
public class LoginResponse
{
    @DataIndex( 0 )
    private KojiSessionInfo sessionInfo;

    @IndexRefs( {0} )
    public LoginResponse( KojiSessionInfo sessionInfo )
    {
        this.sessionInfo = sessionInfo;
    }

    public KojiSessionInfo getSessionInfo()
    {
        return sessionInfo;
    }
}
