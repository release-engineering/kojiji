package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.KeyRefs;
import org.commonjava.rwx.binding.anno.StructPart;

/**
 * Created by jdcasey on 5/6/16.
 */
@StructPart
public class KojiTagQuery
    extends KojiQuery
{
    @DataKey( "build" )
    private KojiIdOrName buildId;

    @KeyRefs( "build" )
    public KojiTagQuery( KojiBuildInfo buildInfo )
    {
        this.buildId = new KojiIdOrName( buildInfo.getId() );
    }

    public KojiTagQuery( KojiIdOrName buildId )
    {
        this.buildId = buildId;
    }

    public KojiTagQuery( KojiNVR nvr )
    {
        this.buildId = new KojiIdOrName( nvr.renderString() );
    }

    public KojiTagQuery( String nvr )
    {
        this.buildId = new KojiIdOrName( nvr );
    }

    public KojiTagQuery( int buildId )
    {
        this.buildId = new KojiIdOrName( buildId );
    }

    public KojiIdOrName getBuildId()
    {
        return buildId;
    }

    public void setBuildId( KojiIdOrName buildId )
    {
        this.buildId = buildId;
    }
}
