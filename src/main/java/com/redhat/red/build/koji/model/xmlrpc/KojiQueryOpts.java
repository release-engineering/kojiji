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
public class KojiQueryOpts
{
    @DataKey( "countOnly" )
    private Boolean countOnly;

    @DataKey( "order" )
    private String order;

    @DataKey( "offset" )
    private Integer offset;

    @DataKey( "limit" )
    private Integer limit;

    @DataKey( "asList" )
    private Boolean asList;

    @DataKey( "rowlock" )
    private Boolean rowlock;

    public KojiQueryOpts()
    {

    }

    public Boolean getCountOnly()
    {
        return countOnly;
    }

    public void setCountOnly( Boolean countOnly )
    {
        this.countOnly = countOnly;
    }

    public KojiQueryOpts withCountOnly( Boolean countOnly )
    {
        this.countOnly = countOnly;
        return this;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder( String order )
    {
        this.order = order;
    }

    public KojiQueryOpts withOrder( String order )
    {
        this.order = order;
        return this;
    }

    public Integer getOffset()
    {
        return offset;
    }

    public void setOffset( Integer offset )
    {
        this.offset = offset;
    }

    public KojiQueryOpts withOffset( Integer offset )
    {
        this.offset = offset;
        return this;
    }

    public Integer getLimit()
    {
        return limit;
    }

    public void setLimit( Integer limit )
    {
        this.limit = limit;
    }

    public KojiQueryOpts withLimit( Integer limit )
    {
        this.limit = limit;
        return this;
    }

    public Boolean getAsList()
    {
        return asList;
    }

    public void setAsList( Boolean asList )
    {
        this.asList = asList;
    }

    public KojiQueryOpts withAsList( Boolean asList )
    {
        this.asList = asList;
        return this;
    }

    public Boolean getRowlock()
    {
        return rowlock;
    }

    public void setRowlock( Boolean rowlock )
    {
        this.rowlock = rowlock;
    }

    public KojiQueryOpts withRowlock( Boolean rowlock )
    {
        this.rowlock = rowlock;
        return this;
    }
}
