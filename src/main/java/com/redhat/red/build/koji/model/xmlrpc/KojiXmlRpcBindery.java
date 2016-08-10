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

import com.redhat.red.build.koji.model.xmlrpc.messages.AckResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.AddPackageToTagRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.AllPermissionsRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.AllPermissionsResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.ApiVersionRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ApiVersionResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.CGImportRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.CheckPermissionRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ConfirmationResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.CreateTagRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetBuildByIdOrNameRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetBuildByNVRObjRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetBuildResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetPackageIdRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetTagIdRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetTaskRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetTaskResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.IdResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListArchivesRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListArchivesResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListBuildsRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.BuildListResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListPackagesRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListPackagesResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListTaggedRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListTagsRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListTagsResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoggedInUserRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoginRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoginResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.LogoutRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.RemovePackageFromTagRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.StatusResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.TagBuildRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.TagRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.TagResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.UntagBuildRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.UploadResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.UserRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.UserResponse;
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
        /* @formatter:off */
        Class<?>[] classes = {
                AckResponse.class,
                AddPackageToTagRequest.class,
                AllPermissionsRequest.class,
                AllPermissionsResponse.class,
                ApiVersionRequest.class,
                ApiVersionResponse.class,
                BuildListResponse.class,
                CGImportRequest.class,
                CheckPermissionRequest.class,
                ConfirmationResponse.class,
                CreateTagRequest.class,
                GetBuildByIdOrNameRequest.class,
                GetBuildByNVRObjRequest.class,
                GetBuildResponse.class,
                GetPackageIdRequest.class,
                GetTagIdRequest.class,
                GetTaskRequest.class,
                GetTaskResponse.class,
                IdResponse.class,
                ListArchivesRequest.class,
                ListArchivesResponse.class,
                ListBuildsRequest.class,
                ListPackagesRequest.class,
                ListPackagesResponse.class,
                ListTaggedRequest.class,
                ListTagsRequest.class,
                ListTagsResponse.class,
                LoggedInUserRequest.class,
                LoginRequest.class,
                LoginResponse.class,
                LogoutRequest.class,
                RemovePackageFromTagRequest.class,
                StatusResponse.class,
                TagBuildRequest.class,
                TagRequest.class,
                TagResponse.class,
                UploadResponse.class,
                UntagBuildRequest.class,
                UserRequest.class,
                UserResponse.class
        };
        /* @formatter:on */

        return new ReflectionMapper().loadRecipes( classes );
    }
}
