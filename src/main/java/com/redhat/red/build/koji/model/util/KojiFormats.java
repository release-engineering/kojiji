package com.redhat.red.build.koji.model.util;

import org.commonjava.maven.atlas.ident.ref.ProjectRef;

/**
 * Created by jdcasey on 9/14/16.
 */
public final class KojiFormats
{
    private KojiFormats(){}

    public static String toKojiName( ProjectRef ga )
    {
        return String.format( "%s-%s", ga.getGroupId(), ga.getArtifactId() );
    }

    public static String toKojiVersion( String version )
    {
        return version.replace( '-', '_' );
    }
}
