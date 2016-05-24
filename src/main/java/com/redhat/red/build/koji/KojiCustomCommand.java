package com.redhat.red.build.koji;

import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;

/**
 * Created by jdcasey on 5/23/16.
 */
public interface KojiCustomCommand<T>
{
    T execute( KojiSessionInfo session )
        throws KojiClientException;
}
