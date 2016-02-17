package com.redhat.red.build.koji;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by jdcasey on 2/8/16.
 */
public class MiscProofs
{
    @Test
    @Ignore
    public void filetreeWalk()
            throws IOException
    {
        Path basePath = Paths.get( "/home/jdcasey/.config/hexchat" );
        Files.walk( basePath ).filter( Files::isRegularFile).forEach( ( path)->{
            System.out.println(basePath.relativize( path ));
        });
    }

}
