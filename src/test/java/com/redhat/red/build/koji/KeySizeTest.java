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
package com.redhat.red.build.koji;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * See https://gist.github.com/jehrhardt/5167854
 */
public class KeySizeTest
{
    @Before
    public void testAssumptions()
    {
        Assume.assumeTrue( System.getProperty("java.runtime.name").contains("OpenJDK") );
    }

    @Test
    public void aesKeySize()
    {
        int allowedKeyLength = 0;

        try
        {
            allowedKeyLength = Cipher.getMaxAllowedKeyLength( "AES" );
        }
        catch ( NoSuchAlgorithmException e )
        {
            e.printStackTrace();
        }

        assertThat( "You can only test this project with unlimited strength ciphers.", allowedKeyLength >= 2147483647, equalTo( true ) );
    }

    @Test
    public void rsaKeySize()
    {
        int allowedKeyLength = 0;

        try
        {
            allowedKeyLength = Cipher.getMaxAllowedKeyLength( "RSA" );
        }
        catch ( NoSuchAlgorithmException e )
        {
            e.printStackTrace();
        }

        assertThat( "You can only test this project with unlimited strength ciphers.", allowedKeyLength >= 2147483647, equalTo( true ) );
    }
}
