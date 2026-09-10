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
package com.redhat.red.build.koji.model.xmlrpc;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class KojiIdOrNameTest
{
    private static final String NAME = "foo-candidate";

    private static final Integer ID = 1;

    @Test
    public void testGetForValid()
    {
        KojiIdOrName name = KojiIdOrName.getFor( NAME );
        assertThat( name.getName(), equalTo( NAME ) );
        assertThat( name.getId(), nullValue() );

        KojiIdOrName looksLikeId = KojiIdOrName.getFor( "12345" );
        assertThat( looksLikeId.getName(), equalTo( "12345" ) );
        assertThat( looksLikeId.getId(), nullValue() );


        KojiIdOrName id = KojiIdOrName.getFor( ID );
        assertThat( id.getId(), equalTo( ID ) );
        assertThat( id.getName(), nullValue() );


        Map<String, String> nameMap = Map.of( "name", NAME );
        assertThat( KojiIdOrName.getFor( nameMap ).getName(), equalTo( NAME ) );
        Map<String, Integer> idMap = Map.of("id", ID );
        assertThat( KojiIdOrName.getFor( idMap ).getId(), equalTo( ID ) );

        Map<String, Object> idNameMap = Map.of( "id", ID, "name", NAME );
        KojiIdOrName idOrName = KojiIdOrName.getFor( idNameMap );
        assertThat( idOrName.getId(), equalTo( ID ) );
        assertThat( idOrName.getName(), nullValue() );

    }

    @Test
    public void testGetForInvalid()
    {
        assertThat( KojiIdOrName.getFor( null ), nullValue() );
        assertThat( KojiIdOrName.getFor( List.of() ), nullValue() );
        assertThat( KojiIdOrName.getFor( Map.of() ), nullValue() );

        String stringId = String.valueOf( ID );
        assertThat( KojiIdOrName.getFor( Map.of( "id", stringId ) ), nullValue() );
        assertThat( KojiIdOrName.getFor( Map.of( "id", ID.doubleValue() ) ), nullValue() );
        assertThat( KojiIdOrName.getFor( Map.of( "id", stringId, "name", NAME ) ), nullValue() );
        assertThat( KojiIdOrName.getFor( Map.of( "id", stringId, "name", ID ) ), nullValue() );
    }
}
