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

public enum KojiBuildState
{
    ALL(null),
    BUILDING(0),
    COMPLETE(1),
    DELETED(2),
    FAILED(3),
    CANCELED(4);

    private Integer value;

    private KojiBuildState( Integer value )
    {
        this.value = value;
    }

    /**
     * Gets the integer value used by koji for this state.
     * @return value
     */
    public Integer getValue()
    {
        return value;
    }

    /**
     * Gets the state by the given integer value.
     * @param value enum key
     * @return KojiBuildState object
     */
    public static KojiBuildState fromInteger( Integer value )
    {
        for ( KojiBuildState state : values() )
        {
            if ( value == null )
            {
                if ( state.getValue() == null )
                {
                    return state;
                }
            }
            else if ( value.equals( state.getValue() ) )
            {
                return state;
            }
        }
        throw new IllegalArgumentException( String.format( "Unknown KojiBuildState value: %i", value ) );
    }

}
