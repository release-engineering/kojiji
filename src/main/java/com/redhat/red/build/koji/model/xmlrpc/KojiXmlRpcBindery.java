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

import com.redhat.red.build.koji.model.xmlrpc.messages.AllPermissionsRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ApiVersionRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.CheckPermissionRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ConfirmationResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.CreateTagRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetPackageIdRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.IdResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListBuildsRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListBuildsResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoggedInUserRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoginRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoginResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.LogoutRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.StatusResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.TagRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.TagResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.UserRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.UserResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.AllPermissionsResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.ApiVersionResponse;
import org.commonjava.rwx.binding.error.BindException;
import org.commonjava.rwx.binding.internal.reflect.ReflectionMapper;
import org.commonjava.rwx.binding.internal.xbr.XBRCompositionBindery;
import org.commonjava.rwx.binding.mapping.Mapping;

import java.util.Map;

/**
 * Created by jdcasey on 12/3/15.
 */
public class KojiXmlRpcBindery
        extends XBRCompositionBindery
{
    public KojiXmlRpcBindery()
            throws BindException
    {
        super( getRecipes() );
    }

    private static Map<Class<?>, Mapping<?>> getRecipes()
            throws BindException
    {
        Class<?>[] classes = { AllPermissionsRequest.class, AllPermissionsResponse.class, ApiVersionRequest.class,
                ApiVersionResponse.class, CheckPermissionRequest.class, ConfirmationResponse.class,
                CreateTagRequest.class, GetPackageIdRequest.class, IdResponse.class, LoggedInUserRequest.class, LoginRequest.class,
                LoginResponse.class, LogoutRequest.class, StatusResponse.class, TagRequest.class, TagResponse.class,
                UserRequest.class, UserResponse.class, ListBuildsRequest.class, ListBuildsResponse.class };

        return new ReflectionMapper().loadRecipes( classes );
    }
}
