package com.redhat.red.build.koji.testutil;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jdcasey on 9/15/16.
 */
public final class TestResourceUtils
{
    private TestResourceUtils(){}

    public static byte[] readTestResourceBytes( String resource )
            throws IOException
    {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream( resource ))
        {
            return IOUtils.toByteArray( in );
        }
    }

    public static String readTestResourceString( String resource )
            throws IOException
    {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream( resource ))
        {
            return IOUtils.toString( in );
        }
    }

}
