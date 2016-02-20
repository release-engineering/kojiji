package com.redhat.red.build.koji.model.json;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 2/17/16.
 */
public class KojiImportTest
        extends AbstractJsonTest
{

    @Test
    public void jsonRoundTrip()
            throws VerificationException, IOException
    {
        KojiImport info = new KojiImport( KojiJsonConstants.DEFAULT_METADATA_VERSION, newBuildDescription(),
                                          Collections.singleton( newBuildRoot() ),
                                          Arrays.asList( newBuildOutput( 1001, "foo-1.jar" ),
                                                         newLogOutput( 1001, "build.log" ) )
                                                .stream()
                                                .collect( Collectors.toSet() ) );

        String json = mapper.writeValueAsString( info );
        System.out.println( json );

        KojiImport out = mapper.readValue( json, KojiImport.class );

        assertThat( out.getBuild(), equalTo( info.getBuild() ) );
    }
}
