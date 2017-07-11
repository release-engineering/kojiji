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
package com.redhat.red.build.koji.kerberos;

import java.io.File;
import java.io.IOException;
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
import org.apache.kerby.kerberos.kerb.type.ticket.TgtTicket;

public class KrbUtils
{
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

    public static TgtTicket getTgt( KrbClient krbClient, String keytab, String principal, String password )
            throws KrbException
    {
        TgtTicket tgt = null;

        if ( keytab != null )
        {
            tgt = krbClient.requestTgt( principal, keytab );
        }
        else
        {
            tgt = krbClient.requestTgt( principal, password );
        }

        return tgt;
    }

    public static TgtTicket getSgt( KrbClient krbClient, String keytab, String password, TgtTicket tgt, String serverPrincipal )
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
}
