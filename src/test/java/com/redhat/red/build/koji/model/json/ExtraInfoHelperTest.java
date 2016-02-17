package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.redhat.red.build.koji.model.json.util.ExtraInfoHelper;
import com.redhat.red.build.koji.model.json.util.KojiObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by jdcasey on 2/16/16.
 */
public class ExtraInfoHelperTest
    extends AbstractJsonTest
{

    @Test
    public void storeAndRetrieveInExtraInfoMap()
            throws IOException
    {
        Map<String, Object> src = newExtraInfo( mainGav );
        assertThat( ExtraInfoHelper.getMavenInfo( src ), equalTo( mainGav ) );

        KojiObjectMapper mapper = new KojiObjectMapper();

        String json = mapper.writeValueAsString( src );
        System.out.println( json );

        Map<String, Object> out = mapper.readValue( json, new TypeReference<Map<String, Object>>()
        {
        } );

        assertThat( ExtraInfoHelper.getMavenInfo( out ), equalTo( ExtraInfoHelper.getMavenInfo( src ) ) );
    }
}
