/*
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallValueObj;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;
import org.commonjava.rwx.core.Registry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MultiCallListTagsResponseTest
                extends AbstractKojiMessageTest
{
    @Test
    public void verifyVsCapturedHttp() throws Exception
    {
        MultiCallResponse parsed = parseCapturedMessage( MultiCallResponse.class, "multicall-listTags-response.xml" );

        List<KojiMultiCallValueObj> valueObjs = parsed.getValueObjs();

        assertEquals( valueObjs.size(), 3 );

        // check #1 object
        KojiMultiCallValueObj value = valueObjs.get( 0 );
        Object data = value.getData();
        assertTrue( data instanceof List );
        List<String> tags = getTags( (List) data );
        assertTrue( tags.contains( "satellite-6.1.0-rhel-7-candidate" ) );

        // check #2 object
        value = valueObjs.get( 1 );
        data = value.getData();
        assertTrue( data instanceof List );
        tags = getTags( (List) data );
        assertTrue( tags.contains( "rhel-7.0" ) );
        assertTrue( tags.contains( "rhel-7.0-mass-rebuild" ) );
        assertTrue( tags.contains( "rhel-7.0-snapshot-2-set" ) );
        assertTrue( tags.contains( "rhel-7.0-snapshot-3-set" ) );
        assertTrue( tags.contains( "rhel-7.0-snapshot-4-set" ) );

        // check #3 object
        value = valueObjs.get( 2 );
        assertTrue( ( (List) value.getData() ).isEmpty() );
    }

    private List<String> getTags( List<Object> data )
    {
        Registry registry = Registry.getInstance();

        List<KojiTagInfo> kojiTagInfoList = new ArrayList<>();
        for ( Object o : (List) data )
        {
            KojiTagInfo kojiTagInfo = registry.parseAs( o, KojiTagInfo.class );
            kojiTagInfoList.add( kojiTagInfo );
        }

        List<String> tags = new ArrayList<>(  );
        kojiTagInfoList.forEach( (tagInfo) -> {
            tags.add( tagInfo.getName() );
        } );
        return tags;
    }
}
