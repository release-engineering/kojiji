package com.redhat.red.build.koji;

import org.junit.Test;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * See https://gist.github.com/jehrhardt/5167854
 */
public class KeySizeTest
{
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
