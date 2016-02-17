package com.redhat.red.build.koji.model.json;

import com.redhat.red.build.koji.model.json.util.KojiObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 2/16/16.
 */
public class BuildDescriptionTest
    extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip()
            throws IOException, VerificationException
    {
        BuildDescription src = newBuildDescription();

        KojiObjectMapper mapper = new KojiObjectMapper();

        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        BuildDescription out = mapper.readValue( json, BuildDescription.class );

        assertThat( out.getName(), equalTo( src.getName() ) );
        assertThat( out.getVersion(), equalTo( src.getVersion() ) );
        assertThat( out.getRelease(), equalTo( src.getRelease() ) );
        assertThat( out.getStartTime(), equalTo( src.getStartTime() ) );
        assertThat( out.getEndTime(), equalTo( src.getEndTime() ) );
        assertThat( out.getBuildType(), equalTo( src.getBuildType() ) );
        assertThat( out.getSource(), equalTo( src.getSource() ) );
        assertThat( out.getExtraInfo(), equalTo( src.getExtraInfo() ) );
    }
}
