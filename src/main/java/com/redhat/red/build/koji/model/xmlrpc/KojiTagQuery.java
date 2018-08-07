/**
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
package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

/**
 * Created by jdcasey on 5/6/16.
 */
@StructPart
public class KojiTagQuery extends KojiQuery
{
    @DataKey( "build" )
    private KojiIdOrName buildId;

    @DataKey( "package" )
    private KojiIdOrName packageId;

    public KojiTagQuery(){}

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

    public KojiTagQuery withBuildId(KojiIdOrName buildId) {
      this.buildId = buildId;
      return this;
    }

    public KojiTagQuery withBuildId(String nvr) {
        this.buildId = new KojiIdOrName( nvr );
        return this;
    }

    public KojiTagQuery withBuildId(int buildId) {
        this.buildId = new KojiIdOrName( buildId );
        return this;
    }

    public KojiTagQuery withBuildId(KojiNVR nvr) {
        this.buildId = new KojiIdOrName( nvr.renderString() );
        return this;
    }

    public KojiIdOrName getPackageId()
    {
        return packageId;
    }

    public void setPackageId( KojiIdOrName packageId )
    {
        this.packageId = packageId;
    }

    public KojiTagQuery withPackageId(KojiIdOrName packageId) {
      this.packageId = packageId;
      return this;
    }

    public KojiTagQuery withPackageId(String nvr) {
        this.packageId = new KojiIdOrName( nvr );
        return this;
    }

    public KojiTagQuery withPackageId(int packageId) {
        this.packageId = new KojiIdOrName( packageId );
        return this;
    }

}
