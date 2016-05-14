package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;
import org.commonjava.rwx.binding.anno.Contains;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Response;

import java.util.List;

/**
 * Created by jdcasey on 5/6/16.
 */
@Response
public class ListTagsResponse
{
    @DataIndex( 0 )
    @Contains( KojiTagInfo.class )
    private final List<KojiTagInfo> tags;

    @IndexRefs( 0 )
    public ListTagsResponse( List<KojiTagInfo> tags )
    {
        this.tags = tags;
    }

    public List<KojiTagInfo> getTags()
    {
        return tags;
    }
}
