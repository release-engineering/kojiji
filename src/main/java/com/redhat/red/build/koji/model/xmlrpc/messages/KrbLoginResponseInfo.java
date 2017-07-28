package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.commonjava.rwx.binding.anno.ArrayPart;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;

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

    @IndexRefs( { 0, 1, 2 } )
    public KrbLoginResponseInfo( String encodedApResponse, String encodedEncryptedSessionInfo, KojiKrbAddressInfo addressInfo )
    {
        this.encodedApResponse = encodedApResponse;
        this.encodedEncryptedSessionInfo = encodedEncryptedSessionInfo;
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
