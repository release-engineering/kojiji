/**
 * Copyright (C) 2010 Red Hat, Inc. (jdcasey@commonjava.org)
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
package com.redhat.red.build.koji.http;

import org.commonjava.rwx.error.XmlRpcException;

import java.io.Closeable;

public interface SyncObjectClient
        extends Closeable
{

    <T> T call( Object request, Class<T> responseType )
            throws XmlRpcException;

    <T> T call( Object request, Class<T> responseType, UrlBuilder urlBuilder, RequestModifier requestModifier )
            throws XmlRpcException;

}
