package com.redhat.red.build.koji.model.messages;

import com.redhat.red.build.koji.model.KojiNVR;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 1/29/16.
 */
@Request( method="getBuild" )
public class GetBuildByNVRObjRequest
{
    @DataIndex( 0 )
    private KojiNVR nvr;

    public GetBuildByNVRObjRequest( KojiNVR nvr )
    {
        this.nvr = nvr;
    }

    public KojiNVR getNvr(){
        return nvr;
    }
}
