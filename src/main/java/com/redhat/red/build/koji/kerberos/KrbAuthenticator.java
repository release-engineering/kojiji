package com.redhat.red.build.koji.kerberos;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.kerby.KOptions;
import org.apache.kerby.kerberos.kerb.KrbCodec;
import org.apache.kerby.kerberos.kerb.KrbConstant;
import org.apache.kerby.kerberos.kerb.KrbErrorCode;
import org.apache.kerby.kerberos.kerb.KrbException;
import org.apache.kerby.kerberos.kerb.ccache.CredentialCache;
import org.apache.kerby.kerberos.kerb.client.KrbClient;
import org.apache.kerby.kerberos.kerb.client.KrbConfig;
import org.apache.kerby.kerberos.kerb.client.KrbOption;
import org.apache.kerby.kerberos.kerb.common.EncryptionUtil;
import org.apache.kerby.kerberos.kerb.type.EncKrbPrivPart;
import org.apache.kerby.kerberos.kerb.type.ap.ApRep;
import org.apache.kerby.kerberos.kerb.type.ap.ApReq;
import org.apache.kerby.kerberos.kerb.type.ap.Authenticator;
import org.apache.kerby.kerberos.kerb.type.ap.EncAPRepPart;
import org.apache.kerby.kerberos.kerb.type.base.EncryptionKey;
import org.apache.kerby.kerberos.kerb.type.base.KeyUsage;
import org.apache.kerby.kerberos.kerb.type.base.KrbMessageType;
import org.apache.kerby.kerberos.kerb.type.base.NameType;
import org.apache.kerby.kerberos.kerb.type.base.PrincipalName;
import org.apache.kerby.kerberos.kerb.ccache.Credential;
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

public class KrbAuthenticator
{
    private KojiConfig config;
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
        String serverName = InetAddress.getByName( new URL( url ).getHost() ).getCanonicalHostName();

        String serverPrincipal = new PrincipalName( service + "/" + serverName + "@" + realm, NameType.NT_UNKNOWN ).toString();

