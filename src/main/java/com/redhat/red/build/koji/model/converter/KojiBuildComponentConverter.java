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
package com.redhat.red.build.koji.model.converter;

import com.redhat.red.build.koji.model.json.BuildComponent;
import com.redhat.red.build.koji.model.json.FileBuildComponent;
import com.redhat.red.build.koji.model.json.RPMBuildComponent;
import org.commonjava.rwx.core.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.*;

/**
 * Created by ruhan on 8/11/17.
 */
public class KojiBuildComponentConverter
                implements Converter<BuildComponent>
{
    Logger logger = LoggerFactory.getLogger( getClass() );

    @Override
    public BuildComponent parse( Object object )
    {
        logger.warn( "Parsing not implemented for: %s", object.getClass() );
        return null;
    }

    @Override
    public Object render( BuildComponent value )
    {
        if ( value instanceof RPMBuildComponent )
        {
            RPMBuildComponent rpm = (RPMBuildComponent) value;
            Map<String, String> map = new HashMap<>();
            map.put( TYPE, RPM );
            map.put( NAME, rpm.getName() );
            map.put( VERSION, rpm.getVersion() );
            map.put( RELEASE, rpm.getRelease() );
            map.put( EPOCH, rpm.getEpoch() );
            map.put( ARCH, rpm.getArch() );
            map.put( SIGMD5, rpm.getSigmd5() );
            map.put( SIGNATURE, rpm.getSignature() );
            return map;
        }
        else if ( value instanceof FileBuildComponent )
        {
            FileBuildComponent file = (FileBuildComponent) value;
            Map<String, Object> map = new HashMap<>();
            map.put( TYPE, FILE );
            map.put( FILENAME, file.getFilename() );
            map.put( FILESIZE, file.getFilesize() );
            map.put( CHECKSUM, file.getChecksum() );
            map.put( CHECKSUM_TYPE, file.getChecksumType() );
            return map;
        }
        return null;
    }
}
