package com.redhat.red.build.koji.model.json;

import com.redhat.red.build.koji.model.json.util.KojiObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 2/16/16.
 */
public class BuildSourceTest
    extends AbstractJsonTest
{
    @Test
    public void jsonRoundTrip()
            throws IOException
    {
        BuildSource src = newBuildSource();
        KojiObjectMapper mapper = new KojiObjectMapper();

        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        BuildSource out = mapper.readValue( json, BuildSource.class );
        assertThat( out.getUrl(), equalTo( src.getUrl() ) );
        assertThat( out.getRevision(), equalTo( src.getRevision() ) );
    }

}
