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
package com.redhat.red.build.koji.model.xmlrpc;

import com.redhat.red.build.koji.model.converter.KojiAuthTypeConverter;
import com.redhat.red.build.koji.model.converter.KojiUserStatusConverter;
import com.redhat.red.build.koji.model.converter.KojiUserTypeConverter;
import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

/**
 * Created by jdcasey on 12/3/15.
 */
@StructPart
public class KojiUserInfo
{
    @DataKey( "status" )
    @Converter( KojiUserStatusConverter.class )
    private KojiUserStatus status;

    @DataKey( "authtype" )
    @Converter( KojiAuthTypeConverter.class )
    private KojiAuthType authType;

    @DataKey( "usertype" )
    @Converter( KojiUserTypeConverter.class )
    private KojiUserType userType;

    @DataKey( "id" )
    private int userId;

    @DataKey( "name" )
    private String userName;

    @DataKey( "krb_principal" )
    private String kerberosPrincipal;

    public KojiUserInfo( KojiUserStatus status, KojiAuthType authType, KojiUserType userType, int userId, String userName, String kerberosPrincipal )
    {
        this.status = status;
        this.authType = authType;
        this.userType = userType;
        this.userId = userId;
        this.userName = userName;
        this.kerberosPrincipal = kerberosPrincipal;
    }

    public KojiUserInfo()
    {
    }

    public void setStatus( KojiUserStatus status )
    {
        this.status = status;
    }

    public void setAuthType( KojiAuthType authType )
    {
        this.authType = authType;
    }

    public void setUserType( KojiUserType userType )
    {
        this.userType = userType;
    }

    public void setUserId( int userId )
    {
        this.userId = userId;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public void setKerberosPrincipal( String kerberosPrincipal )
    {
        this.kerberosPrincipal = kerberosPrincipal;
    }

    public String getKerberosPrincipal()
    {
        return kerberosPrincipal;
    }

    public KojiUserStatus getStatus()
    {
        return status;
    }

    public KojiAuthType getAuthType()
    {
        return authType;
    }

    public KojiUserType getUserType()
    {
        return userType;
    }

    public int getUserId()
    {
        return userId;
    }

    public String getUserName()
    {
        return userName;
    }

    @Override
    public String toString()
    {
        return "KojiUserInfo{" +
                "status=" + status +
                ", authType=" + authType +
                ", userType=" + userType +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", kerberosPrincipal='" + kerberosPrincipal + '\'' +
                '}';
    }
}
