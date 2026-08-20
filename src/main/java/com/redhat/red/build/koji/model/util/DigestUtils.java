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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class DigestUtils
{
    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private DigestUtils(){}

    public static String md5Hex( byte[] data )
    {
        try
        {
            byte[] digest = MessageDigest.getInstance( "MD5" ).digest( data );
            char[] out = new char[digest.length * 2];

            for ( int i = 0, j = 0; i < digest.length; i++ )
            {
                out[j++] = DIGITS[( 0xF0 & digest[i] ) >>> 4];
                out[j++] = DIGITS[0x0F & digest[i]];
            }

            return new String( out );
        }
        catch ( NoSuchAlgorithmException e )
        {
            throw new IllegalArgumentException( e );
        }
    }
}
