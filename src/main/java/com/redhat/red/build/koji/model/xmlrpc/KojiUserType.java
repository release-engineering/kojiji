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

public enum KojiUserType
{
    NORMAL(0),
    HOST(1),
    GROUP(2);

    private Integer value;

    private KojiUserType( Integer value )
    {
        this.value = value;
    }

    public Integer getValue()
    {
        return value;
    }

    public static KojiUserType fromInteger( Integer value )
    {
        for ( KojiUserType type : values() )
        {
            if ( value == type.getValue() )
            {
                return type;
            }
        }

        throw new IllegalArgumentException( String.format( "Unknown KojiUserType value: %d", value ) );
    }

}
