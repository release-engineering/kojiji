package com.redhat.red.build.koji.model.json;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 2/16/16.
 */
public class BuildContainerTest
    extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip()
            throws IOException
    {
        BuildContainer src = newBuildContainer( "docker", "x86_64");

        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        BuildContainer out = mapper.readValue( json, BuildContainer.class );

        assertThat( out, equalTo( src ) );
    }
}
