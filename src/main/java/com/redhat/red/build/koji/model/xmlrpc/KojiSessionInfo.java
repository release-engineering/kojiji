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
package com.redhat.red.build.koji.model.xmlrpc;

import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

/**
 * Created by jdcasey on 12/3/15.
 */
@StructPart
public class KojiSessionInfo
        implements Destroyable
{
    @DataKey( "session-id" )
    private int sessionId;

    @DataKey( "session-key" )
    private String sessionKey;

    private transient KojiUserInfo userInfo;

    private transient boolean destroyed = false;

    public KojiSessionInfo( int sessionId, String sessionKey )
    {
        this.sessionId = sessionId;
        this.sessionKey = sessionKey;
    }

    public KojiSessionInfo()
    {
    }

    public void setSessionId( int sessionId )
    {
        this.sessionId = sessionId;
    }

    public void setSessionKey( String sessionKey )
    {
        this.sessionKey = sessionKey;
    }

    public void setDestroyed( boolean destroyed )
    {
        this.destroyed = destroyed;
    }

    public int getSessionId()
    {
        if ( destroyed )
        {
             throw new IllegalStateException( "This session is no longer valid" );
        }

        return sessionId;
    }

    public String getSessionKey()
    {
        if ( destroyed )
        {
             throw new IllegalStateException( "This session is no longer valid" );
        }

        return sessionKey;
    }

    @Override
    public String toString()
    {
        return "KojiSessionInfo{" +
                "sessionId=" + sessionId +
                ", sessionKey='" + sessionKey + '\'' +
                '}';
    }

    public void setUserInfo( KojiUserInfo userInfo )
    {
        this.userInfo = userInfo;
    }

    public KojiUserInfo getUserInfo()
    {
        if ( destroyed )
        {
            throw new IllegalStateException( "This session is no longer valid" );
        }

        return userInfo;
    }

    @Override
    public void destroy() throws DestroyFailedException
    {
        if ( !destroyed )
        {
            sessionId = 0;
            sessionKey = null;
            userInfo = null;
            destroyed = true;
        }
    }

    @Override
    public boolean isDestroyed()
    {
        return destroyed;
    }
}
