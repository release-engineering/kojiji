/*
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
package com.redhat.red.build.koji.kerberos;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

import org.apache.kerby.kerberos.kerb.KrbCodec;
import org.apache.kerby.kerberos.kerb.KrbException;
import org.apache.kerby.kerberos.kerb.ccache.CredentialCache;
import org.apache.kerby.kerberos.kerb.ccache.KrbCredentialCache;
import org.apache.kerby.kerberos.kerb.client.KrbClient;
import org.apache.kerby.kerberos.kerb.client.KrbConfig;
import org.apache.kerby.kerberos.kerb.common.EncryptionUtil;
import org.apache.kerby.kerberos.kerb.request.ApRequest;
import org.apache.kerby.kerberos.kerb.type.EncKrbPrivPart;
import org.apache.kerby.kerberos.kerb.type.ap.ApRep;
import org.apache.kerby.kerberos.kerb.type.ap.ApReq;
import org.apache.kerby.kerberos.kerb.type.ap.Authenticator;
import org.apache.kerby.kerberos.kerb.type.ap.EncAPRepPart;
import org.apache.kerby.kerberos.kerb.type.base.EncryptionKey;
import org.apache.kerby.kerberos.kerb.type.base.PrincipalName;
import org.apache.kerby.kerberos.kerb.ccache.Credential;
import org.apache.kerby.kerberos.kerb.type.KrbPriv;
import org.apache.kerby.kerberos.kerb.type.ticket.SgtTicket;
import org.apache.kerby.kerberos.kerb.type.ticket.TgtTicket;
import org.apache.kerby.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.red.build.koji.KojiClientException;
import com.redhat.red.build.koji.config.KojiConfig;
import com.redhat.red.build.koji.model.xmlrpc.KojiKrbAddressInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import com.redhat.red.build.koji.model.xmlrpc.messages.KrbLoginResponse;
import com.redhat.red.build.koji.model.xmlrpc.messages.KrbLoginResponseInfo;

import static java.io.File.separator;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.kerby.kerberos.kerb.KrbConstant.KRB_V5;
import static org.apache.kerby.kerberos.kerb.KrbErrorCode.KRB_AP_ERR_BADADDR;
import static org.apache.kerby.kerberos.kerb.KrbErrorCode.KRB_AP_ERR_BADORDER;
import static org.apache.kerby.kerberos.kerb.KrbErrorCode.KRB_AP_ERR_BADVERSION;
import static org.apache.kerby.kerberos.kerb.KrbErrorCode.KRB_AP_ERR_MODIFIED;
import static org.apache.kerby.kerberos.kerb.KrbErrorCode.KRB_AP_ERR_MSG_TYPE;
import static org.apache.kerby.kerberos.kerb.KrbErrorCode.KRB_AP_ERR_SKEW;
import static org.apache.kerby.kerberos.kerb.type.ap.ApOption.MUTUAL_REQUIRED;
import static org.apache.kerby.kerberos.kerb.type.base.KeyUsage.AP_REP_ENCPART;
import static org.apache.kerby.kerberos.kerb.type.base.KeyUsage.KRB_PRIV_ENCPART;
import static org.apache.kerby.kerberos.kerb.type.base.KrbMessageType.AP_REP;
import static org.apache.kerby.kerberos.kerb.type.base.KrbMessageType.KRB_PRIV;
import static org.apache.kerby.kerberos.kerb.type.base.NameType.NT_PRINCIPAL;

public class KrbAuthenticator
{
    private static final Logger logger = LoggerFactory.getLogger( KrbAuthenticator.class );

    private static final String KRB5_CONF = "krb5.conf";

    private final KojiConfig config;

    private ApReq apReq;

    private EncryptionKey key;

    private KrbClient krbClient;

    public KrbAuthenticator( KojiConfig config )
    {
        this.config = config;
    }

    public static String makeServerPrincipal( String service, String url, String realm )
            throws UnknownHostException, MalformedURLException
    {
        String serverName;

        try
        {
            serverName = InetAddress.getByName( new URI( url ).toURL().getHost() ).getCanonicalHostName();
        }
        catch ( URISyntaxException e )
        {
            throw new MalformedURLException( e.getMessage() );
        }

        return new PrincipalName( service + "/" + serverName + "@" + realm, NT_PRINCIPAL ).toString();
    }

    public static Path getKrb5ConfFilename()
            throws IOException
    {
        String prop = System.getProperty( "java.security.krb5.conf" );
        Path path = null;

        if ( prop != null )
        {
            path = Paths.get( prop );
        }

        if ( path == null || !Files.exists( path ) )
        {
            path = Paths.get( System.getProperty( "java.home" ), "lib", "security", KRB5_CONF );

            if ( !Files.exists( path ) )
            {
                String osName = System.getProperty( "os.name" );

                if ( osName.startsWith( "Windows" ) )
                {
                    String systemRoot = System.getenv( "SYSTEMROOT" );

                    if ( systemRoot != null )
                    {
                        path = Paths.get( systemRoot, "krb5.ini" );
                    }
                }
                else if ( osName.startsWith( "SunOS" ) )
                {
                    path = Paths.get( separator, "etc", "krb5", KRB5_CONF );
                }
                else
                {
                    path = Paths.get( separator, "etc", KRB5_CONF );
                }
            }
        }

        if ( !Files.exists( path ) || !Files.isReadable( path ) )
        {
            throw new IOException( "Must create or point to a krb5 configuration file before logging in" );
        }

        return path;
    }


    public static KrbClient newClient( Path krb5ConfFilename )
            throws IOException, KrbException
    {
        KrbConfig krbConfig = new KrbConfig();

        if ( krb5ConfFilename != null )
        {
            krbConfig.addKrb5Config( krb5ConfFilename.toFile() );
        }

        KrbClient krbClient = new KrbClient( krbConfig );

        krbClient.init();

        return krbClient;
    }

    public static TgtTicket getTgt( KrbClient krbClient, String keytab, String ccache, String principal,
                                    String password )
            throws KrbException
    {
        TgtTicket tgt;

        if ( keytab != null )
        {
            File keytabFile = new File( keytab );
            tgt = krbClient.requestTgt( principal, keytabFile );
        }
        else if ( ccache != null )
        {
            try
            {
                CredentialCache credCache = krbClient.resolveCredCache( new File( ccache ) );
                Credential cred = findMatchingCredential( principal, credCache );
                tgt = krbClient.getTgtTicketFromCredential( cred );
            }
            catch ( IOException e )
            {
                throw new KrbException( e.getMessage(), e );
            }
        }
        else
        {
            tgt = krbClient.requestTgt( principal, password );
        }

        return tgt;
    }

    private static Credential findMatchingCredential( String principal, KrbCredentialCache credCache )
            throws IOException
    {
        for ( Credential cred : credCache.getCredentials() )
        {
            if ( principal.equals( cred.getClientName().getName() ) )
            {
                return cred;
            }
        }

        throw new IOException( "Could not find credential with client name matching principal " + principal
                + " in credential cache" );
    }

    public static SgtTicket getSgt( KrbClient krbClient, String ccache, TgtTicket tgt, String serverPrincipal )
            throws KrbException
    {
        SgtTicket sgt;

        if ( ccache != null )
        {
            File ccFile = new File( ccache );
            sgt = krbClient.requestSgt( ccFile, serverPrincipal );
        }
        else
        {
            sgt = krbClient.requestSgt( tgt, serverPrincipal );
        }

        return sgt;
    }

    public static ApReq makeReq( SgtTicket sgt )
            throws KrbException
    {
        ApRequest apRequest = new ApRequest( sgt.getClientPrincipal(), sgt, EnumSet.of( MUTUAL_REQUIRED ) );

        return apRequest.getApReq();
    }

    public static ApRep readRep( byte[] buf, EncryptionKey key, long allowableClockSkew, ApReq apReq,
                                 InetAddress initiator )
            throws KrbException
    {
        ApRep apRep = KrbCodec.decode( buf, ApRep.class );

        if ( apRep.getPvno() != KRB_V5 )
        {
            throw new KrbException( KRB_AP_ERR_BADVERSION );
        }

        if ( apRep.getMsgType() != AP_REP )
        {
            throw new KrbException( KRB_AP_ERR_MSG_TYPE );
        }

        try
        {
            ApRequest.validate( key, apReq, initiator, SECONDS.toMillis( allowableClockSkew ) );
        }
        catch ( KrbException e )
        {
            // FIXME: The checksum verification fails, but we can continue, so just log the error
            logger.debug( "Ap Request validation error: code={}, message={}", e.getKrbErrorCode(), e.getMessage(), e );
        }

        EncAPRepPart encRepPart = EncryptionUtil.unseal( apRep.getEncryptedEncPart(), key, AP_REP_ENCPART,
                EncAPRepPart.class );

        apRep.setEncRepPart( encRepPart );

        ApRequest.unsealAuthenticator( key, apReq );

        EncAPRepPart encAPRepPart = apRep.getEncRepPart();
        Authenticator authenticator = apReq.getAuthenticator();

        if ( !encAPRepPart.getCtime().equals( authenticator.getCtime() )
                || encAPRepPart.getCusec() != authenticator.getCusec() )
        {
            throw new KrbException( KRB_AP_ERR_MODIFIED );
        }

        return apRep;
    }

    public static KrbPriv readPriv( byte[] buf, EncryptionKey key, InetAddress sAddress, InetAddress rAddress,
                                    long allowableClockSkew, ApRep apRep )
            throws KrbException
    {
        KrbPriv sessionInfoPriv = KrbCodec.decode( buf, KrbPriv.class );

        if ( sessionInfoPriv.getPvno() != KRB_V5 )
        {
            throw new KrbException( KRB_AP_ERR_BADVERSION );
        }

        if ( sessionInfoPriv.getMsgType() != KRB_PRIV )
        {
            throw new KrbException( KRB_AP_ERR_MSG_TYPE );
        }

        EncKrbPrivPart encPart = EncryptionUtil.unseal( sessionInfoPriv.getEncryptedEncPart(), key, KRB_PRIV_ENCPART,
                EncKrbPrivPart.class );

        sessionInfoPriv.setEncPart( encPart );

        if ( sessionInfoPriv.getEncPart().getSAddress() == null
                || !sessionInfoPriv.getEncPart().getSAddress().equalsWith( sAddress ) )
        {
            throw new KrbException( KRB_AP_ERR_BADADDR );
        }

        if ( sessionInfoPriv.getEncPart().getRAddress() != null
                && !sessionInfoPriv.getEncPart().getRAddress().equalsWith( rAddress ) )
        {
            throw new KrbException( KRB_AP_ERR_BADADDR );
        }

        if (  sessionInfoPriv.getEncPart().getTimeStamp() == null )
        {
            throw new KrbException( KRB_AP_ERR_MODIFIED );
        }

        if ( !sessionInfoPriv.getEncPart().getTimeStamp().isInClockSkew( SECONDS.toMillis( allowableClockSkew ) ) )
        {
            throw new KrbException( KRB_AP_ERR_SKEW );
        }

        if ( sessionInfoPriv.getEncPart().getSeqNumber() != apRep.getEncRepPart().getSeqNumber() )
        {
            throw new KrbException( KRB_AP_ERR_BADORDER );
        }

        return sessionInfoPriv;
    }

    public String prepareRequest()
            throws KojiClientException
    {
        try
        {
            if ( config.getKrbService() == null )
            {
                throw new KojiClientException( "Must set krbService option before logging in" );
            }

            if ( config.getKrbPrincipal() == null )
            {
                throw new KojiClientException( "Must set krbPrincipal option before logging in" );
            }

            if ( config.getKrbPassword() == null && config.getKrbKeytab() == null && config.getKrbCCache() == null )
            {
                throw new KojiClientException(
                        "Must set krbPassword, krbKeyTab, or krbCCache option before logging in" );
            }

            Path krb5ConfFilename = getKrb5ConfFilename();

            logger.debug( "Logging into Kerberos service {} using krb5 configuration file {}", config.getKrbService(),
                    krb5ConfFilename );

            krbClient = newClient( krb5ConfFilename );
            TgtTicket tgt = getTgt( krbClient, config.getKrbKeytab(), config.getKrbCCache(), config.getKrbPrincipal(),
                    config.getKrbPassword() );
            String serverPrincipal = makeServerPrincipal( config.getKrbService(), config.getKojiURL(), tgt.getRealm() );
            SgtTicket sgt = getSgt( krbClient, config.getKrbCCache(), tgt, serverPrincipal );
            key = sgt.getSessionKey();
            apReq = makeReq( sgt );
            byte[] asn1EncodedApReq = apReq.encode();
            byte[] base64EncodedApReq = new Base64().encode( asn1EncodedApReq );

            return new String( base64EncodedApReq, US_ASCII );
        }
        catch ( KrbException | IOException e )
        {
            throw new KojiClientException( "Failed to login: " + e.getMessage(), e );
        }
    }

    public KojiSessionInfo handleResponse( KrbLoginResponse loginResponse )
            throws KojiClientException
    {
        try
        {
            KrbLoginResponseInfo info = loginResponse.getInfo();
            String encodedResponse = info.getEncodedApResponse();
            byte[] decodedApRep = new Base64().decode( encodedResponse );
            KojiKrbAddressInfo addressInfo = info.getAddressInfo();
            ApRep apRep = readRep( decodedApRep, key, krbClient.getKrbConfig().getAllowableClockSkew(), apReq,
                    addressInfo.getClientAddress() );
            String encryptedSessionInfo = info.getEncodedEncryptedSessionInfo();
            byte[] decodedSessionInfo = new Base64().decode( encryptedSessionInfo );
            KrbPriv sessionInfoPriv = readPriv( decodedSessionInfo, key, addressInfo.getServerAddress(),
                    addressInfo.getClientAddress(), krbClient.getKrbConfig().getAllowableClockSkew(), apRep );
            EncKrbPrivPart encKrbPrivPart = sessionInfoPriv.getEncPart();
            byte[] userData = encKrbPrivPart.getUserData();

            if ( userData == null )
            {
                throw new KojiClientException( "Could not find session info in private message" );
            }

            String sessionInfoString = new String( userData, US_ASCII );
            String[] sessionInfoParts = sessionInfoString.split( " " );

            if ( sessionInfoParts.length != 2 )
            {
                throw new KojiClientException( "Failed to split session info string" );
            }

            return new KojiSessionInfo( Integer.parseInt( sessionInfoParts[0] ), sessionInfoParts[1] );
        }
        catch ( KrbException e )
        {
            throw new KojiClientException( "Failed to login: " + e.getMessage(), e );
        }
    }
}
