package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class ImportInfoTest
        extends AbstractJsonTest
{

    @Test
    public void jsonRoundTrip()
            throws VerificationException, IOException
    {
        ImportInfo info = new ImportInfo( KojiJsonConstants.DEFAULT_METADATA_VERSION, newBuildDescription(),
                                          Collections.singleton( newBuildRoot() ),
                                          Arrays.asList( newBuildOutput( 1001, "foo-1.jar" ),
                                                         newLogOutput( 1001, "build.log" ) )
                                                .stream()
                                                .collect( Collectors.toSet() ) );

        String json = mapper.writeValueAsString( info );
        System.out.println( json );

        ImportInfo out = mapper.readValue( json, ImportInfo.class );

        assertThat( out.getBuild(), equalTo( info.getBuild() ) );
    }
}
