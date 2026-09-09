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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static <T> T getObj( Object xmlrpcObj, Class<T> type )
    {
        return !type.isInstance( xmlrpcObj ) ? null : type.cast( xmlrpcObj );
    }

    public static <T> T getObjFromList( List<?> list, int index, Class<T> type )
    {
        return ( list == null || index < 0 || list.size() <= index ) ? null : getObj( list.get( index ), type );
    }

    public static <T> T getObjFromMap( Map<?, ?> map, String key, Class<T> type )
    {
        return map == null ? null : getObj( map.get( key ), type );
    }

    public static boolean toBoolean( Object xmlrpcObj )
    {
        return Boolean.TRUE.equals( xmlrpcObj );
    }

    @SuppressWarnings( "unchecked" )
    public static List<String> toStringList( Object xmlrpcObj )
    {
        return !( xmlrpcObj instanceof List<?> ) ? null : (List<String>) xmlrpcObj;
    }

    public static Map<String, String> toStringMap( Object xmlrpcObj )
    {
        return !( xmlrpcObj instanceof Map<?, ?> ) ? null : ( (Map<?, ?>) xmlrpcObj ).entrySet().stream().filter( et -> !isBlankObj( et.getValue() ) ).collect( Collectors.toMap( et -> String.valueOf( et.getKey() ), et -> (String) et.getValue() ) );
    }
}
