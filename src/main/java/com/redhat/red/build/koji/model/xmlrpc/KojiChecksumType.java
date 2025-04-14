/*
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

public enum KojiChecksumType
{
    md5    ( 0, "MD5"),
    sha1   ( 1, "SHA-1" ),
    sha256 ( 2, "SHA-256" );

    private final Integer value;

    private final String algorithm;

    private KojiChecksumType( int value, String algorithm )
    {
        this.value = value;
        this.algorithm = algorithm;
    }

    public Integer getValue()
    {
        return value;
    }

    public String getAlgorithm()
    {
        return algorithm;
    }

    public static KojiChecksumType fromInteger( int value )
    {
        for ( KojiChecksumType checksum : values() )
        {
            if ( value == checksum.getValue() )
            {
                return checksum;
            }
        }

        throw new IllegalArgumentException( String.format( "Unknown KojiChecksumType value: %d", value ) );
    }
}
