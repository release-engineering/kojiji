package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 2/16/16.
 */
public class BuildRootTest
    extends AbstractJsonTest
{

    @Test
    public void jsonRoundTrip()
            throws IOException, VerificationException
    {
        BuildRoot src = newBuildRoot();
        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        BuildRoot out = mapper.readValue( json, BuildRoot.class );

        assertThat( out, equalTo( src ) );
    }

}
