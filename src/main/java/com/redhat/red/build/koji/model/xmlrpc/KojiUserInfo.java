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

import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.KeyRefs;
import org.commonjava.rwx.binding.anno.StructPart;

/**
 * Created by jdcasey on 12/3/15.
 */
@StructPart
public class KojiUserInfo
{
    @DataKey( "status" )
    private int status;

    @DataKey( "usertype" )
    private int userType;

    @DataKey( "id" )
    private int userId;

    @DataKey( "name" )
    private String userName;

    @DataKey( "krb_principal" )
    private String kerberosPrincipal;

    @KeyRefs( { "status", "usertype", "id", "name", "krb_principal" } )
    public KojiUserInfo( int status, int userType, int userId, String userName, String kerberosPrincipal )
    {
        this.status = status;
        this.userType = userType;
        this.userId = userId;
        this.userName = userName;
        this.kerberosPrincipal = kerberosPrincipal;
    }

    public String getKerberosPrincipal()
    {
        return kerberosPrincipal;
    }

    public int getStatus()
    {
        return status;
    }

    public int getUserType()
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
                ", userType=" + userType +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", kerberosPrincipal='" + kerberosPrincipal + '\'' +
                '}';
    }
}
