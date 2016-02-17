package com.redhat.red.build.koji.model.json;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 2/16/16.
 */
public class BuildToolTest
    extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip()
            throws IOException
    {
        BuildTool src = newBuildTool( "test-tool", "1.0" );
        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        BuildTool out = mapper.readValue( json, BuildTool.class );
        assertThat( out, equalTo( src ) );
    }
}
