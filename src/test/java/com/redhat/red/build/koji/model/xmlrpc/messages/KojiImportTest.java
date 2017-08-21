package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.json.BuildContainer;
import com.redhat.red.build.koji.model.json.KojiImport;
import com.redhat.red.build.koji.model.json.StandardArchitecture;
import com.redhat.red.build.koji.model.json.StandardBuildType;
import com.redhat.red.build.koji.model.json.StandardChecksum;
import org.apache.commons.codec.digest.DigestUtils;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.commonjava.rwx.api.RWXMapper;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.redhat.red.build.koji.testutil.TestResourceUtils.readTestResourceBytes;
import static org.junit.Assert.assertEquals;

/**
 * Created by ruhan on 8/18/17.
 */
public class KojiImportTest extends AbstractKojiMessageTest
{
    @Test
    public void xmlRpcRender()
                    throws Exception
    {
        ProjectVersionRef gav = new SimpleProjectVersionRef( "org.foo", "bar", "1.1" );

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss Z" );

        Date start = sdf.parse( "2017-08-01 12:23:00 UTC" );
        Date end = sdf.parse( "2017-08-01 12:38:00 UTC" );

        KojiImport.Builder importBuilder = new KojiImport.Builder();

        int brId = 1;
        String pomPath = "org/foo/bar/1.1/bar-1.1.pom";
        byte[] pomBytes = readTestResourceBytes( "import-data/" + pomPath );

        KojiImport importMetadata = importBuilder.withNewBuildDescription( gav )
                                                 .withStartTime( start )
                                                 .withEndTime( end )
                                                 .withBuildSource( "http://builder.foo.com", "1.0" )
                                                 .parent()
                                                 .withNewBuildRoot( brId )
                                                 .withContentGenerator( "test-cg", "1.0" )
                                                 .withContainer( new BuildContainer( StandardBuildType.maven.name(),
                                                                                     StandardArchitecture.noarch.name() ) )
                                                 .withHost( "linux", StandardArchitecture.x86_64 )
                                                 .parent()
                                                 .withNewOutput( 1, new File( pomPath ).getName() )
                                                 .withFileSize( pomBytes.length )
                                                 .withChecksum( StandardChecksum.md5.name(), DigestUtils.md5Hex( pomBytes ) )
                                                 .withOutputType( "pom" )
                                                 .parent()
                                                 .build();

        String rendered = new RWXMapper().render( importMetadata );
        assertEquals( getFormalizeXMLStringFromFile( "struct-KojiImport.xml" ), formalizeXMLString( rendered ) );
    }
}
