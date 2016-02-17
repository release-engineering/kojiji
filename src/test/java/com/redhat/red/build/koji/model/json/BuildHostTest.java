package com.redhat.red.build.koji.model.json;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 2/16/16.
 */
public class BuildHostTest
    extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip()
            throws IOException
    {
        BuildHost src = newBuildHost();

        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        BuildHost out = mapper.readValue( json, BuildHost.class );

        assertThat( out, equalTo( src ) );
    }
}
