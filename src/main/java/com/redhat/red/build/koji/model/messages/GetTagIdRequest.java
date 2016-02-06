package com.redhat.red.build.koji.model.messages;

import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 1/29/16.
 */
@Request( method="getTagID" )
public class GetTagIdRequest
{
    @DataIndex( 0 )
    private String name;

    @IndexRefs( 0 )
    public GetTagIdRequest( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
