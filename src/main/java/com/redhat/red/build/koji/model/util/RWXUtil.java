package com.redhat.red.build.koji.model.util;

import org.commonjava.rwx.vocab.Nil;

/**
 * Created by ruhan on 1/9/18.
 */
public class RWXUtil
{
    public static boolean isBlankObj( Object xmlrpcObj )
    {
        return ( xmlrpcObj == null ) || ( xmlrpcObj instanceof Nil );
    }
}
