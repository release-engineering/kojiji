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

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public final class StringUtils
{
    private StringUtils(){}

    public static String join( Iterable<?> elements, String delimiter )
    {
        if ( elements == null )
        {
            return null;
        }

        StringJoiner joiner = new StringJoiner( Objects.toString( delimiter, "" ) );
        elements.forEach(element -> joiner.add( Objects.toString( element, "" ) ) );
        return joiner.toString();
    }

    public static String join( Object[] elements, String delimiter )
    {
        return elements != null ? join( Arrays.asList( elements ), delimiter ) : null;
    }
}
