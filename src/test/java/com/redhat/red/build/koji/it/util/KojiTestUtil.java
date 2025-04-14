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
