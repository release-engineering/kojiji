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
package com.redhat.red.build.koji;

import com.redhat.red.build.koji.model.messages.AllPermissionsRequest;
import com.redhat.red.build.koji.model.messages.ApiVersionRequest;
import com.redhat.red.build.koji.model.messages.CheckPermissionRequest;
import com.redhat.red.build.koji.model.messages.ConfirmationResponse;
import com.redhat.red.build.koji.model.messages.LoggedInUserRequest;
import com.redhat.red.build.koji.model.messages.LoginRequest;
import com.redhat.red.build.koji.model.messages.LoginResponse;
import com.redhat.red.build.koji.model.messages.LogoutRequest;
import com.redhat.red.build.koji.model.messages.LogoutResponse;
import com.redhat.red.build.koji.model.messages.TagRequest;
import com.redhat.red.build.koji.model.messages.TagResponse;
import com.redhat.red.build.koji.model.messages.UserRequest;
import com.redhat.red.build.koji.model.messages.UserResponse;
import com.redhat.red.build.koji.model.messages.AllPermissionsResponse;
import com.redhat.red.build.koji.model.messages.ApiVersionResponse;
import org.commonjava.rwx.binding.error.BindException;
import org.commonjava.rwx.binding.internal.reflect.ReflectionMapper;
import org.commonjava.rwx.binding.internal.xbr.XBRCompositionBindery;
import org.commonjava.rwx.binding.mapping.Mapping;

import java.util.Map;

/**
 * Created by jdcasey on 12/3/15.
 */
public class KojiBindery
        extends XBRCompositionBindery
{
    public KojiBindery()
            throws BindException
    {
        super( getRecipes() );
    }

    private static Map<Class<?>, Mapping<?>> getRecipes()
            throws BindException
    {
        Class<?>[] classes =
                { LoginRequest.class, LoginResponse.class, ApiVersionRequest.class, ApiVersionResponse.class,
                        UserRequest.class, LoggedInUserRequest.class, UserResponse.class, LogoutRequest.class, LogoutResponse.class,
                        TagRequest.class, TagResponse.class, AllPermissionsRequest.class, AllPermissionsResponse.class,
                        CheckPermissionRequest.class, ConfirmationResponse.class };

        return new ReflectionMapper().loadRecipes( classes );
    }
}
