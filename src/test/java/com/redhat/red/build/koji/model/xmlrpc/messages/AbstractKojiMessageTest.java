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

import com.redhat.red.build.koji.model.generated.Model_Registry;
import org.apache.commons.io.IOUtils;
import org.commonjava.rwx.api.RWXMapper;
import org.commonjava.rwx.core.Registry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by jdcasey on 12/3/15.
 */
public class AbstractKojiMessageTest
{
    protected static final String MESSAGES_BASE = "messages/";

    protected static RWXMapper rwxMapper;

    @BeforeClass
    public static void setupClass() throws Exception
    {
        Registry.setInstance( new Model_Registry() ); // Register RWX Parser/Renderers
        rwxMapper = new RWXMapper();
    }

    @Before
    public void setup() throws Exception
    {
    }

    protected String readResource( String resourceFile ) throws Exception
    {
        String resource = MESSAGES_BASE + resourceFile;
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream( resource ))
        {
            if ( is == null )
            {
                Assert.fail( "Cannot find message XML file on classpath: " + resource );
            }

            return IOUtils.toString( is, StandardCharsets.UTF_8 );
        }
    }

    /**
     * @return parsed object. Caller need to assert its field values.
     */
    protected <T> T parseCapturedMessage( Class<T> type, String filename ) throws Exception
    {
        String source = readResource( filename );
        T parsed = rwxMapper.parse( new ByteArrayInputStream( source.getBytes() ), type );
        assertNotNull( parsed );
        return parsed;
    }

    /**
     * Parse the input file and get parsed object, then render it and compare with source file.
     * This is for simple req/resp. Does not work for complex ones since the rendered xml may virtually equal to source,
     * but may not literally equal to it.
     */
    protected <T> void verifyVsCapturedMessage( Class<T> type, String filename ) throws Exception
    {
        Object parsed = parseCapturedMessage( type, filename );
        assertEquals( getFormalizeXMLStringFromFile( filename ), formalizeXMLString( rwxMapper.render( parsed ) ) );
    }

    /**
     * Parse the input file and get parsed object, then use equals() to compare it with expected object
     */
    protected <T> void verifyVsCapturedMessage( Class<T> type, String filename, Object expected ) throws Exception
    {
        Object parsed = parseCapturedMessage( type, filename );
        assertEquals( expected, parsed );
    }

    /**
     * Create new instance by default constructor, render it to string and parse it again, return the rounded object.
     * @return rounded object.
     */
    protected <T> void roundTrip( Class<T> type ) throws Exception
    {
        roundTrip( type, type.getDeclaredConstructor().newInstance() );
    }

    /**
     * Render target instance to string and parse it again, return the rounded object.
     * @return rounded object.
     */
    protected <T> T roundTrip( Class<T> type, Object instance ) throws Exception
    {
        String rendered = rwxMapper.render( instance );
        T parsed = rwxMapper.parse( new ByteArrayInputStream( rendered.getBytes() ), type );
        assertNotNull( parsed );
        return parsed;
    }

    protected InputStream getXMLStream( final String name ) throws IOException, XMLStreamException
    {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream( MESSAGES_BASE + name );
    }

    protected String getFormalizeXMLStringFromFile( final String filename ) throws IOException, XMLStreamException
    {
        final BufferedReader reader = new BufferedReader( new InputStreamReader( getXMLStream( filename ) ) );
        final StringWriter writer = new StringWriter();
        final PrintWriter pWriter = new PrintWriter( writer );

        String line = null;
        while ( ( line = reader.readLine() ) != null )
        {
            pWriter.print( line.trim() );
        }

        return formalizeXMLString( writer.toString().trim() );
    }

    protected String formalizeXMLString( String xml )
    {
        xml = xml.replaceFirst( "<\\?.*\\?>", "<?xml version=\"1.0\"?>" );
        xml = xml.replaceAll( "<nil/>", "<nil></nil>" );
        return xml;
    }

    /*// the members in struct is not ordered. we have to make it ordered to compare
    protected String splitAndSort( String xml )
    {
        String MEMBER = "<member>";
        String MEMBER_END = "</member>";

        StringBuilder sb = new StringBuilder();

        List<String> membersList = new ArrayList<>();

        String newSource = xml.replaceAll( MEMBER, "\n" + MEMBER );
        String[] lines = newSource.split( "\n" );
        for ( String line : lines )
        {
            if ( line.startsWith( MEMBER ) )
            {
                if ( line.endsWith( MEMBER_END ) )
                {
                    membersList.add( line );
                }
                else
                {
                    // get the last member in a struct
                    int idx = line.indexOf( MEMBER_END );
                    String m = line.substring( 0, idx + MEMBER_END.length() );
                    membersList.add( m );
                    String[] array = membersList.toArray( new String[0] );
                    Arrays.sort( array );
                    for ( String s : array )
                    {
                        sb.append( s + "\n" );
                    }
                    membersList = new ArrayList<>();
                    String following = line.substring( idx + MEMBER_END.length() ); // what is following </member>...
                    sb.append( following + "\n" );
                }
            }
            else
            {
                sb.append( line + "\n" );
            }
        }

        return sb.toString();
    }*/

}
