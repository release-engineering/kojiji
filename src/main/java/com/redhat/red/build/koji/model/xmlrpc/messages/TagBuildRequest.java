package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import com.redhat.red.build.koji.model.xmlrpc.KojiNVR;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 8/8/16.
 */
@Request( method = "packageListAdd" )
public class TagBuildRequest
{
    @DataIndex( 0 )
    private KojiIdOrName tag;

    @DataIndex( 1 )
    private KojiIdOrName build;

    @DataIndex( 2 )
    private Boolean force;

    @DataIndex( 3 )
    private KojiIdOrName fromTag;

    public TagBuildRequest(){}

    public TagBuildRequest( String tagName, String buildNVR )
    {
        this.tag = new KojiIdOrName( tagName );
        this.build = new KojiIdOrName( buildNVR );
    }

    public TagBuildRequest( int tagId, int buildId )
    {
        this.tag = new KojiIdOrName( tagId );
        this.build = new KojiIdOrName( buildId );
    }

    public KojiIdOrName getTag()
    {
        return tag;
    }

    public void setTag( KojiIdOrName tag )
    {
        this.tag = tag;
    }

    public KojiIdOrName getBuild()
    {
        return build;
    }

    public void setBuild( KojiIdOrName build )
    {
        this.build = build;
    }

    public Boolean getForce()
    {
        return force;
    }

    public void setForce( Boolean force )
    {
        this.force = force;
    }

    public KojiIdOrName getFromTag()
    {
        return fromTag;
    }

    public void setFromTag( KojiIdOrName fromTag )
    {
        this.fromTag = fromTag;
    }

    public TagBuildRequest withTag( String name )
    {
        this.tag = new KojiIdOrName( name );
        return this;
    }

    public TagBuildRequest withTag( int buildId )
    {
        this.tag = new KojiIdOrName( buildId );
        return this;
    }

    public TagBuildRequest withBuild( String nvr )
    {
        this.build = new KojiIdOrName( nvr );
        return this;
    }

    public TagBuildRequest withBuild( int buildId )
    {
        this.build = new KojiIdOrName( buildId );
        return this;
    }

    public TagBuildRequest withBuild( KojiNVR nvr )
    {
        this.build = new KojiIdOrName( nvr.renderString() );
        return this;
    }

    public TagBuildRequest withFromTag( String name )
    {
        this.fromTag = new KojiIdOrName( name );
        return this;
    }

    public TagBuildRequest withFromTag( int buildId )
    {
        this.fromTag = new KojiIdOrName( buildId );
        return this;
    }

    public TagBuildRequest withForce(Boolean force) {
      this.force = force;
      return this;
    }

}
