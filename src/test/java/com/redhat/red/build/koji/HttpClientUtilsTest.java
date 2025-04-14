/**
 * Copyright (C) 2015 Red Hat, Inc. (jcasey@redhat.com)
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
package com.redhat.red.build.koji;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.InputStream;

import static org.apache.http.client.utils.HttpClientUtils.closeQuietly;
import static org.mockito.Mockito.never;

public class HttpClientUtilsTest
{

    /**
     * The test mocks available CloseableHttpQuietlyClient, and verify InputStream / CloseableHttpResponse close()
     * methods will never be met when closeQuietly( client ) called.
     */
    @Test
    public void testCloseQuietlyClient() throws Exception
    {
        final CloseableHttpClient client = Mockito.mock( CloseableHttpClient.class );
        final HttpPost request = Mockito.mock( HttpPost.class );
        final CloseableHttpResponse response = Mockito.mock( CloseableHttpResponse.class );
        final HttpEntity entity = Mockito.mock( HttpEntity.class );
        final InputStream inputStream = Mockito.mock( InputStream.class );

        Mockito.when( client.execute( request ) ).thenReturn( response );
        Mockito.when( response.getEntity() ).thenReturn( entity );
        Mockito.when( entity.isStreaming() ).thenReturn( Boolean.TRUE );
        Mockito.when( entity.getContent() ).thenReturn( inputStream );

        closeQuietly( client );
        Mockito.verify( inputStream, never() ).close();
        Mockito.verify( response, never() ).close();
    }

    /**
     * The test mocks available CloseableHttpResponse, and verify InputStream close() method will never be met when
     * CloseableHttpResponse close() method called.
     */
    @Test
    public void testResponseClose() throws Exception
    {
        final CloseableHttpResponse response = Mockito.mock( CloseableHttpResponse.class );
        final HttpEntity entity = Mockito.mock( HttpEntity.class );
        final InputStream inputStream = Mockito.mock( InputStream.class );

        Mockito.when( response.getEntity() ).thenReturn( entity );
        Mockito.when( entity.isStreaming() ).thenReturn( Boolean.TRUE );
        Mockito.when( entity.getContent() ).thenReturn( inputStream );

        response.close();
        Mockito.verify( inputStream, never() ).close();
        Mockito.verify( response ).close();
    }

    /**
     * The test mocks available CloseableHttpResponse, and verify InputStream / CloseableHttpResponse close()
     * methods will both be met when closeQuietly( response ) called.
     */
    @Test
    public void testCloseQuietlyResponse() throws Exception
    {
        final CloseableHttpResponse response = Mockito.mock( CloseableHttpResponse.class );
        final HttpEntity entity = Mockito.mock( HttpEntity.class );
        final InputStream inputStream = Mockito.mock( InputStream.class );

        Mockito.when( response.getEntity() ).thenReturn( entity );
        Mockito.when( entity.isStreaming() ).thenReturn( Boolean.TRUE );
        Mockito.when( entity.getContent() ).thenReturn( inputStream );

        closeQuietly( response );
        Mockito.verify( inputStream ).close();
        Mockito.verify( response ).close();
    }
}
