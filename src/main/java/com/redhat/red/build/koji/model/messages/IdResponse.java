package com.redhat.red.build.koji.model.messages;

import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Response;

/**
 * Created by jdcasey on 1/11/16.
 */
@Response
public class IdResponse
{
    @DataIndex( 0 )
    private int id;

    @IndexRefs( 0 )
    public IdResponse( int id )
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
