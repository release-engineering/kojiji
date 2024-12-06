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
import com.redhat.red.build.koji.kerberos.KrbAuthenticator;
import com.redhat.red.build.koji.model.ImportFile;
import com.redhat.red.build.koji.model.KojiImportResult;
import com.redhat.red.build.koji.model.generated.Model_Registry;
import com.redhat.red.build.koji.model.json.KojiImport;
import com.redhat.red.build.koji.model.json.util.KojiObjectMapper;
import com.redhat.red.build.koji.model.xmlrpc.*;
import com.redhat.red.build.koji.model.xmlrpc.messages.*;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.commonjava.atlas.maven.ident.ref.ProjectRef;
import org.commonjava.atlas.maven.ident.ref.ProjectVersionRef;
import org.commonjava.o11yphant.metrics.api.MetricRegistry;
import org.commonjava.rwx.api.RWXMapper;
import org.commonjava.rwx.core.Registry;
import org.commonjava.rwx.error.XmlRpcException;
import com.redhat.red.build.koji.http.RequestModifier;
import com.redhat.red.build.koji.http.UrlBuildResult;
import com.redhat.red.build.koji.http.UrlBuilder;
import com.redhat.red.build.koji.http.httpclient4.HC4SyncObjectClient;
import org.commonjava.util.jhttpc.HttpFactory;
import org.commonjava.util.jhttpc.JHttpCException;
import org.commonjava.util.jhttpc.auth.PasswordManager;
import org.commonjava.util.jhttpc.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.security.auth.DestroyFailedException;

import static com.redhat.red.build.koji.KojiClientUtils.buildMultiCallRequest;
import static com.redhat.red.build.koji.KojiClientUtils.parseMultiCallResponse;
import static com.redhat.red.build.koji.model.util.KojiFormats.toKojiName;
import static com.redhat.red.build.koji.model.xmlrpc.KojiBuildTypeInfo.addBuildTypeInfo;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.*;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.GET_BUILD;
import static com.redhat.red.build.koji.model.xmlrpc.messages.MultiCallRequest.getBuilder;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.apache.http.client.utils.HttpClientUtils.closeQuietly;

/**
 * Created by jdcasey on 12/3/15.
 */
