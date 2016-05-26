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
import com.redhat.red.build.koji.model.ImportFile;
import com.redhat.red.build.koji.model.KojiImportResult;
import com.redhat.red.build.koji.model.json.KojiImport;
import com.redhat.red.build.koji.model.json.util.KojiObjectMapper;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiNVR;
import com.redhat.red.build.koji.model.xmlrpc.KojiPermission;
import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiUploaderResult;
import com.redhat.red.build.koji.model.xmlrpc.KojiUserInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcBindery;
import com.redhat.red.build.koji.model.xmlrpc.messages.AllPermissionsRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.AllPermissionsResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.ApiVersionRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ApiVersionResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.CGImportRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.CheckPermissionRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ConfirmationResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.CreateTagRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetBuildByIdOrNameRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetBuildByNVRObjRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetBuildResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.GetTagIdRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.IdResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListBuildsRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListBuildsResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListTagsRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.ListTagsResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoggedInUserRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoginRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.LoginResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.LogoutRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.StatusResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.TagRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.TagResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.UploadResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.UserRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.UserResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.binding.error.BindException;
import org.commonjava.rwx.error.XmlRpcException;
import org.commonjava.rwx.http.RequestModifier;
import org.commonjava.rwx.http.UrlBuildResult;
import org.commonjava.rwx.http.UrlBuilder;
import org.commonjava.rwx.http.httpclient4.HC4SyncObjectClient;
import org.commonjava.util.jhttpc.HttpFactory;
import org.commonjava.util.jhttpc.JHttpCException;
import org.commonjava.util.jhttpc.auth.MemoryPasswordManager;
import org.commonjava.util.jhttpc.auth.PasswordManager;
import org.commonjava.util.jhttpc.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.ACCEPT_ENCODING_HEADER;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.ADLER_32_CHECKSUM;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.CALL_NUMBER_PARAM;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.EMBEDDED_ERROR_PARAM;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.IDENTITY_ENCODING_VALUE;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.METADATA_JSON_FILE;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.SESSION_ID_PARAM;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.SESSION_KEY_PARAM;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.SSL_LOGIN_PATH;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.UPLOAD_CHECKSUM_TYPE_PARAM;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.UPLOAD_DIR_PARAM;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.UPLOAD_FILENAME_PARAM;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.UPLOAD_OFFSET_PARAM;
import static com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants.UPLOAD_OVERWRITE_PARAM;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.apache.http.client.utils.HttpClientUtils.closeQuietly;

/**
 * Created by jdcasey on 12/3/15.
 */
