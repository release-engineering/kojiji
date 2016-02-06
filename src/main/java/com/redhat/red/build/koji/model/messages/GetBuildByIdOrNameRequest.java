package com.redhat.red.build.koji.model.messages;

import com.redhat.red.build.koji.model.KojiIdOrName;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 1/29/16.
 */
@Request( method="getBuild" )
public class GetBuildByIdOrNameRequest
{
    @DataIndex( 0 )
    private KojiIdOrName buildIdOrName;

    public GetBuildByIdOrNameRequest(){}

    public GetBuildByIdOrNameRequest( String buildName )
    {
        this.buildIdOrName = new KojiIdOrName( buildName );
    }

    public GetBuildByIdOrNameRequest( int buildId )
    {
        this.buildIdOrName = new KojiIdOrName( buildId );
    }

    public KojiIdOrName getBuildIdOrName()
    {
        return buildIdOrName;
    }

    public void setBuildIdOrName( KojiIdOrName buildIdOrName )
    {
        this.buildIdOrName = buildIdOrName;
    }
}
