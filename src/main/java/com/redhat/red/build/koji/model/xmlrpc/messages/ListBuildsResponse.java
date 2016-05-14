package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import org.commonjava.rwx.binding.anno.Contains;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Response;

import java.util.List;

/**
 * Created by jdcasey on 1/29/16.
 */
@Response
public class ListBuildsResponse
{
    @DataIndex( 0 )
    @Contains( KojiBuildInfo.class )
    private List<KojiBuildInfo> builds;

    @IndexRefs( 0 )
    public ListBuildsResponse( List<KojiBuildInfo> builds )
    {
        this.builds = builds;
    }

    public List<KojiBuildInfo> getBuilds()
    {
        return builds;
    }
}
