package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 2/17/16.
 */
public class BuildOutputTest
    extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip_MavenFile()
            throws VerificationException, IOException
    {
        BuildOutput src = newBuildOutput( 1001, "foo.txt" );

        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        BuildOutput out = mapper.readValue( json, BuildOutput.class );

        assertThat( out, equalTo( src ) );
    }

    @Test
    public void jsonRoundTrip_LogFile()
            throws VerificationException, IOException
    {
        BuildOutput src = newLogOutput( 1001, "build.log" );

        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        BuildOutput out = mapper.readValue( json, BuildOutput.class );

        assertThat( out, equalTo( src ) );
    }

}