        return serverPrincipal;
    }

    public static String getKrb5ConfFilename()
    {
        String filename = System.getProperty( "java.security.krb5.conf" );

        if ( filename == null )
        {
            filename = System.getProperty( "java.home" ) + File.separator + "lib" + File.separator + "security" + File.separator + "krb5.conf";

            Path javaHomeLibSecurityPath = Paths.get( filename );

            if ( !Files.exists( javaHomeLibSecurityPath, new LinkOption[] { } ) )
            {
                String osName = System.getProperty( "os.name" );

                if ( osName.startsWith( "Windows" ) )
                {
                    String systemRoot = System.getenv( "SYSTEMROOT" );

                    if ( systemRoot != null )
                    {
                        filename = systemRoot + File.separator + "krb5.ini";

                    }
                }
                else if ( osName.startsWith( "SunOS" ) )
                {
                    filename = File.separator + "etc" + File.separator + "krb5" + File.separator + "krb5.conf";
                }
                else
                {
                    filename = File.separator + "etc" + File.separator + "krb5.conf";
                }
            }
        }

        Path filenamePath = Paths.get( filename );

        if ( !Files.exists( filenamePath, new LinkOption[] { } ) || !Files.isReadable( filenamePath ) )
        {
            filename = null;
        }

        return filename;
    }


    public static KrbClient newClient( String krb5ConfFilename )
            throws IOException, KrbException {
        KrbConfig krbConfig = new KrbConfig();

        if ( krb5ConfFilename != null )
        {
            krbConfig.addKrb5Config( new File( krb5ConfFilename ) );
        }

        KrbClient krbClient = new KrbClient( krbConfig );

        krbClient.init();

        return krbClient;
    }

    public static TgtTicket getTgt( KrbClient krbClient, String keytab, String ccache, String principal, String password )
            throws KrbException
    {
        TgtTicket tgt = null;

        if ( keytab != null )
        {
            tgt = krbClient.requestTgt( principal, keytab );
        }
        else if ( ccache != null )
        {
            try
            {
                CredentialCache cc = krbClient.resolveCredCache( new File( ccache ) );
                Credential cred = cc.getCredentials().get( 0 );
                tgt = krbClient.getTgtTicketFromCredential( cred );
            }
            catch ( IOException e )
            {
                throw new KrbException( e.getMessage() );
            }
        }
        else
        {
            tgt = krbClient.requestTgt( principal, password );
        }

        return tgt;
    }

    public static TgtTicket getSgt( KrbClient krbClient, String keytab, String ccache, String password, TgtTicket tgt, String serverPrincipal )
            throws KrbException
    {
        KOptions requestOptions = new KOptions();

        if ( keytab != null )
        {
            requestOptions.add( KrbOption.USE_KEYTAB, true );
            requestOptions.add( KrbOption.KEYTAB_FILE, keytab );
        }
        else
        {
            requestOptions.add( KrbOption.USE_PASSWD, true );
            requestOptions.add( KrbOption.USER_PASSWD, password );
        }

        requestOptions.add( KrbOption.CLIENT_PRINCIPAL, tgt.getClientPrincipal().getName() );
        requestOptions.add( KrbOption.SERVER_PRINCIPAL, serverPrincipal );
        requestOptions.add( KrbOption.USE_TGT, tgt );

        TgtTicket sgt = krbClient.requestTgt( requestOptions );

        return sgt;
    }

    public static ApReq makeReq( TgtTicket sgt )
            throws KrbException
    {
        ApRequest apRequest = new ApRequest( sgt.getClientPrincipal(), sgt );

        ApReq apReq = apRequest.getApReq();

        return apReq;
    }

    public static ApRep readRep( byte[] buf, EncryptionKey key, ApReq apReq )
            throws KrbException
    {
           ApRep apRep = KrbCodec.decode( buf, ApRep.class );

           if ( apRep.getPvno() != KrbConstant.KRB_V5 )
           {
               throw new KrbException( KrbErrorCode.KRB_AP_ERR_BADVERSION );
           }

           if ( !apRep.getMsgType().equals( KrbMessageType.AP_REP ) )
           {
               throw new KrbException( KrbErrorCode.KRB_AP_ERR_MSG_TYPE );
           }

           EncAPRepPart encRepPart = EncryptionUtil.unseal( apRep.getEncryptedEncPart(), key, KeyUsage.AP_REP_ENCPART, EncAPRepPart.class );

           apRep.setEncRepPart( encRepPart );

           ApRequest.unsealAuthenticator( key, apReq );

           EncAPRepPart encAPRepPart = apRep.getEncRepPart();

           Authenticator authenticator = apReq.getAuthenticator();

           if ( !encAPRepPart.getCtime().equals( authenticator.getCtime() ) || encAPRepPart.getCusec() != authenticator.getCusec() )
           {
               throw new KrbException( KrbErrorCode.KRB_AP_ERR_MODIFIED );
           }

           return apRep;
    }

    public static KrbPriv readPriv( byte[] buf, EncryptionKey key, InetAddress sAddress, InetAddress rAddress, long allowableClockSkew, ApRep apRep )
            throws KrbException
    {
        KrbPriv sessionInfoPriv = KrbCodec.decode( buf, KrbPriv.class );

        if ( sessionInfoPriv.getPvno() != KrbConstant.KRB_V5 )
        {
            throw new KrbException( KrbErrorCode.KRB_AP_ERR_BADVERSION );
        }

        if ( !sessionInfoPriv.getMsgType().equals( KrbMessageType.KRB_PRIV ) )
        {
            throw new KrbException( KrbErrorCode.KRB_AP_ERR_MSG_TYPE );
        }

        EncKrbPrivPart encPart = EncryptionUtil.unseal( sessionInfoPriv.getEncryptedEncPart(), key, KeyUsage.KRB_PRIV_ENCPART, EncKrbPrivPart.class );

        sessionInfoPriv.setEncPart( encPart );

        if ( sessionInfoPriv.getEncPart().getSAddress() == null || !sessionInfoPriv.getEncPart().getSAddress().equalsWith( sAddress ) )
        {
            throw new KrbException( KrbErrorCode.KRB_AP_ERR_BADADDR );
        }

        if ( sessionInfoPriv.getEncPart().getRAddress() != null && !sessionInfoPriv.getEncPart().getRAddress().equalsWith( rAddress ) )
        {
            throw new KrbException( KrbErrorCode.KRB_AP_ERR_BADADDR );
        }

        if (  sessionInfoPriv.getEncPart().getTimeStamp() == null )
        {
            throw new KrbException( KrbErrorCode.KRB_AP_ERR_MODIFIED );
        }

        if ( !sessionInfoPriv.getEncPart().getTimeStamp().isInClockSkew( allowableClockSkew * 1000 ) )
        {
            throw new KrbException( KrbErrorCode.KRB_AP_ERR_SKEW );
        }

        if ( sessionInfoPriv.getEncPart().getSeqNumber() != apRep.getEncRepPart().getSeqNumber() )
        {
            throw new KrbException( KrbErrorCode.KRB_AP_ERR_BADORDER );
        }

        return sessionInfoPriv;
    }

    public String prepareRequest() throws KojiClientException
    {
        try
        {
            if ( config.getKrbService() == null )
            {
                throw new KojiClientException( "Must set krbService option before logging in" );
            }

            if ( config.getKrbPrincipal() == null && ( config.getKrbKeytab() == null && config.getKrbCCache() == null && config.getKrbPassword() == null ) )
            {
                throw new KojiClientException( "Must set krbPrincipal and krbPassword or krbKeyTab or krbCCache options before logging in" );
            }

            String krb5ConfFilename = getKrb5ConfFilename();

            if ( krb5ConfFilename == null )
            {
                throw new KojiClientException( "Must create or point to a krb5 configuration file before logging in" );
            }

            Logger logger = LoggerFactory.getLogger( getClass() );

            logger.debug( "Logging into Kerberos service {} using krb5 configuration file {}", config.getKrbService(), krb5ConfFilename );

           this.krbClient = newClient( krb5ConfFilename );

            TgtTicket tgt = getTgt( krbClient, config.getKrbKeytab(), config.getKrbCCache(), config.getKrbPrincipal(), config.getKrbPassword() );

            String serverPrincipal = makeServerPrincipal( config.getKrbService(), config.getKojiURL(), tgt.getRealm() );

            TgtTicket sgt = getSgt( krbClient, config.getKrbKeytab(), config.getKrbCCache(), config.getKrbPassword(), tgt, serverPrincipal );

            this.key = sgt.getSessionKey();

            this.apReq = makeReq( sgt );
            byte[] asn1EncodedApReq = apReq.encode();
            byte[] base64EncodedApReq = new Base64().encode( asn1EncodedApReq );
            String encodedApReq = new String( base64EncodedApReq, "US-ASCII" );

            return encodedApReq;
        }
       catch ( KrbException e )
        {
            throw new KojiClientException( "Failed to login: %s", e, e.getMessage() );
        }
        catch ( IOException e )
        {
            throw new KojiClientException( "Failed to login: %s", e, e.getMessage() );
        }
    }

    public KojiSessionInfo hanldleResponse( KrbLoginResponse loginResponse ) throws KojiClientException
    {
        try
        {
            KrbLoginResponseInfo info = loginResponse.getInfo();
            String encodedResponse = info.getEncodedApResponse();
            KojiKrbAddressInfo addressInfo = info.getAddressInfo();
            String encodedEncryptedSessionInfo = info.getEncodedEncryptedSessionInfo();

            byte[] decodedApRep = new Base64().decode( encodedResponse );
            ApRep apRep = readRep( decodedApRep, key, apReq );

            byte[] decodedSessionInfoPriv = new Base64().decode( encodedEncryptedSessionInfo );
            KrbPriv sessionInfoPriv = readPriv( decodedSessionInfoPriv, key, InetAddress.getByName( addressInfo.getServerAddress() ), InetAddress.getByName( addressInfo.getClientAddress() ), krbClient.getKrbConfig().getAllowableClockSkew(), apRep );
            EncKrbPrivPart encKrbPrivPart = sessionInfoPriv.getEncPart();
            byte[] userData = encKrbPrivPart.getUserData();

            if ( userData == null )
            {
                throw new KojiClientException( "Could not find session info in private message" );
            }

            String sessionInfoString = new String( userData, "US-ASCII" );
            String[] sessionInfoParts = sessionInfoString.split(" ");

            if ( sessionInfoParts.length != 2 )
            {
                throw new KojiClientException( "Failed to split session info string" );
            }

            KojiSessionInfo session = new KojiSessionInfo( Integer.parseInt(sessionInfoParts[0]), sessionInfoParts[1] );

            return session;
        }
        catch ( KrbException e )
        {
            throw new KojiClientException( "Failed to login: %s", e, e.getMessage() );
        }
        catch ( UnknownHostException e )
        {
            throw new KojiClientException( "Failed to login: %s", e, e.getMessage() );
        }
        catch ( UnsupportedEncodingException e )
        {
            throw new KojiClientException( "Failed to login: %s", e, e.getMessage() );
        }
    }
}
