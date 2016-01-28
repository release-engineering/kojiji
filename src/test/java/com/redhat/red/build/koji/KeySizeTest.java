package com.redhat.red.build.koji;

import org.junit.Test;

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;

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

        System.out.println( "The allowed key length for AES is: " + allowedKeyLength );
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

        System.out.println( "The allowed key length for RSA is: " + allowedKeyLength );
    }
}
