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
package com.redhat.red.build.koji;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
        try ( Stream<Path> stream = Files.walk( basePath ).filter( Files::isRegularFile ) )
        {
            stream.forEach( ( path ) -> System.out.println( basePath.relativize( path ) ) );
        }
    }

}
