/*
 * Copyright (C) 2015 Red Hat, Inc. (jcasey@redhat.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import com.redhat.red.build.koji.model.xmlrpc.KojiNVR;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.UNTAG_BUILD;

/**
 * Created by jdcasey on 8/8/16.
 */
@Request( method = UNTAG_BUILD )
public class UntagBuildRequest
{
    @DataIndex( 0 )
    private KojiIdOrName tag;

    @DataIndex( 1 )
    private KojiIdOrName build;

    @DataIndex( 2 )
    private boolean strict;

    @DataIndex( 3 )
    private boolean force;

    public UntagBuildRequest(){}

    public UntagBuildRequest( String tagName, String buildNVR )
    {
        this.tag = new KojiIdOrName( tagName );
        this.build = new KojiIdOrName( buildNVR );
    }

    public UntagBuildRequest( int tagId, int buildId )
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

    public boolean isForce()
    {
        return force;
    }

    public boolean getForce()
    {
        return force;
    }

    public void setForce( boolean force )
    {
        this.force = force;
    }

    public boolean isStrict()
    {
        return strict;
    }

    public boolean getStrict()
    {
        return strict;
    }

    public void setStrict( boolean strict )
    {
        this.strict = strict;
    }

    public UntagBuildRequest withTag( String name )
    {
        this.tag = new KojiIdOrName( name );
        return this;
    }

    public UntagBuildRequest withTag( int buildId )
    {
        this.tag = new KojiIdOrName( buildId );
        return this;
    }

    public UntagBuildRequest withBuild( String nvr )
    {
        this.build = new KojiIdOrName( nvr );
        return this;
    }

    public UntagBuildRequest withBuild( int buildId )
    {
        this.build = new KojiIdOrName( buildId );
        return this;
    }

    public UntagBuildRequest withBuild( KojiNVR nvr )
    {
        this.build = new KojiIdOrName( nvr.renderString() );
        return this;
    }

    public UntagBuildRequest withForce( boolean force) {
      this.force = force;
      return this;
    }

    public UntagBuildRequest withStrict( boolean strict) {
        this.strict = strict;
        return this;
    }

}
