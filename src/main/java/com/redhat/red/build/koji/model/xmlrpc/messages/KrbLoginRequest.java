package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

@Request( method="krbLogin" )
public class KrbLoginRequest
{
    @DataIndex( 0 )
    private String krbRequest;

    @IndexRefs( 0 )
    public KrbLoginRequest( String krbRequest )
    {
        this.krbRequest = krbRequest;
    }

    public String getKrbRequest()
    {
        return krbRequest;
    }
}
