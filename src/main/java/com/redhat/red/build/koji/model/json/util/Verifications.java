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
package com.redhat.red.build.koji.model.json.util;

import java.util.Set;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by jdcasey on 2/16/16.
 */
public final class Verifications
{

    public static void checkString( String value, Set<String> missing, String format, Object... params )
    {
        if ( isEmpty( value ) )
        {
            missing.add( String.format( format, params ) );
        }
    }

    public static void checkNull( Object value, Set<String> missing, String format, Object... params )
    {
        if ( value == null )
        {
            missing.add( String.format( format, params ) );
        }
    }

    private Verifications(){}
}
