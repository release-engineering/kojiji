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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.PACKAGE_LIST_ADD;

/**
 * Created by jdcasey on 8/8/16.
 */
@Request( method = PACKAGE_LIST_ADD )
public class AddPackageToTagRequest
{
    @DataIndex( 0 )
    private KojiIdOrName tag;

    @DataIndex( 1 )
    private String packageName;

    @DataIndex( 2 )
    private String ownerName;

    public AddPackageToTagRequest()
    {
    }

    public AddPackageToTagRequest( String tagName, String packageName, String ownerName)
    {
        this.tag = new KojiIdOrName( tagName );
        this.packageName = packageName;
        this.ownerName = ownerName;
    }

    public AddPackageToTagRequest( int tagId, String packageName, String ownerName)
    {
        this.tag = new KojiIdOrName( tagId );
        this.packageName = packageName;
        this.ownerName = ownerName;
    }

    public KojiIdOrName getTag()
    {
        return tag;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setTag( KojiIdOrName tag )
    {
        this.tag = tag;
    }

    public void setPackageName( String packageName )
    {
        this.packageName = packageName;
    }

    public void setOwnerName( String ownerName )
    {
        this.ownerName = ownerName;
    }
}
