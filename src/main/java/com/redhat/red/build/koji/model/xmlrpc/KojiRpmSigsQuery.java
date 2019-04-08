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

@StructPart
public class KojiRpmSigsQuery
        extends KojiQuery
{
    @DataKey( "rpm_id" )
    private Integer rpmId;

    @DataKey( "sigkey" )
    private String sigkey;

    public KojiRpmSigsQuery()
    {

    }

    public Integer getRpmId()
    {
        return rpmId;
    }

    public void setRpmId( Integer rpmId )
    {
        this.rpmId = rpmId;
    }

    public KojiRpmSigsQuery withRpmId( Integer rpmId )
    {
        this.rpmId = rpmId;
        return this;
    }

    public String getSigkey()
    {
        return sigkey;
    }

    public void setSigkey( String sigkey )
    {
        this.sigkey = sigkey;
    }

    public KojiRpmSigsQuery withSigkey( String sigkey )
    {
        this.sigkey = sigkey;
        return this;
    }

    @Override
    public String toString() {
        return "KojiRpmSigsQuery{" +
               "rpmId=" + rpmId +
               ", sigkey='" + sigkey +
               "', " +
               super.toString() +
               "}";
    }
}
