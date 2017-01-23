package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.maven.atlas.ident.ref.SimpleArtifactRef;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 1/23/17.
 */
public class KojiArchiveInfoTest
{

    @Test
    public void testExtensionSearch()
    {
        KojiArchiveInfo archiveInfo = new KojiArchiveInfo();
        archiveInfo.setGroupId( "org.bar" );
        archiveInfo.setArtifactId( "foo" );
        archiveInfo.setVersion( "1.0" );

        archiveInfo.setTypeExtensions( "jar war rar sar plugin" );

        archiveInfo.setFilename( "foo-1.0.jar" );

        assertThat( archiveInfo.getExtension(), equalTo( "jar" ) );

        assertThat( archiveInfo.asArtifact(),
                    equalTo( new SimpleArtifactRef( "org.bar", "foo", "1.0", "jar", null ) ) );
    }
}
