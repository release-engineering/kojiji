package com.redhat.red.build.koji.model.messages;

import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Response;

/**
 * Created by jdcasey on 1/11/16.
 */
@Response
public class ConfirmationResponse
{
    @DataIndex( 0 )
    private boolean success;

    @IndexRefs( 0 )
    public ConfirmationResponse( boolean success )
    {
        this.success = success;
    }

    public boolean isSuccess()
    {
        return success;
    }
}
