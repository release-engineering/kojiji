package com.redhat.red.build.koji.model.messages;

import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 1/11/16.
 */
@Request( method="hasPerm" )
public class CheckPermissionRequest
{
    @DataIndex( 0 )
    private String role;

    @IndexRefs( 0 )
    public CheckPermissionRequest( String role )
    {
        this.role = role;
    }

    public String getRole()
    {
        return role;
    }
}
