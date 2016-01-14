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

import com.redhat.red.build.koji.config.KojiConfig;
import com.redhat.red.build.koji.model.KojiSessionInfo;
import com.redhat.red.build.koji.model.KojiUserInfo;
import com.redhat.red.build.koji.model.messages.ApiVersionRequest;
import com.redhat.red.build.koji.model.messages.LoggedInUserRequest;
import com.redhat.red.build.koji.model.messages.LoginRequest;
import com.redhat.red.build.koji.model.messages.LoginResponse;
import com.redhat.red.build.koji.model.messages.LogoutRequest;
import com.redhat.red.build.koji.model.messages.LogoutResponse;
import com.redhat.red.build.koji.model.messages.UserRequest;
import com.redhat.red.build.koji.model.messages.UserResponse;
import com.redhat.red.build.koji.model.messages.ApiVersionResponse;
import org.commonjava.rwx.error.XmlRpcException;
import org.commonjava.rwx.http.RequestModifier;
import org.commonjava.rwx.http.UrlBuilder;
import org.commonjava.rwx.http.httpclient4.HC4SyncObjectClient;
import org.commonjava.util.jhttpc.HttpFactory;
import org.commonjava.util.jhttpc.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jdcasey on 12/3/15.
 */
public class KojiClient
        implements Closeable
{
    private static final String SSL_LOGIN_PATH = "ssllogin";

    private static final String ACCEPT_ENCODING_HEADER = "Accept-Encoding";

    private static final String IDENTITY_ENCODING_VALUE = "identity";

    private static final String SESSION_ID_PARAM = "session-id";

    private static final String SESSION_KEY_PARAM = "session-key";

    private static final String CALL_NUMBER_PARAM = "callnum";

    private HC4SyncObjectClient xmlrpcClient;

    private HttpFactory httpFactory;

    private KojiConfig config;

    private KojiBindery bindery;

    private AtomicInteger callCount = new AtomicInteger( 0 );

    private static final RequestModifier STANDARD_REQUEST_MODIFIER = (request)->{
        request.setHeader( ACCEPT_ENCODING_HEADER, IDENTITY_ENCODING_VALUE );
        Logger logger = LoggerFactory.getLogger( KojiClient.class );
        logger.debug( "\n\n\n\nTarget URI: {}\n\n\n\n", request.getURI() );
    };

    private static final UrlBuilder NO_OP_URL_BUILDER = (url) -> url;

    private UrlBuilder sessionUrlBuilder( KojiSessionInfo session )
    {
        return (url)-> {
            if ( session == null )
            {
                return url;
            }

            Map<String, String> params = new HashMap<>();
            params.put( SESSION_ID_PARAM, Integer.toString( session.getSessionId() ) );
            params.put( SESSION_KEY_PARAM, session.getSessionKey() );
            params.put( CALL_NUMBER_PARAM, Integer.toString( callCount.getAndIncrement() ) );

            String result = UrlUtils.buildUrl( url, params );

            Logger logger = LoggerFactory.getLogger( KojiClient.class );
            logger.debug( "\n\n\n\nBuild URL: {}\n\n\n\n", result );
            return result;
        };
    }

    public KojiClient( KojiConfig config, KojiBindery bindery, HttpFactory httpFactory )
    {
        this.config = config;
        this.bindery = bindery;
        this.httpFactory = httpFactory;
        setup();
    }

    public synchronized void close()
    {
        if ( xmlrpcClient != null )
        {
            xmlrpcClient.close();
            xmlrpcClient = null;
        }
    }

    public void setup()
    {
        Logger logger = LoggerFactory.getLogger( getClass() );
        logger.debug( "SETUP: Starting KojiClient for: " + config.getKojiURL() );
        try
        {
            xmlrpcClient = new HC4SyncObjectClient( httpFactory, bindery, config.getKojiSiteConfig() );
        }
        catch ( IOException e )
        {
            logger.error( "Cannot construct koji HTTP site-config: " + e.getMessage(), e );
            xmlrpcClient.close();
            xmlrpcClient = null;
        }

        if ( xmlrpcClient != null )
        {
            try
            {
                ApiVersionResponse response =
                        xmlrpcClient.call( new ApiVersionRequest(), ApiVersionResponse.class, NO_OP_URL_BUILDER,
                                           STANDARD_REQUEST_MODIFIER );

                if ( 1 != response.getApiVersion() )
                {
                    logger.error( "Cannot connect to koji at: " + config.getKojiURL() + ". API Version reported is '"
                                          + response.getApiVersion() + "' but this client only supports version 1." );
                    xmlrpcClient.close();
                    xmlrpcClient = null;
                }
            }
            catch ( XmlRpcException e )
            {
                logger.error( "Cannot retrieve koji API version from: " + config.getKojiURL() + ". (Reason: " + e.getMessage() + ")", e );
                xmlrpcClient.close();
                xmlrpcClient = null;
            }
        }
    }

    public KojiSessionInfo login()
            throws KojiClientException
    {
        checkConnection();

        try
        {
            LoginResponse loginResponse = xmlrpcClient.call( new LoginRequest(), LoginResponse.class, ( url ) -> {
                return UrlUtils.buildUrl( url, SSL_LOGIN_PATH );
            }, ( request ) -> request.setHeader( ACCEPT_ENCODING_HEADER, IDENTITY_ENCODING_VALUE ) );

            return loginResponse == null ? null : loginResponse.getSessionInfo();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to login: %s", e, e.getMessage() );
        }
    }

    public KojiUserInfo getUserInfo( String username )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            UserResponse response = xmlrpcClient.call( new UserRequest( username ), UserResponse.class,
                                                       NO_OP_URL_BUILDER, STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getUserInfo();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve current user info: %s", e, e.getMessage() );
        }
    }

    public KojiUserInfo getUserInfo( KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            UserResponse response = xmlrpcClient.call( new LoggedInUserRequest(), UserResponse.class,
                                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getUserInfo();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve current user info: %s", e, e.getMessage() );
        }
    }

    public void logout( KojiSessionInfo session )
            throws KojiClientException
    {
        if ( xmlrpcClient != null )
        {
            try
            {
                LogoutResponse response = xmlrpcClient.call( new LogoutRequest(), LogoutResponse.class,
                                                             sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );
            }
            catch ( XmlRpcException e )
            {
                throw new KojiClientException( "Failed to logout: %s", e, e.getMessage() );
            }
        }
    }

    private void checkConnection()
            throws KojiClientException
    {
        if ( xmlrpcClient == null )
        {
            throw new KojiClientException( "Connection to koji at %s is closed. Perhaps it failed to initialize?", config.getKojiURL() );
        }
    }

}
