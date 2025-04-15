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

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.TAG_BUILD;

/**
 * Created by jdcasey on 8/8/16.
 */
@Request( method = TAG_BUILD )
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
