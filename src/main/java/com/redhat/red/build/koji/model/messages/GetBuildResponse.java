package com.redhat.red.build.koji.model.messages;

import com.redhat.red.build.koji.model.KojiBuildInfo;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Response;

/**
 * Created by jdcasey on 1/29/16.
 */
@Response
public class GetBuildResponse
{
    @DataIndex( 0 )
    private KojiBuildInfo buildInfo;

    @IndexRefs( 0 )
    public GetBuildResponse( KojiBuildInfo buildInfo )
    {
        this.buildInfo = buildInfo;
    }

    public KojiBuildInfo getBuildInfo()
    {
        return buildInfo;
    }
}
