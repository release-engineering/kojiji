package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiTagQuery;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 5/6/16.
 */
@Request( method = "listTags" )
public class ListTagsRequest
{
    @DataIndex( 0 )
    private final KojiTagQuery query;

    @IndexRefs( 0 )
    public ListTagsRequest( KojiTagQuery query )
    {
        this.query = query;
    }

    public KojiTagQuery getQuery()
    {
        return query;
    }
}
