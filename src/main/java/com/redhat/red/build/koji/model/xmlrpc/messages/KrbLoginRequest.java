package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

@Request( method="krbLogin" )
public class KrbLoginRequest
{
    @DataIndex( 0 )
    private String krbRequest;

    public KrbLoginRequest( String krbRequest )
    {
        this.krbRequest = krbRequest;
    }

    public KrbLoginRequest()
    {
    }

    public void setKrbRequest( String krbRequest )
    {
        this.krbRequest = krbRequest;
    }

    public String getKrbRequest()
    {
        return krbRequest;
    }
}