public class KojiClient
        implements Closeable
{
    private HC4SyncObjectClient xmlrpcClient;

    private HttpFactory httpFactory;

    private ExecutorService executorService;

    private ExecutorCompletionService<KojiUploaderResult> uploadService;

    private KojiObjectMapper objectMapper;

    private KojiConfig config;

    private KojiXmlRpcBindery bindery;

    private AtomicInteger callCount = new AtomicInteger( 0 );

    private static final RequestModifier STANDARD_REQUEST_MODIFIER = ( request ) -> {
        request.setHeader( ACCEPT_ENCODING_HEADER, IDENTITY_ENCODING_VALUE );
        Logger logger = LoggerFactory.getLogger( KojiClient.class );
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

            Logger logger = LoggerFactory.getLogger( KojiClient.class );
            logger.debug( "\n\n\n\nBuild URL: {}\n\n\n\n", result );
            return new UrlBuildResult( result );
        };
    }

    public KojiClient( KojiConfig config, PasswordManager passwordManager,
                       ExecutorService executorService )
            throws BindException
    {
        this.config = config;
        this.bindery = new KojiXmlRpcBindery();
        this.httpFactory = new HttpFactory( passwordManager );
        this.executorService = executorService;
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
        uploadService = new ExecutorCompletionService<KojiUploaderResult>( executorService );
        objectMapper = new KojiObjectMapper();

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
                logger.error(
                        "Cannot retrieve koji API version from: " + config.getKojiURL() + ". (Reason: " + e.getMessage()
                                + ")", e );
                xmlrpcClient.close();
                xmlrpcClient = null;
            }
        }
    }

    public int getApiVersion()
            throws KojiClientException
    {
        checkConnection();

        Logger logger = LoggerFactory.getLogger( getClass() );
        try
        {
            ApiVersionResponse response =
                    xmlrpcClient.call( new ApiVersionRequest(), ApiVersionResponse.class, NO_OP_URL_BUILDER,
                                       STANDARD_REQUEST_MODIFIER );

            return response.getApiVersion();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Cannot retrieve koji API version from: %s. (Reason: %s)", e,
                                           config.getKojiURL(), e.getMessage() );
        }
    }

    public KojiSessionInfo login()
            throws KojiClientException
    {
        checkConnection();

        try
        {
            UrlBuilder urlBuilder = ( url ) -> new UrlBuildResult( UrlUtils.buildUrl( url, SSL_LOGIN_PATH ) );

            RequestModifier requestModifier = ( request ) -> request.setHeader( ACCEPT_ENCODING_HEADER,
                                                                                IDENTITY_ENCODING_VALUE );

            LoginResponse loginResponse = xmlrpcClient.call( new LoginRequest(), LoginResponse.class,
                                                             urlBuilder,
                                                             requestModifier );

            return loginResponse == null ? null : loginResponse.getSessionInfo();
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
        finally
        {
            logout( session );
        }

        return result;
    }

    public KojiUserInfo getLoggedInUserInfo( String username )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            UserResponse response =
                    xmlrpcClient.call( new UserRequest( username ), UserResponse.class, NO_OP_URL_BUILDER,
                                       STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getUserInfo();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve current user info: %s", e, e.getMessage() );
        }
    }

    public KojiUserInfo getLoggedInUserInfo( KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            UserResponse response =
                    xmlrpcClient.call( new LoggedInUserRequest(), UserResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            return response == null ? null : response.getUserInfo();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve current user info: %s", e, e.getMessage() );
        }
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
                    Logger logger = LoggerFactory.getLogger( getClass() );
                    logger.error( "Failed to logout from Koji: {}", response.getError() );
                }
            }
            catch ( XmlRpcException e )
            {
                Logger logger = LoggerFactory.getLogger( getClass() );
                logger.error( String.format( "Failed to logout: %s", e.getMessage() ), e );
            }
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

    public Set<KojiPermission> getAllPermissions( KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();
        try
        {
            AllPermissionsResponse response =
                    xmlrpcClient.call( new AllPermissionsRequest(), AllPermissionsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getPermissions();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve listing of koji permissions: %s", e, e.getMessage() );
        }
    }

    public boolean hasPermission( String permission, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();
        try
        {
            ConfirmationResponse response =
                    xmlrpcClient.call( new CheckPermissionRequest( permission ), ConfirmationResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.isSuccess();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to check whether logged-in user has permission: %s. Reason: %s", e,
                                           permission, e.getMessage() );
        }
    }

    public int createTag( CreateTagRequest request, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            IdResponse response = xmlrpcClient.call( request, IdResponse.class, sessionUrlBuilder( session ),
                                                     STANDARD_REQUEST_MODIFIER );

            return response.getId();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to create tag: %s. Reason: %s", e, request, e.getMessage() );
        }
    }

    public KojiTagInfo getTag( int tagId, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            TagResponse response =
                    xmlrpcClient.call( new TagRequest( tagId ), TagResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            return response.getTagInfo();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve tag: %s. Reason: %s", e, tagId, e.getMessage() );
        }
    }

    public KojiTagInfo getTag( String tagName, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            TagResponse response =
                    xmlrpcClient.call( new TagRequest( tagName ), TagResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            return response.getTagInfo();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve tag: %s. Reason: %s", e, tagName, e.getMessage() );
        }
    }

    public Integer getTagId( String tagName, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            IdResponse response =
                    xmlrpcClient.call( new GetTagIdRequest( tagName ), IdResponse.class, sessionUrlBuilder( session ),
                                       STANDARD_REQUEST_MODIFIER );

            return response.getId();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve tag: %s. Reason: %s", e, tagName, e.getMessage() );
        }
    }

    public Integer getPackageId( String packageName, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            IdResponse response = xmlrpcClient.call( new GetTagIdRequest( packageName ), IdResponse.class,
                                                     sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getId();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve package: %s. Reason: %s", e, packageName,
                                           e.getMessage() );
        }
    }

    public KojiImportResult importBuild( KojiImport buildInfo, Supplier<Iterable<ImportFile>> outputSupplier,
                                         KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        String dirname = generateUploadDirname( session );
        Map<String, KojiClientException> uploadErrors = uploadForImport( buildInfo, outputSupplier, dirname, session );

        if ( !uploadErrors.isEmpty() )
        {
            return new KojiImportResult( buildInfo ).withUploadErrors( uploadErrors );
        }

        try
        {
            StatusResponse response = xmlrpcClient.call( new CGImportRequest( dirname ), StatusResponse.class,
                                                         sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            if ( !isEmpty( response.getError() ) )
            {
                throw new KojiClientException( "Error response from Koji server: %s", response.getError() );
            }

            KojiBuildInfo build = getBuildInfo( buildInfo.getBuildNVR(), session );

            return new KojiImportResult( buildInfo ).withBuildInfo( build );
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to execute content-generator import. Reason: %s", e,
                                           e.getMessage() );
        }
    }

    public List<KojiTagInfo> listTags( KojiBuildInfo buildInfo, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            ListTagsResponse response =
                    xmlrpcClient.call( new ListTagsRequest( new KojiTagQuery( buildInfo ) ), ListTagsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiTagInfo> tags = response.getTags();
            return tags == null ? Collections.emptyList() : tags;
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve list of tags for build: %s. Reason: %s", e, buildInfo,
                                           e.getMessage() );
        }
    }

    public List<KojiTagInfo> listTags( KojiNVR nvr, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            ListTagsResponse response =
                    xmlrpcClient.call( new ListTagsRequest( new KojiTagQuery( nvr ) ), ListTagsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiTagInfo> tags = response.getTags();
            return tags == null ? Collections.emptyList() : tags;
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve list of tags for build: %s. Reason: %s", e, nvr,
                                           e.getMessage() );
        }
    }

    public List<KojiTagInfo> listTags( String nvr, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            ListTagsResponse response =
                    xmlrpcClient.call( new ListTagsRequest( new KojiTagQuery( nvr ) ), ListTagsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiTagInfo> tags = response.getTags();
            return tags == null ? Collections.emptyList() : tags;
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve list of tags for build: %s. Reason: %s", e, nvr,
                                           e.getMessage() );
        }
    }

    public List<KojiTagInfo> listTags( int buildId, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            ListTagsResponse response =
                    xmlrpcClient.call( new ListTagsRequest( new KojiTagQuery( buildId ) ), ListTagsResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiTagInfo> tags = response.getTags();
            return tags == null ? Collections.emptyList() : tags;
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve list of tags for build: %s. Reason: %s", e, buildId,
                                           e.getMessage() );
        }
    }

    public List<KojiBuildInfo> listBuilds( ProjectVersionRef gav, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            ListBuildsResponse response = xmlrpcClient.call( new ListBuildsRequest( gav ), ListBuildsResponse.class,
                                                             sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            List<KojiBuildInfo> builds = response.getBuilds();
            return builds == null ? Collections.emptyList() : builds;
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve list of builds for: %s. Reason: %s", e, gav,
                                           e.getMessage() );
        }
    }

    public KojiBuildInfo getBuildInfo( KojiNVR nvr, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            GetBuildResponse response = xmlrpcClient.call( new GetBuildByNVRObjRequest( nvr ), GetBuildResponse.class,
                                                           sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getBuildInfo();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve build info for: %s. Reason: %s", e, nvr,
                                           e.getMessage() );
        }
    }

    public KojiBuildInfo getBuildInfo( String nvr, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            GetBuildResponse response = xmlrpcClient.call( new GetBuildByIdOrNameRequest( nvr ), GetBuildResponse.class,
                                                           sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getBuildInfo();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve build info for: %s. Reason: %s", e, nvr,
                                           e.getMessage() );
        }
    }

    public KojiBuildInfo getBuildInfo( int buildId, KojiSessionInfo session )
            throws KojiClientException
    {
        checkConnection();

        try
        {
            GetBuildResponse response =
                    xmlrpcClient.call( new GetBuildByIdOrNameRequest( buildId ), GetBuildResponse.class,
                                       sessionUrlBuilder( session ), STANDARD_REQUEST_MODIFIER );

            return response.getBuildInfo();
        }
        catch ( XmlRpcException e )
        {
            throw new KojiClientException( "Failed to retrieve build info for: %s. Reason: %s", e, buildId,
                                           e.getMessage() );
        }
    }

    protected String generateUploadDirname( KojiSessionInfo session )
            throws KojiClientException
    {
        KojiUserInfo userInfo = getLoggedInUserInfo( session );
        return String.format( "kojiji-upload/%s-%s/", new SimpleDateFormat( "yyyymmdd-hhMM" ).format( new Date() ),
                              userInfo.getKerberosPrincipal() );
    }

    protected Map<String, KojiClientException> uploadForImport( KojiImport buildInfo,
                                                                Supplier<Iterable<ImportFile>> outputSupplier,
                                                                String dirname, KojiSessionInfo session )
            throws KojiClientException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            objectMapper.writeValue( baos, buildInfo );
        }
        catch ( IOException e )
        {
            throw new KojiClientException( "Failed to serialize import info to JSON. Reason: %s", e, e.getMessage() );
        }

        AtomicInteger count = new AtomicInteger( 0 );
        uploadService.submit(
                newUploader( new ImportFile( METADATA_JSON_FILE, new ByteArrayInputStream( baos.toByteArray() ) ),
                             dirname, session ) );

        count.incrementAndGet();

        outputSupplier.get().forEach( ( importFile ) -> {
            uploadService.submit( newUploader( importFile, dirname, session ) );
            count.incrementAndGet();
        } );

        Logger logger = LoggerFactory.getLogger( getClass() );
        Map<String, KojiClientException> uploadErrors = new HashMap<>();
        Set<UploadResponse> responses = new HashSet<>();
        int total = count.get();
        do
        {
            logger.debug( "Waiting for %d uploads.", count.get() );

            try
            {
                Future<KojiUploaderResult> future = uploadService.take();
                KojiUploaderResult result = future.get();
                KojiClientException error = result.getError();
                if ( error != null )
                {
                    uploadErrors.put( result.getImportFile().getFilePath(), error );
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
        while ( count.decrementAndGet() > 0 );

        return uploadErrors;
    }

    protected Callable<KojiUploaderResult> newUploader( ImportFile importFile, String dirname, KojiSessionInfo session )
    {
        return () -> {

            KojiUploaderResult result = new KojiUploaderResult( importFile );

            try
            {
                result.setResponse( upload( importFile.getStream(), importFile.getFilePath(), dirname, session ) );
            }
            catch ( KojiClientException e )
            {
                result.setError( e );
            }

            return result;
        };
    }

    protected UploadResponse upload( InputStream stream, String filepath, String uploadDir, KojiSessionInfo session )
            throws KojiClientException
    {
        CloseableHttpClient client = null;
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
            request.setEntity( new InputStreamEntity( stream ) );

            CloseableHttpResponse response = client.execute( request );

            if ( response.getStatusLine().getStatusCode() == 200 )
            {
                return bindery.parse( response.getEntity().getContent(), UploadResponse.class );
            }
            else
            {
                throw new KojiClientException( "Failed to upload: %s to dir: %s. Server response: %s", filepath,
                                               uploadDir, response.getStatusLine() );
            }
        }
        catch ( IOException | JHttpCException | XmlRpcException e )
        {
            throw new KojiClientException( "Failed to upload: %s to dir: %s. Reason: %s", e, filepath, uploadDir,
                                           e.getMessage() );
        }
        finally
        {
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

            Logger logger = LoggerFactory.getLogger( getClass() );
            logger.error( msg, e );

            throw new MalformedURLException( msg );
        }
    }
}
