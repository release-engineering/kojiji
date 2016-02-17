package com.redhat.red.build.koji.model.json.util;

import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Created by jdcasey on 2/16/16.
 */
public class ExtraInfoHelper
{
    public static final String GROUP_ID = "group_id";

    public static final String ARTIFACT_ID = "artifact_id";

    public static final String VERSION = "version";

    public static final String MAVEN_INFO = "maven";

    public static ProjectVersionRef getMavenInfo( Map<String, Object> extraInfo )
    {
        Logger logger = LoggerFactory.getLogger( ExtraInfoHelper.class );
        Map<String, String> gav = (Map<String, String>) extraInfo.get( MAVEN_INFO );
        if ( gav != null )
        {
            String g = gav.get( GROUP_ID );
            String a = gav.get( ARTIFACT_ID );
            String v = gav.get( VERSION );
            if ( isNotEmpty( g ) && isNotEmpty( a ) && isNotEmpty( v ) )
            {
                return new SimpleProjectVersionRef( g, a, v );
            }
            else
            {
                logger.debug(
                        "Cannot pull Maven GAV out of extra info map. Got values: g={}, a={}, v={} (full extra-info: {})",
                        g, a, v, extraInfo );
            }
        }

        logger.debug( "Cannot find Maven key '{}' in extra info map. (full extra-info: {})", MAVEN_INFO, extraInfo );

        return null;
    }

    public static void addMavenInfo( ProjectVersionRef ref, Map<String, Object> extraInfo )
    {
        Map<String, String> gav = new HashMap<>();
        gav.put( GROUP_ID, ref.getGroupId() );
        gav.put( ARTIFACT_ID, ref.getArtifactId() );
        gav.put( VERSION, ref.getVersionString() );

        extraInfo.put( MAVEN_INFO, gav );
    }
}
