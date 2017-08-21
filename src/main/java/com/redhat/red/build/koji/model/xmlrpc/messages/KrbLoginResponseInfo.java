package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.commonjava.rwx.anno.ArrayPart;
import org.commonjava.rwx.anno.DataIndex;

import com.redhat.red.build.koji.model.xmlrpc.KojiKrbAddressInfo;

@ArrayPart
public class KrbLoginResponseInfo
{
    @DataIndex( 0 )
    String encodedApResponse;

    @DataIndex( 1 )
    String encodedEncryptedSessionInfo;

    @DataIndex( 2 )
    KojiKrbAddressInfo addressInfo;

    public KrbLoginResponseInfo( String encodedApResponse, String encodedEncryptedSessionInfo, KojiKrbAddressInfo addressInfo )
    {
        this.encodedApResponse = encodedApResponse;
        this.encodedEncryptedSessionInfo = encodedEncryptedSessionInfo;
        this.addressInfo = addressInfo;
    }

    public KrbLoginResponseInfo()
    {
    }

    public void setEncodedApResponse( String encodedApResponse )
    {
        this.encodedApResponse = encodedApResponse;
    }

    public void setEncodedEncryptedSessionInfo( String encodedEncryptedSessionInfo )
    {
        this.encodedEncryptedSessionInfo = encodedEncryptedSessionInfo;
    }

    public void setAddressInfo( KojiKrbAddressInfo addressInfo )
    {
        this.addressInfo = addressInfo;
    }

    public String getEncodedApResponse()
    {
        return encodedApResponse;
    }

    public String getEncodedEncryptedSessionInfo()
    {
        return encodedEncryptedSessionInfo;
    }

    public KojiKrbAddressInfo getAddressInfo()
    {
        return addressInfo;
    }
}