public class KojiClient
        implements Closeable
{
    static Logger logger = LoggerFactory.getLogger( KojiClient.class );

    private HC4SyncObjectClient xmlrpcClient;

    private HttpFactory httpFactory;

    private ExecutorService executorService;

    private MetricRegistry metricRegistry;

    private KojiObjectMapper objectMapper;

    private KojiConfig config;

    public KojiConfig getConfig()
    {
        return config;
    }

    private AtomicInteger callCount = new AtomicInteger( 0 );

    private static final RequestModifier STANDARD_REQUEST_MODIFIER = ( request ) -> {
        request.setHeader( ACCEPT_ENCODING_HEADER, IDENTITY_ENCODING_VALUE );
        logger.debug( "\n\n\n\nTarget URI: {}\n\n\n\n", request.getURI() );
    };

    private static final UrlBuilder NO_OP_URL_BUILDER = ( url ) -> new UrlBuildResult( url );

    private UrlBuilder sessionUrlBuilder( KojiSessionInfo session )
    {
        return sessionUrlBuilder( session, null );
    }

    private UrlBuilder sessionUrlBuilder( KojiSessionInfo session, Supplier<Map<String, Object>> paramEditor )
    {
        return ( url ) -> {
            if ( session == null )
            {
                return new UrlBuildResult( url );
            }

            Map<String, String> params = new HashMap<>();
            params.put( SESSION_ID_PARAM, Integer.toString( session.getSessionId() ) );
            params.put( SESSION_KEY_PARAM, session.getSessionKey() );
            params.put( CALL_NUMBER_PARAM, Integer.toString( callCount.getAndIncrement() ) );

            if ( paramEditor != null )
            {
                Map<String, Object> extraParams = paramEditor.get();
                if ( extraParams != null )
                {
                    MalformedURLException error = (MalformedURLException) extraParams.get( EMBEDDED_ERROR_PARAM );
                    if ( error != null )
                    {
                        return new UrlBuildResult( error );
                    }
                    else
                    {
                        extraParams.forEach( ( key, value ) -> {
                            params.put( key, String.valueOf( value ) );
                        } );
                    }
                }
            }

            String result = UrlUtils.buildUrl( url, params );

            logger.debug( "\n\n\n\nBuild URL: {}\n\n\n\n", result );
            return new UrlBuildResult( result );
        };
    }

    public KojiClient( KojiConfig config, PasswordManager passwordManager, ExecutorService executorService,
                       MetricRegistry metricRegistry ) throws KojiClientException
    {
        this.config = config;
        this.httpFactory = new HttpFactory( passwordManager );
        this.executorService = executorService;
        this.metricRegistry = metricRegistry;
        setup();
    }

    public KojiClient( KojiConfig config, PasswordManager passwordManager, ExecutorService executorService )
                    throws KojiClientException
    {
        this( config, passwordManager, executorService, null );
    }

    @Override
    public synchronized void close()
    {
        if ( xmlrpcClient != null )
        {
            xmlrpcClient.close();
            xmlrpcClient = null;
        }
    }

    static
    {
        Registry.setInstance( new Model_Registry() ); // Register RWX Parser/Renderers
    }

    public void setup()
                    throws KojiClientException
    {
        objectMapper = new KojiObjectMapper();

        logger.debug( "SETUP: Starting KojiClient for: {}", config.getKojiURL() );
        try
        {
            xmlrpcClient = new HC4SyncObjectClient( httpFactory, config.getKojiSiteConfig(), metricRegistry );
        }
        catch ( IOException e )
        {
            xmlrpcClient.close();
            xmlrpcClient = null;
            throw new KojiClientException("Cannot construct koji HTTP site-config: " + e.getMessage(), e);
        }

        try
        {
            ApiVersionResponse response =
                    xmlrpcClient.call( new ApiVersionRequest(), ApiVersionResponse.class, NO_OP_URL_BUILDER,
                                       STANDARD_REQUEST_MODIFIER );

            if ( 1 != response.getApiVersion() )
            {
                logger.error( "Cannot connect to koji at: {}. API Version reported is '{}' but this client only supports version 1.", config.getKojiURL(), response.getApiVersion() );
                xmlrpcClient.close();
                xmlrpcClient = null;
            }
        }
        catch ( XmlRpcException e )
        {
            logger.error( "Cannot retrieve koji API version from: {}. (Reason: {})", config.getKojiURL(), e.getMessage(), e );
            xmlrpcClient.close();
            xmlrpcClient = null;
        }
    }

    public int getApiVersion()
            throws KojiClientException
    {
        checkConnection();

        try
        {
            ApiVersionResponse response =
                    xmlrpcClient.call( new ApiVersionRequest(), ApiVersionResponse.class, NO_OP_URL_BUILDER,
                                       STANDARD_REQUEST_MODIFIER );

            return response == null ? -1 : response.getApiVersion();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Cannot retrieve koji API version from: %s. (Reason: %s)", e,
                                           config.getKojiURL(), e.getMessage() );
        }
    }

    public KojiSessionInfo krbLogin()
            throws KojiClientException
    {
        checkConnection();

        try
        {
            KrbAuthenticator krbAuthenticator = new KrbAuthenticator( config );

            String encodedApReq = krbAuthenticator.prepareRequest();

            KrbLoginResponse loginResponse =
                    xmlrpcClient.call( new KrbLoginRequest( encodedApReq ), KrbLoginResponse.class, NO_OP_URL_BUILDER, STANDARD_REQUEST_MODIFIER );

            if ( loginResponse == null )
            {
                throw new KojiClientException( "Failed to get loginResponse" );
            }

            KojiSessionInfo session = krbAuthenticator.handleResponse( loginResponse );

            setLoggedInUser( session );

            return session;
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to login: %s", e, e.getMessage() );
        }
    }

    public KojiSessionInfo login()
            throws KojiClientException
    {
        checkConnection();

        if ( config.getKrbService() != null )
        {
            return krbLogin();
        }

        try
        {
            UrlBuilder urlBuilder = ( url ) -> new UrlBuildResult( UrlUtils.buildUrl( url, SSL_LOGIN_PATH ) );

            RequestModifier requestModifier =
                    ( request ) -> request.setHeader( ACCEPT_ENCODING_HEADER, IDENTITY_ENCODING_VALUE );

            LoginResponse loginResponse =
                    xmlrpcClient.call( new LoginRequest(), LoginResponse.class, urlBuilder, requestModifier );

            if ( loginResponse == null )
            {
                throw new KojiClientException( "Failed to get loginResponse" );
            }

            KojiSessionInfo session = loginResponse.getSessionInfo();
            setLoggedInUser( session );

            return session;
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to login: %s", e, e.getMessage() );
        }
    }

    public <T> T withKojiSession( KojiCustomCommand<T> command )
            throws KojiClientException
    {
        KojiSessionInfo session = null;
        T result = null;
        try
        {
            session = login();
            result = command.execute( session );
        }
        catch ( Exception e )
        {
            if ( logger.isDebugEnabled() )
            {
                logger.error( "Koji withSession lambda failed", e );
            }

            if ( e instanceof KojiClientException )
            {
                throw e;
            }
            else
            {
                throw new KojiClientException( "Koji withSession lambda command failed: %s", e, e.getMessage() );
            }
        }
        finally
        {
            logout( session );
        }

        return result;
    }

    private interface KojiInternalCommand<T>
    {
        T execute()
                throws KojiClientException, XmlRpcException;
    }

    private <T> T doXmlRpcAndThrow( KojiInternalCommand<T> cmd, String message, Object... params )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            return cmd.execute();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "%s. Reason: %s", e, String.format( message, params ), e.getMessage() );
        }
    }

    @SuppressWarnings("unused")
    private <T> T doXmlRpcAndWarn( KojiInternalCommand<T> cmd, String message, Object... params )
    {
        try
        {
            checkConnection();

            return cmd.execute();
        }
        catch ( XmlRpcException | KojiClientException e )
        {
            String formatted = String.format( "%s. Reason: %s", String.format( message, params ), e.getMessage() );
            if ( logger.isDebugEnabled() )
            {
                logger.warn( formatted, e );
            }
            else
            {
                logger.warn( formatted );
            }
        }

        return null;
    }

    public KojiUserInfo getLoggedInUserInfo( String username )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            UserResponse response =
                    xmlrpcClient.call( new UserRequest( username ), UserResponse.class, NO_OP_URL_BUILDER,
                                       STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getUserInfo();
        }, "Failed to retrieve current user info." );
    }

    public KojiUserInfo getLoggedInUserInfo( KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            UserResponse response =
                    xmlrpcClient.call( new LoggedInUserRequest(), UserResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );
                return response == null ? null : response.getUserInfo();
        }, "Failed to retrieve current user info." );
    }

    public void logout( KojiSessionInfo session )
    {
        if ( session == null )
        {
            return;
        }

        if ( xmlrpcClient != null )
        {
            try
            {
                StatusResponse response =
                        xmlrpcClient.call( new LogoutRequest(), StatusResponse.class, sessionUrlBuilder( session ),
                                           STANDARD_REQUEST_MODIFIER );

                if ( isNotEmpty( response.getError() ) )
                {
                    logger.error( "Failed to logout from Koji: {}", response.getError() );
                }
            }
            catch ( XmlRpcException e )
            {
                logger.error( "Failed to logout: {}", e.getMessage(), e );
            }
        }

        try
        {
            session.destroy();
        }
        catch ( DestroyFailedException e )
        {
            logger.error( "Failed to destroy session: {}", e.getMessage(), e );
        }
    }

    private void checkConnection()
            throws KojiClientException
    {
        if ( xmlrpcClient == null )
        {
            throw new KojiClientException( "Connection to koji at %s is closed. Perhaps it failed to initialize?",
                                           config.getKojiURL() );
        }
    }

    public List<KojiPermission> getAllPermissions( KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            AllPermissionsResponse response =
                    xmlrpcClient.call( new AllPermissionsRequest(), AllPermissionsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getPermissions();
        }, "Failed to retrieve listing of koji permissions." );
    }

    public boolean hasPermission( String permission, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            ConfirmationResponse response =
                    xmlrpcClient.call( new CheckPermissionRequest( permission ), ConfirmationResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? false : response.isSuccess();
        }, "Failed to check whether logged-in user has permission: %s", permission );
    }

    public Integer createTag( CreateTagRequest request, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            IdResponse response = xmlrpcClient.call( request, IdResponse.class, sessionUrlBuilder( session ),
                                                     STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getId();
        }, "Failed to create tag: %s", request );
    }

    public KojiTagInfo getTag( int tagId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            TagResponse response =
                    xmlrpcClient.call( new TagRequest( tagId ), TagResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getTagInfo();
        }, "Failed to retrieve tag: %s", tagId );
    }

    public KojiTagInfo getTag( String tagName, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            TagResponse response =
                    xmlrpcClient.call( new TagRequest( tagName ), TagResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getTagInfo();
        }, "Failed to retrieve tag: %s", tagName );
    }

    public Integer getTagId( String tagName, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            IdResponse response =
                    xmlrpcClient.call( new GetTagIdRequest( tagName ), IdResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getId();
        }, "Failed to retrieve tag: %s", tagName );
    }

    public Integer getPackageId( String packageName, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            IdResponse response = xmlrpcClient.call( new GetPackageIdRequest( packageName ), IdResponse.class,
                                                     sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getId();
        }, "Failed to retrieve package: %s", packageName );
    }

    public Map<String, KojiArchiveType> getArchiveTypeMap( KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            GetArchiveTypesResponse response =
                    xmlrpcClient.call( new GetArchiveTypesRequest(), GetArchiveTypesResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            if ( response == null )
            {
                return Collections.emptyMap();
            }

            Map<String, KojiArchiveType> types = new HashMap<>();
            response.getArchiveTypes()
                    .forEach( ( at ) -> at.getExtensions().forEach( ( ext ) -> types.put( ext, at ) ) );


            return types;
        }, "Failed to retrieve list of acceptable archive types" );
    }

    public KojiArchiveType getArchiveType( GetArchiveTypeRequest request, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            GetArchiveTypeResponse response =
                    xmlrpcClient.call( request, GetArchiveTypeResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getArchiveType();
        }, "Failed to retrieve archive type for request: %s", request );
    }

    public KojiImportResult importBuild( KojiImport importInfo, Iterable<Supplier<ImportFile>> importedFileSuppliers,
                                         KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            try
            {
                String dirname = generateUploadDirname( session, importInfo );

                Map<String, KojijiErrorInfo> uploadErrors =
                        uploadForImport( null, importedFileSuppliers, dirname, session );

                if ( !uploadErrors.isEmpty() )
                {
                    return new KojiImportResult( importInfo ).withUploadErrors( uploadErrors );
                }

                GetBuildResponse response =
                        xmlrpcClient.call( new CGInlinedImportRequest( importInfo, dirname ), GetBuildResponse.class,
                                           sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

                return new KojiImportResult( importInfo ).withBuildInfo( response.getBuildInfo() );
            }
            catch ( RuntimeException e )
            {
                logger.error( "FAIL: {}", e.getMessage(), e );
                throw e;
            }
        }, "Failed to execute content-generator import" );
    }

    public <T extends KojiQuery> List<Integer> queryCountOnly( String method, List<T> queries, KojiSessionInfo session )
        throws KojiClientException
    {
        Registry registry = Registry.getInstance();

        List<Object> args = new ArrayList<>();
        for ( T query : queries )
        {
            if ( query.getQueryOpts() != null )
            {
                query.getQueryOpts().setCountOnly( true );
            }
            else
            {
                query.setQueryOpts( new KojiQueryOpts().withCountOnly( true ) );
            }

            args.add( registry.renderTo( query ) );
        }

        MultiCallRequest.Builder builder = getBuilder();
        args.forEach( arg -> builder.addCallObj( method, arg ) );

        MultiCallResponse multiCallResponse = multiCall( builder.build(), session );
        List<KojiMultiCallValueObj> multiCallValueObjs = multiCallResponse.getValueObjs();
        List<Integer> ret = new ArrayList<>( multiCallValueObjs.size() );

        multiCallValueObjs.forEach( v -> {
            Object data = v.getData();
            if ( data instanceof Integer )
            {
                ret.add( (Integer) data );
            }
            else
            {
                logger.debug( "Data object is not of type Integer, type: {}, data: {}", data.getClass(), data );
                ret.add( null ); // indicate an error
            }
        } );

        return ret;
    }

    public List<KojiBuildType> listBuildTypes( KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            ListBuildTypesResponse response =
                    xmlrpcClient.call( new ListBuildTypesRequest(), ListBuildTypesResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiBuildType> types = response.getBuildTypes();
            return types == null ? Collections.emptyList() : types;
        }, "Failed to retrieve list of available build types" );
    }

    public List<KojiBuildType> listBuildTypes( KojiBuildTypeQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            ListBuildTypesResponse response =
                    xmlrpcClient.call( new ListBuildTypesRequest( query ), ListBuildTypesResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiBuildType> types = response.getBuildTypes();
            return types == null ? Collections.emptyList() : types;
        }, "Failed to retrieve list of available build types for build type query: %s", query );
    }

    public int getBuildTypeCount( KojiBuildTypeQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        if ( query.getQueryOpts() != null )
        {
            query.getQueryOpts().setCountOnly( true );
        }
        else
        {
            query.setQueryOpts( new KojiQueryOpts().withCountOnly( true ) );
        }

        return doXmlRpcAndThrow( ()->{
            KojiQueryCountOnlyResponse response =
                    xmlrpcClient.call( new ListBuildTypesRequest( query ), KojiQueryCountOnlyResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getCount();
        }, "Failed to retrieve count for query: %s", query );
    }

    public List<KojiBuildInfo> listBuilds( KojiBuildQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            BuildListResponse response =
                    xmlrpcClient.call( new ListBuildsRequest( query ),
                                       BuildListResponse.class, sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiBuildInfo> builds = response.getBuilds();
            return builds == null ? Collections.emptyList() : builds;
        }, "Failed to retrieve list of builds for build query: %s", query );

    }

    public int getBuildCount( KojiBuildQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        if ( query.getQueryOpts() != null )
        {
            query.getQueryOpts().setCountOnly( true );
        }
        else
        {
            query.setQueryOpts( new KojiQueryOpts().withCountOnly( true ) );
        }

        return doXmlRpcAndThrow( ()->{
            KojiQueryCountOnlyResponse response =
                    xmlrpcClient.call( new ListBuildsRequest( query ), KojiQueryCountOnlyResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getCount();
        }, "Failed to retrieve count for query: %s", query );
    }

    public List<KojiTagInfo> listAllTags( KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            ListTagsResponse response =
                    xmlrpcClient.call( new ListTagsRequest(), ListTagsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiTagInfo> tags = response.getTags();
            return tags == null ? Collections.emptyList() : tags;
        }, "Failed to retrieve list of all tags" );
    }

    public int getTagCount( KojiTagQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        if ( query.getQueryOpts() != null )
        {
            query.getQueryOpts().setCountOnly( true );
        }
        else
        {
            query.setQueryOpts( new KojiQueryOpts().withCountOnly( true ) );
        }

        return doXmlRpcAndThrow( ()->{
            KojiQueryCountOnlyResponse response =
                    xmlrpcClient.call( new ListTagsRequest( query ), KojiQueryCountOnlyResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getCount();
        }, "Failed to retrieve count for query: %s", query );
    }

    public List<KojiTagInfo> listTags( KojiBuildInfo buildInfo, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            ListTagsResponse response =
                    xmlrpcClient.call( new ListTagsRequest( new KojiTagQuery( buildInfo ) ), ListTagsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiTagInfo> tags = response.getTags();
            return tags == null ? Collections.emptyList() : tags;
        }, "Failed to retrieve list of tags for build: %s", buildInfo );
    }

    public List<KojiTagInfo> listTags( KojiNVR nvr, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            ListTagsResponse response =
                    xmlrpcClient.call( new ListTagsRequest( new KojiTagQuery( nvr ) ), ListTagsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiTagInfo> tags = response.getTags();
            return tags == null ? Collections.emptyList() : tags;
        }, "Failed to retrieve list of tags for build: %s", nvr );
    }

    public List<KojiTagInfo> listTags( String nvr, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            ListTagsResponse response =
                    xmlrpcClient.call( new ListTagsRequest( new KojiTagQuery( nvr ) ), ListTagsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiTagInfo> tags = response.getTags();
            return tags == null ? Collections.emptyList() : tags;
        }, "Failed to retrieve list of tags for build: %s", nvr );
    }

    public List<KojiTagInfo> listTags( int buildId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            ListTagsResponse response =
                    xmlrpcClient.call( new ListTagsRequest( new KojiTagQuery( buildId ) ), ListTagsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiTagInfo> tags = response.getTags();
            return tags == null ? Collections.emptyList() : tags;
        }, "Failed to retrieve list of tags for build: %s", buildId );
    }

    /**
     * Get tags giving a list of build Ids. This uses multicall and is much faster than calling listTags(id) one by one.
     * @return A Map where the build Id is the key and tags as value ( a list ).
     */
    public Map<Integer, List<KojiTagInfo>> listTags( List<Integer> buildIds, KojiSessionInfo session )
                    throws KojiClientException
    {
        Map<Integer, List<KojiTagInfo>> ret = new HashMap<>();

        List<List<KojiTagInfo>> l = new KojiClientHelper( this ).listTagsByIds( buildIds, session );
        for ( int i = 0; i < buildIds.size(); i++ )
        {
            List<KojiTagInfo> list = l.get( i );
            if ( list != null )
            {
                ret.put( buildIds.get( i ), list );
            }
        }
        return ret;
    }

    public KojiArchiveInfo getArchiveInfo( int archiveId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            GetArchiveResponse response = xmlrpcClient.call( new GetArchiveRequest( archiveId ),
                                                             GetArchiveResponse.class, sessionUrlBuilder( session ),
                                                             STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getArchiveInfo();
        }, "Failed to retrieve archive info for: %d", archiveId );

    }

    public KojiMavenArchiveInfo getMavenArchiveInfo( int archiveId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            GetMavenArchiveResponse response =
                    xmlrpcClient.call( new GetMavenArchiveRequest( archiveId ), GetMavenArchiveResponse.class,
                            sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getMavenArchiveInfo();
        }, "Failed to retrieve maven archive info for: %d", archiveId );
    }

    public KojiImageArchiveInfo getImageArchiveInfo( int archiveId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            GetImageArchiveResponse response =
                    xmlrpcClient.call( new GetImageArchiveRequest( archiveId ), GetImageArchiveResponse.class,
                            sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getImageArchiveInfo();
        }, "Failed to retrieve image archive info for: %d", archiveId );
    }

    public KojiWinArchiveInfo getWinArchiveInfo( int archiveId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            GetWinArchiveResponse response =
                    xmlrpcClient.call( new GetWinArchiveRequest( archiveId ), GetWinArchiveResponse.class,
                            sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getWinArchiveInfo();
        }, "Failed to retrieve win archive info for: %d", archiveId );
    }

    public int getArchiveCount( KojiArchiveQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        if ( query.getQueryOpts() != null )
        {
            query.getQueryOpts().setCountOnly( true );
        }
        else
        {
            query.setQueryOpts( new KojiQueryOpts().withCountOnly( true ) );
        }

        return doXmlRpcAndThrow( ()->{
            KojiQueryCountOnlyResponse response =
                    xmlrpcClient.call( new ListArchivesRequest( query ), KojiQueryCountOnlyResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getCount();
        }, "Failed to retrieve count for query: %s", query );
    }

    public List<KojiArchiveInfo> listArchives( KojiArchiveQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            ListArchivesResponse response = xmlrpcClient.call( new ListArchivesRequest( query ),
                                                               ListArchivesResponse.class, sessionUrlBuilder( session ),
                                                               STANDARD_REQUEST_MODIFIER );

            List<KojiArchiveInfo> archives = response.getArchives();

            return archives == null ? Collections.emptyList() : archives;
        }, "Failed to retrieve list of artifacts matching archive query: %s", query );
    }

    public void enrichArchiveTypeInfo( List<KojiArchiveInfo> archives, KojiSessionInfo session )
            throws KojiClientException
    {
        Map<String, List<KojiArchiveInfo>> buildTypeMap = archives.stream().collect( Collectors.groupingBy( KojiArchiveInfo::getBuildType ) );

        final AtomicReference<KojiClientException> err = new AtomicReference<>();

        buildTypeMap.forEach( ( buildType, archiveInfos ) -> {
            List<Object> archiveIds = archiveInfos.stream().map( KojiArchiveInfo::getArchiveId ).collect( Collectors.toList() );
            try
            {
                switch ( buildType )
                {
                    case "maven":
                        List<KojiMavenArchiveInfo> mavenArchiveInfos =
                                        multiCall( Constants.GET_MAVEN_ARCHIVE, archiveIds, KojiMavenArchiveInfo.class,
                                                   session );
                        for ( int i = 0; i < mavenArchiveInfos.size(); i++ )
                        {
                            archiveInfos.get( i ).addMavenArchiveInfo( mavenArchiveInfos.get( i ) );
                        }
                        break;
                    case "image":
                        List<KojiImageArchiveInfo> imageArchiveInfos =
                                        multiCall( Constants.GET_IMAGE_ARCHIVE, archiveIds, KojiImageArchiveInfo.class,
                                                   session );
                        for ( int i = 0; i < imageArchiveInfos.size(); i++ )
                        {
                            archiveInfos.get( i ).addImageArchiveInfo( imageArchiveInfos.get( i ) );
                        }
                        break;
                    case "win":
                        List<KojiWinArchiveInfo> winArchiveInfos =
                                        multiCall( Constants.GET_WIN_ARCHIVE, archiveIds, KojiWinArchiveInfo.class,
                                                   session );
                        for ( int i = 0; i < winArchiveInfos.size(); i++ )
                        {
                            archiveInfos.get( i ).addWinArchiveInfo( winArchiveInfos.get( i ) );
                        }
                        break;
                    default:
                        logger.warn( "Unknown archive build type: {}", buildType );
                }
            }
            catch ( KojiClientException e )
            {
                err.set( e );
            }
        });
        if ( err.get() != null )
        {
            throw err.get();
        }
    }

    public List<KojiArchiveInfo> listMavenArchivesMatching( String groupId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            ListArchivesResponse response = xmlrpcClient.call( new ListArchivesRequest(
                                                                       new KojiArchiveQuery().withMavenRef(
                                                                               new KojiMavenRef().withGroupId( groupId ) ) ),
                                                               ListArchivesResponse.class, sessionUrlBuilder( session ),
                                                               STANDARD_REQUEST_MODIFIER );

            List<KojiArchiveInfo> archives = response.getArchives();
            return archives == null ? Collections.emptyList() : archives;
        }, "Failed to retrieve list of Maven archives matching groupId: %s", groupId );
    }

    public List<KojiArchiveInfo> listMavenArchivesMatching( String groupId, String artifactId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            ListArchivesResponse response = xmlrpcClient.call( new ListArchivesRequest(
                                                                       new KojiArchiveQuery().withMavenRef(
                                                                               new KojiMavenRef().withGroupId( groupId ).withArtifactId( artifactId ) ) ),
                                                               ListArchivesResponse.class, sessionUrlBuilder( session ),
                                                               STANDARD_REQUEST_MODIFIER );

            List<KojiArchiveInfo> archives = response.getArchives();
            return archives == null ? Collections.emptyList() : archives;
        }, "Failed to retrieve list of Maven archives matching: %s:%s", groupId, artifactId );
    }

    public List<KojiArchiveInfo> listArchivesMatching( ProjectRef ga, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            ListArchivesResponse response =
                    xmlrpcClient.call( new ListArchivesRequest( ga ), ListArchivesResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiArchiveInfo> archives = response.getArchives();
            return archives == null ? Collections.emptyList() : archives;
        }, "Failed to retrieve list of archives for: %s", ga );
    }

    public List<KojiBuildArchiveCollection> listArchivesForBuilds( ProjectRef ga, KojiBuildState state,
                                                                   KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            BuildListResponse buildsResponse =
                    xmlrpcClient.call( new ListBuildsRequest( new KojiBuildQuery( ga ).withState( state ) ),
                                       BuildListResponse.class, sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            if ( buildsResponse == null )
            {
                return Collections.emptyList();
            }

            List<KojiBuildArchiveCollection> builds = new ArrayList<>();

            for ( KojiBuildInfo build : buildsResponse.getBuilds() )
            {
                KojiBuildArchiveCollection collection = listArchivesForBuild( build, session );;
                if ( collection != null )
                {
                    builds.add( collection );
                }
            }
            return builds;

        }, "Failed to retrieve list of archives for: %s", ga );
    }

    public KojiBuildArchiveCollection listArchivesForBuild( KojiNVR nvr, KojiSessionInfo session )
            throws KojiClientException
    {
        return listArchivesForBuild( new GetBuildByNVRObjRequest( nvr ), session );
    }

    public KojiBuildArchiveCollection listArchivesForBuild( KojiIdOrName build, KojiSessionInfo session )
            throws KojiClientException
    {
        return listArchivesForBuild( new GetBuildByIdOrNameRequest( build ), session );
    }

    public KojiBuildArchiveCollection listArchivesForBuild( String nvr, KojiSessionInfo session )
            throws KojiClientException
    {
        return listArchivesForBuild( new GetBuildByIdOrNameRequest( nvr ), session );
    }

    public KojiBuildArchiveCollection listArchivesForBuild( int buildId, KojiSessionInfo session )
            throws KojiClientException
    {
        return listArchivesForBuild( new GetBuildByIdOrNameRequest( buildId ), session );
    }

    public KojiBuildArchiveCollection listArchivesForBuild( GetBuildRequest request, KojiSessionInfo session )
            throws KojiClientException
    {
        KojiBuildInfo build = doXmlRpcAndThrow( () -> {
            GetBuildResponse buildResponse =
                    xmlrpcClient.call( request, GetBuildResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            if ( buildResponse == null )
            {
                throw new KojiClientException( "No such build for request: %s", request );
            }

            return buildResponse.getBuildInfo();
        }, "Failed to retrieve build for: %s", request );

        return listArchivesForBuild( build, session );
    }

    public KojiBuildArchiveCollection listArchivesForBuild( final KojiBuildInfo build, final KojiSessionInfo session )
                    throws KojiClientException
    {
        ListArchivesResponse archivesResponse = doXmlRpcAndThrow( () -> xmlrpcClient.call(
                new ListArchivesRequest( new KojiArchiveQuery().withBuildId( build.getId() ) ),
                ListArchivesResponse.class, sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER ),
                                                                 "Failed to retrieve archives for build: '%s'",
                                                                 build.getNvr() );

        if ( archivesResponse != null )
        {
            List<KojiArchiveInfo> archives = archivesResponse.getArchives();
            return new KojiBuildArchiveCollection( build, archives );
        }

        return null;
    }

    public KojiRpmBuildList getLatestRPMs( KojiTagInfo tag, KojiSessionInfo session )
            throws KojiClientException
    {
        return getLatestRPMs( tag.getName(), session );
    }

    public KojiRpmBuildList getLatestRPMs( String tagName, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()-> {
            RpmBuildListResponse response =
                    xmlrpcClient.call( new ListTaggedRpmsRequest( tagName ), RpmBuildListResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getRpmBuildList();
        }, "Failed to list builds tagged in: %s", tagName );
    }

    public List<KojiRpmInfo> getRPM( KojiIdOrName rpm, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            RpmListResponse response =
                    xmlrpcClient.call( new GetRpmRequest().withRpminfo( rpm ).withParams( new KojiGetRpmParams().withMulti( Boolean.TRUE ) ),
                                       RpmListResponse.class, sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiRpmInfo> builds = response.getRpms();
            return builds == null ? Collections.emptyList() : builds;
        }, "Failed to retrieve list of rpms for rpm: %s", rpm );
    }

    public List<KojiRpmDependencyInfo> getRPMDeps( int rpmId, KojiSessionInfo session )
            throws KojiClientException
    {
        return getRPMDeps( rpmId, null, session );
    }

    public List<KojiRpmDependencyInfo> getRPMDeps( int rpmId, KojiRpmDepsQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            GetRpmDepsResponse response =
                    xmlrpcClient.call( new GetRpmDepsRequest( rpmId, query ),
                                       GetRpmDepsResponse.class, sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiRpmDependencyInfo> depInfos = response.getRpmDependencyInfos();
            return depInfos == null ? Collections.emptyList() : depInfos;
        }, "Failed to retrieve list of rpm dependency info for rpm id: %d", rpmId );
    }

    public KojiRpmFileInfo getRPMFile( int rpmId, String filename, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            GetRpmFileResponse response =
                    xmlrpcClient.call( new GetRpmFileRequest( rpmId, filename ),
                                       GetRpmFileResponse.class, sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getRpmFileInfo();
        }, "Failed to retrieve list of rpm dependency info for rpm id: %d, filename: %s", rpmId, filename );
    }

    public Map<String, Object> getRPMHeaders( KojiGetRpmHeadersParams params, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            GetRpmHeadersResponse response =
                    xmlrpcClient.call( new GetRpmHeadersRequest( params ),
                                       GetRpmHeadersResponse.class, sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            Map<String, Object> ret = response.getHeaders();
            return ret == null ? Collections.emptyMap() : ret;
        }, "Failed to retrieve list of rpm headers for params: %s", params );
    }

    public List<KojiRpmInfo> listBuildRPMs( int buildId, KojiSessionInfo session )
            throws KojiClientException
    {
        return listBuildRPMs( KojiIdOrName.getFor( buildId ), session );
    }

    public List<KojiRpmInfo> listBuildRPMs( String buildName, KojiSessionInfo session )
            throws KojiClientException
    {
        return listBuildRPMs( KojiIdOrName.getFor( buildName ), session );
    }

    public List<KojiRpmInfo> listBuildRPMs( KojiIdOrName build, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            RpmListResponse response =
                    xmlrpcClient.call( new ListBuildRpmsRequest( build ), RpmListResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? Collections.emptyList() : response.getRpms();
        }, "Failed to retrieve rpms for build: %s", build );
    }

    public List<KojiRpmFileInfo> listRPMFiles( int rpmId, KojiRpmFilesQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            ListRpmFilesResponse response =
                    xmlrpcClient.call( new ListRpmFilesRequest( rpmId, query ),
                                       ListRpmFilesResponse.class, sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiRpmFileInfo> rpmFileInfos = response.getRpmFileInfos();
            return rpmFileInfos == null ? Collections.emptyList() : rpmFileInfos;
        }, "Failed to retrieve list of rpms files for rpm id: %d", rpmId );
    }

    public List<KojiRpmInfo> listRPMs( KojiRpmQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            RpmListResponse response =
                    xmlrpcClient.call( new ListRpmsRequest( query ),
                                       RpmListResponse.class, sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiRpmInfo> builds = response.getRpms();
            return builds == null ? Collections.emptyList() : builds;
        }, "Failed to retrieve list of rpms for rpm query: %s", query );
    }

    public KojiRpmBuildList listTaggedRPMS( KojiTagInfo tag, KojiSessionInfo session )
            throws KojiClientException
    {
        return listTaggedRPMS( tag.getName(), session );
    }

    public KojiRpmBuildList listTaggedRPMS( String tagName, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()-> {
            RpmBuildListResponse response =
                    xmlrpcClient.call( new ListTaggedRpmsRequest( tagName ), RpmBuildListResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getRpmBuildList();
        }, "Failed to list builds tagged in: %s", tagName );
    }

    public List<KojiRpmSignatureInfo> queryRPMSigs( KojiRpmSigsQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            QueryRpmSigsResponse response =
                    xmlrpcClient.call( new QueryRpmSigsRequest( query ),
                                       QueryRpmSigsResponse.class, sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiRpmSignatureInfo> rpmSignatureInfos = response.getRpmSignatureInfos();
            return rpmSignatureInfos == null ? Collections.emptyList() : rpmSignatureInfos;
        }, "Failed to retrieve list of rpm sigs for query: %s", query );
    }

    /**
     * Generic multiCall method. User can construct their own multicall request and parse the returned muticall response.
     * @param multiCallRequest
     * @param session
     * @return
     */
    public MultiCallResponse multiCall( MultiCallRequest multiCallRequest, KojiSessionInfo session )
                    throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            MultiCallResponse response =
                            xmlrpcClient.call( multiCallRequest,
                                               MultiCallResponse.class, sessionUrlBuilder( session ),
                                               STANDARD_REQUEST_MODIFIER );

            return response;
        }, "Failed to do multicall" );
    }

    /**
     * This is multiCall for homogeneous request. The "method" is specified and the call responses are of single type T.
     * @param method
     * @param session
     * @param args args list. If call object has more than one parameters, use List for element type.
     * @param type result object type
     * @return a list containing objects of type T
     */
    public <S extends Object, T> List<T> multiCall( String method, List<S> args, Class<T> type, KojiSessionInfo session )
                    throws KojiClientException
    {
        MultiCallRequest req = buildMultiCallRequest( method, args );
        MultiCallResponse response = multiCall( req, session );
        return parseMultiCallResponse( response, type );
    }

    /**
     * This is multiCall for more flexible homogeneous request.
     * The "method" is specified and the responses type can be of a single type T or List when the args are queries
     * which would return a List for every single query.
     *
     * The caller can parse/interperete the response by {@link KojiClientUtils#parseMultiCallResponse(MultiCallResponse, Class)} or
     * {@link KojiClientUtils#parseMultiCallResponseToLists(MultiCallResponse, Class)}.
     */
    public MultiCallResponse multiCall( String method, List<?> args, KojiSessionInfo session )
                    throws KojiClientException
    {
        MultiCallRequest req = buildMultiCallRequest( method, args );
        return multiCall( req, session );
    }

    /**
     * Get list of KojiBuildInfo objects that contains specified GAV. It first get archives list, and retrieve
     * build ids. Then use the ids to issue a multicall request to retrieve all build info objects.
     */
    public List<KojiBuildInfo> listBuildsContaining( ProjectVersionRef gav, KojiSessionInfo session )
                    throws KojiClientException
    {
        List<KojiArchiveInfo> archives = doXmlRpcAndThrow( () -> {
            ListArchivesResponse response =
                            xmlrpcClient.call( new ListArchivesRequest( gav ), ListArchivesResponse.class,
                                               sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? Collections.emptyList() : response.getArchives();
        }, "Failed to retrieve list of archives for: %s", gav );

        List<Object> args = new ArrayList<>();
        archives.forEach(( archive ) -> {
            args.add( archive.getBuildId() );
        });

        return multiCall( GET_BUILD, args, KojiBuildInfo.class, session );
    }

    public int getPackageCount( KojiPackageQuery query, KojiSessionInfo session )
            throws KojiClientException
    {
        if ( query.getQueryOpts() != null )
        {
            query.getQueryOpts().setCountOnly( true );
        }
        else
        {
            query.setQueryOpts( new KojiQueryOpts().withCountOnly( true ) );
        }

        return doXmlRpcAndThrow( ()->{
            KojiQueryCountOnlyResponse response =
                    xmlrpcClient.call( new ListPackagesRequest( query ), KojiQueryCountOnlyResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getCount();
        }, "Failed to retrieve count for query: %s", query );
    }

    public List<KojiPackageInfo> listPackagesForTag( String tag, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            IdResponse r =
                    xmlrpcClient.call( new GetTagIdRequest( tag ), IdResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            if ( r == null || r.getId() == null )
            {
                throw new KojiClientException( "No such tag: %s", tag );
            }

            ListPackagesResponse response =
                    xmlrpcClient.call( new ListPackagesRequest( new KojiPackageQuery().withTagId( r.getId() ) ),
                                       ListPackagesResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            return response == null ? Collections.emptyList() : response.getPackages();
        }, "Failed to retrieve package list for tag: %s", tag );
    }

    public boolean addPackageToTag( String tag, String pkg, KojiSessionInfo session )
            throws KojiClientException
    {
        return addPackageToTag( tag, pkg, null, session );
    }

    public boolean addPackageToTag( String tag, ProjectRef ga, KojiSessionInfo session )
            throws KojiClientException
    {
        return addPackageToTag( tag, toKojiName( ga ), null, session );
    }

    public boolean addPackageToTag( String tag, ProjectRef gav, String ownerName, KojiSessionInfo session )
            throws KojiClientException
    {
        return addPackageToTag( tag, toKojiName( gav ), ownerName, session );
    }

    public boolean addPackageToTag( String tag, String pkg, String ownerName, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            setLoggedInUser( session );

            IdResponse r =
                    xmlrpcClient.call( new GetTagIdRequest( tag ), IdResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            if ( r == null || r.getId() == null )
            {
                throw new KojiClientException( "No such tag: %s", tag );
            }

            boolean add = true;

            ListPackagesResponse listPackagesResponse =
                    xmlrpcClient.call( new ListPackagesRequest( new KojiPackageQuery().withTagId( r.getId() ).withUserId( session.getUserInfo().getUserId() ) ),
                                       ListPackagesResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            if ( listPackagesResponse != null )
            {
                List<KojiPackageInfo> packages = listPackagesResponse.getPackages();
                if ( packages.parallelStream()
                             .filter( ( info ) -> info.getPackageName().equals( pkg ) )
                             .findFirst()
                             .isPresent() )
                {
                    add = false;
                }
            }
            else
            {
                logger.debug( "List-packages for tag: {} returned null result!", tag );
            }

            if ( add )
            {
                String owner = ownerName;
                if ( isEmpty( owner ) )
                {
                    owner = session.getUserInfo().getUserName();
                }

                xmlrpcClient.call( new AddPackageToTagRequest( r.getId(), pkg, owner ), AckResponse.class,
                                   sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

                return true;
            }

            return false;

        }, "Failed to retrieve package list for tag: %s", tag );
    }

    private void setLoggedInUser( KojiSessionInfo session )
            throws KojiClientException
    {
        if ( session.getUserInfo() == null )
        {
            session.setUserInfo( getLoggedInUserInfo( session ) );
        }
    }

    public void removePackageFromTag( String tag, String pkg, KojiSessionInfo session )
            throws KojiClientException
    {
        doXmlRpcAndThrow( () -> {
            xmlrpcClient.call( new RemovePackageFromTagRequest( tag, pkg ), AckResponse.class,
                               sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );
            return null;
        }, "Failed to remove package '%s' from tag '%s'", pkg, tag );
    }

    public Integer tagBuild( String tag, String buildNvr, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            IdResponse response = xmlrpcClient.call( new TagBuildRequest( tag, buildNvr ), IdResponse.class,
                                                 sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getId();
        }, "Failed to start tagging request for build: %s into tag: %s", buildNvr, tag  );
    }

    public KojiTaskInfo getTaskInfo( int taskId, KojiSessionInfo session )
        throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            GetTaskResponse taskResponse = xmlrpcClient.call( new GetTaskRequest( taskId ), GetTaskResponse.class,
                               sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return taskResponse == null ? null : taskResponse.getTaskInfo();
        }, "Failed to load task info for: %s", taskId );
    }

    public KojiTaskInfo getTaskInfo( int taskId, boolean request, KojiSessionInfo session )
            throws KojiClientException
    {
            return doXmlRpcAndThrow( ()->{
                GetTaskResponse taskResponse = xmlrpcClient.call( new GetTaskRequest( taskId, request ), GetTaskResponse.class,
                                   sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

                return taskResponse == null ? null : taskResponse.getTaskInfo();
            }, "Failed to load task info for: %s", taskId );
    }

    public KojiTaskRequest getTaskRequest( int taskId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            GetTaskRequestResponse response = xmlrpcClient.call( new GetTaskRequestRequest( taskId ), GetTaskRequestResponse.class,
                                   sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : new KojiTaskRequest( response.getTaskRequestInfo() );
        }, "Failed to get task request info for: %s", taskId );
    }

    public List<KojiBuildInfo> listTagged( KojiTagInfo tag, KojiSessionInfo session )
            throws KojiClientException
    {
        return listTagged( tag.getName(), session );
    }

    public List<KojiBuildInfo> listTagged( String tagName, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()-> {
            BuildListResponse buildsResponse =
                    xmlrpcClient.call( new ListTaggedRequest( tagName ), BuildListResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            if ( buildsResponse == null )
            {
                logger.debug( "No builds response was returned!" );
                return Collections.emptyList();
            }

            List<KojiBuildInfo> builds = buildsResponse.getBuilds();
            if ( builds == null || builds.isEmpty() )
            {
                logger.debug( "No builds tagged in: '{}'", tagName );
                return Collections.emptyList();
            }

            return builds;
        }, "Failed to list builds tagged in: %s", tagName );
    }

    public boolean untagBuild( String tag, String buildNvr, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( ()->{
            xmlrpcClient.call( new UntagBuildRequest( tag, buildNvr ), AckResponse.class, sessionUrlBuilder( session ),
                               STANDARD_REQUEST_MODIFIER );

            return true;
        }, "Failed to untag build: %s from: %s", buildNvr, tag );
    }

    public KojiBuildInfo getBuildInfo( KojiNVR nvr, KojiSessionInfo session )
            throws KojiClientException
    {
        KojiBuildInfo buildInfo = doXmlRpcAndThrow( () -> {
            GetBuildResponse response = xmlrpcClient.call( new GetBuildByNVRObjRequest( nvr ), GetBuildResponse.class,
                                                           sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getBuildInfo();
        }, "Failed to retrieve build info for: %s", nvr );

        return withBuildTypeInfo( buildInfo, session );
    }

    public KojiBuildInfo getBuildInfo( String nvr, KojiSessionInfo session )
            throws KojiClientException
    {
        KojiBuildInfo buildInfo = doXmlRpcAndThrow( () -> {
            GetBuildResponse response = xmlrpcClient.call( new GetBuildByIdOrNameRequest( nvr ), GetBuildResponse.class,
                                                           sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getBuildInfo();
        }, "Failed to retrieve build info for: %s", nvr );

        return withBuildTypeInfo( buildInfo, session );
    }

    public KojiBuildInfo getBuildInfo( int buildId, KojiSessionInfo session )
            throws KojiClientException
    {
        KojiBuildInfo buildInfo = doXmlRpcAndThrow( () -> {
            GetBuildResponse response =
                    xmlrpcClient.call( new GetBuildByIdOrNameRequest( buildId ), GetBuildResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getBuildInfo();
        }, "Failed to retrieve build info for: %s", buildId );

        return withBuildTypeInfo( buildInfo, session );
    }

    public KojiBuildTypeInfo getBuildTypeInfo(int buildId, KojiSessionInfo session)
            throws KojiClientException
    {
        KojiBuildTypeInfo buildTypeInfo = doXmlRpcAndThrow( () -> {
            GetBuildTypeResponse response =
                    xmlrpcClient.call( new GetBuildTypeRequest( buildId ), GetBuildTypeResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getBuildTypeInfo();
        }, "Failed to retrieve build info for: %s", buildId );

        return buildTypeInfo;
    }

    private KojiBuildInfo withBuildTypeInfo( KojiBuildInfo buildInfo, KojiSessionInfo session )
            throws KojiClientException
    {
        if ( buildInfo != null )
        {
            KojiBuildTypeInfo buildTypeInfo = getBuildTypeInfo(buildInfo.getId(), session);

            if ( buildTypeInfo != null )
            {
                return addBuildTypeInfo( buildTypeInfo, buildInfo );
            }
        }

        return buildInfo;
    }

    public KojiMavenBuildInfo getMavenBuildInfo( int buildId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            GetMavenBuildResponse response =
                    xmlrpcClient.call( new GetMavenBuildRequest( buildId ), GetMavenBuildResponse.class,
                            sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getMavenBuildInfo();
        }, "Failed to retrieve maven build info for: %s", buildId );
    }

    public KojiWinBuildInfo getWinBuildInfo( int buildId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            GetWinBuildResponse response =
                    xmlrpcClient.call( new GetWinBuildRequest( buildId ), GetWinBuildResponse.class,
                            sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getWinBuildInfo();
        }, "Failed to retrieve win build info for: %s", buildId );
    }

    public KojiImageBuildInfo getImageBuildInfo( int buildId, KojiSessionInfo session )
            throws KojiClientException
    {
        return doXmlRpcAndThrow( () -> {
            GetImageBuildResponse response =
                    xmlrpcClient.call( new GetImageBuildRequest( buildId ), GetImageBuildResponse.class,
                            sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getImageBuildInfo();
        }, "Failed to retrieve image build info for: %s", buildId );
    }

    protected String generateUploadDirname( KojiSessionInfo session, KojiImport importInfo )
            throws KojiClientException
    {
        setLoggedInUser( session );

        return String.format( "kojiji-upload/%s-%s-%s-%s/", new SimpleDateFormat( "yyyyMMdd-HHmmssSSS" ).format( new Date() ),
                              importInfo.getBuildNVR().getName(), importInfo.getBuildNVR().getVersion(), session.getUserInfo().getUserName() );
    }

    /**
     * Upload built artifacts in sequence.
     */
    private Map<String, KojijiErrorInfo> uploadForImport( KojiImport buildInfo,
                                                            Iterable<Supplier<ImportFile>> uploadedFileSuppliers,
                                                            String dirname, KojiSessionInfo session )
            throws KojiClientException
    {
        final Map<String, KojijiErrorInfo> uploadErrors = new HashMap<>();

        if ( buildInfo != null )
        {
            // there are two ways to call CGImport: with the metadata uploaded (here), and with it inlined in the request.
            // if buildInfo is null, we're using the second approach.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try
            {
                objectMapper.writeValue( baos, buildInfo );
            }
            catch ( IOException e )
            {
                throw new KojiClientException( "Failed to serialize import info to JSON. Reason: %s", e, e.getMessage() );
            }

            byte[] data = baos.toByteArray();
            try {
                KojiUploaderResult result = newUploader(() -> new ImportFile(METADATA_JSON_FILE, new ByteArrayInputStream(data), data.length),
                        dirname, session).call();
                Exception error = result.getError();
                if ( error != null )
                {
                    uploadErrors.put(result.getUploadFilePath(),
                            new KojijiErrorInfo( result.getUploadFilePath(), error, result.isTemporaryError() ) );
                }
            }
            catch (Exception e)
            {
                throw new KojiClientException( "Failed to execute uploads for: %s. Reason: %s", e, buildInfo, e.getMessage() );
            }
        }

        for ( Supplier<ImportFile> fileSupplier : uploadedFileSuppliers )
        {
            try
            {
                KojiUploaderResult result = newUploader(fileSupplier, dirname, session).call();
                Exception error = result.getError();
                if ( error != null )
                {
                    uploadErrors.put( result.getUploadFilePath(),
                            new KojijiErrorInfo( result.getUploadFilePath(), error, result.isTemporaryError() ) );
                }
            }
            catch ( Exception e )
            {
                throw new KojiClientException( "Failed to execute uploads for: %s. Reason: %s", e, buildInfo, e.getMessage() );
            }
        }
        return uploadErrors;
    }

/*
 * This is not thread-safe.
 * Uploading files in parallel much be done in sub-sessions. Otherwise, it can fail on
 * "requests are received out of sequence" err if the last call for the session has higher callnum than prev ones.
 * Until we could add and test the sub-session approach, I leave out this method.
 * ruhan, Jan 13, 2023

     protected Map<String, KojijiErrorInfo> uploadForImport( KojiImport buildInfo,
                                                                Iterable<Supplier<ImportFile>> uploadedFileSuppliers,
                                                                String dirname, KojiSessionInfo session )
            throws KojiClientException
    {
        ExecutorCompletionService<KojiUploaderResult> uploadService = new ExecutorCompletionService<>( executorService );
        AtomicInteger count = new AtomicInteger( 0 );

        if ( buildInfo != null )
        {
            // there are two ways to call CGImport: with the metadata uploaded (here), and with it inlined in the request.
            // if buildInfo is null, we're using the second approach.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try
            {
                objectMapper.writeValue( baos, buildInfo );
            }
            catch ( IOException e )
            {
                throw new KojiClientException( "Failed to serialize import info to JSON. Reason: %s", e, e.getMessage() );
            }

            byte[] data = baos.toByteArray();
            uploadService.submit(
                    newUploader( ()->new ImportFile( METADATA_JSON_FILE, new ByteArrayInputStream( data ), data.length ),
                                 dirname, session ) );

            count.incrementAndGet();
        }

        uploadedFileSuppliers.forEach( ( importFileSupplier ) -> {
            uploadService.submit( newUploader( importFileSupplier, dirname, session ) );
            count.incrementAndGet();
        } );

        Map<String, KojijiErrorInfo> uploadErrors = new HashMap<>();
        Set<UploadResponse> responses = new HashSet<>();
        int total = count.get();
        while ( count.getAndDecrement() > 0 )
        {
            logger.debug( "Waiting for {} uploads.", count.get() + 1 );

            try
            {
                Future<KojiUploaderResult> future = uploadService.take();
                KojiUploaderResult result = future.get();
                Exception error = result.getError();
                if ( error != null )
                {
                    uploadErrors.put(
                            result.getUploadFilePath(), new KojijiErrorInfo( result.getUploadFilePath(), error, result.isTemporaryError() ) );
                }
                else
                {
                    responses.add( result.getResponse() );
                }
            }
            catch ( InterruptedException e )
            {
                logger.debug( "Interrupted while uploading. Aborting upload." );
                break;
            }
            catch ( ExecutionException e )
            {
                throw new KojiClientException( "Failed to execute %d uploads for: %s. Reason: %s", e, total, buildInfo,
                                               e.getMessage() );
            }
        }

        return uploadErrors;
    }
*/

    protected Callable<KojiUploaderResult> newUploader( Supplier<ImportFile> importFileSupplier, String dirname, KojiSessionInfo session )
    {
        return () -> {

            ImportFile importFile = importFileSupplier.get();
            KojiUploaderResult result = new KojiUploaderResult( importFile );

            try
            {
                File f = new File( importFile.getFilePath() );
                String fname = f.getName();
                String fullDir = f.getParent() == null ? dirname : Paths.get( dirname, f.getParent() ).toString();

                result.setResponse(
                        upload( importFile.getStream(), fname, importFile.getSize(), fullDir,
                                session ) );
            }
            catch ( ConnectionPoolTimeoutException e )
            {
                result.setError( e, true );
            }
            catch ( KojiClientException e )
            {
                result.setError( e, false );
            }

            return result;
        };
    }

    protected UploadResponse upload( InputStream stream, String filepath, long size, String uploadDir, KojiSessionInfo session )
            throws KojiClientException, ConnectionPoolTimeoutException
    {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try
        {
            client = httpFactory.createClient( config.getKojiSiteConfig() );

            String url = sessionUrlBuilder( session, () -> {
                Map<String, Object> params = new HashMap<>();

                try
                {
                    params.put( UPLOAD_DIR_PARAM, encodeParam( UPLOAD_DIR_PARAM, uploadDir ) );
                    params.put( UPLOAD_CHECKSUM_TYPE_PARAM, ADLER_32_CHECKSUM );
                    params.put( UPLOAD_FILENAME_PARAM, encodeParam( UPLOAD_FILENAME_PARAM, filepath ) );
                    params.put( UPLOAD_OFFSET_PARAM, Integer.toString( 0 ) );
                    params.put( UPLOAD_OVERWRITE_PARAM, Integer.toString( 1 ) );
                }
                catch ( MalformedURLException e )
                {
                    params.put( EMBEDDED_ERROR_PARAM, e );
                }

                return params;
            } ).buildUrl( config.getKojiURL() ).throwError().get();

            HttpPost request = new HttpPost( url );
            request.setEntity( new InputStreamEntity( stream, size, ContentType.APPLICATION_OCTET_STREAM ) );

            response = client.execute( request );

            if ( response.getStatusLine().getStatusCode() == 200 )
            {
                return new RWXMapper().parse( response.getEntity().getContent(), UploadResponse.class );
            }
            else
            {
                throw new KojiClientException( "Failed to upload: %s to dir: %s. Server response: %s", filepath,
                                               uploadDir, response.getStatusLine() );
            }
        }
        catch ( ConnectionPoolTimeoutException e )
        {
            throw e;
        }
        catch ( IOException | JHttpCException | XmlRpcException e )
        {
            throw new KojiClientException( "Failed to upload: %s to dir: %s. Reason: %s", e, filepath, uploadDir,
                                           e.getMessage() );
        }
        finally
        {
            closeQuietly( response );
            closeQuietly( client );
        }
    }

    private String encodeParam( String param, String value )
            throws MalformedURLException
    {
        try
        {
            return URLEncoder.encode( value, "UTF-8" );
        }
        catch ( UnsupportedEncodingException e )
        {
            String msg = String.format( "Failed to encode %s parameter: %s. Reason: %s", param, value, e.getMessage() );
            logger.error( msg, e );

            throw new MalformedURLException( msg );
        }
    }
}
