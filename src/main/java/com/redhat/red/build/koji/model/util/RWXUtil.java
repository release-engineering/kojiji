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
package com.redhat.red.build.koji.model.util;

import org.commonjava.rwx.vocab.Nil;

/**
 * Created by ruhan on 1/9/18.
 */
public final class RWXUtil
{
    private RWXUtil()
    {
        throw new UnsupportedOperationException( "This is a utility class and cannot be instantiated" );
    }


    public static boolean isBlankObj( Object xmlrpcObj )
    {
        return ( xmlrpcObj == null ) || ( xmlrpcObj instanceof Nil );
    }
}
