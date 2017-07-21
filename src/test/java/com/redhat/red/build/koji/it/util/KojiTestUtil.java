package com.redhat.red.build.koji.it.util;

import org.commonjava.util.jhttpc.HttpFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.function.Consumer;

/**
 * Created by jdcasey on 7/21/17.
 */
public final class KojiTestUtil
{
    private static File CG_ACCESS_ENABLED =
            new File( System.getProperty( "project.build.dir", "target" ), "cg-access-enabled.flag" );

    private KojiTestUtil()
    {
    }

    public static void enableCGAccess( final HttpFactory factory, Consumer<String> commander )
            throws Exception
    {
        if ( CG_ACCESS_ENABLED.exists() )
        {
            return;
        }

        try (RandomAccessFile f = new RandomAccessFile( CG_ACCESS_ENABLED, "rws" );
             FileLock lock = f.getChannel().tryLock())
        {
            if ( lock != null )
            {
                commander.accept( "koji grant-cg-access kojiadmin test-cg" );
                f.write( 1 );
            }
        }
    }
}
