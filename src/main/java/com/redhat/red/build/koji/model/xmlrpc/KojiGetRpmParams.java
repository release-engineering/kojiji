/*
 * Copyright (C) 2015 Red Hat, Inc.
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
public class KojiGetRpmParams
                extends KojiParams
{
    @DataKey( "strict" )
    private Boolean strict;

    @DataKey( "multi" )
    private Boolean multi = Boolean.TRUE;

    public KojiGetRpmParams()
    {

    }

    public Boolean getStrict()
    {
        return strict;
    }

    public void setStrict( Boolean strict )
    {
        this.strict = strict;
    }

    public KojiGetRpmParams withStrict( Boolean strict )
    {
        this.strict = strict;
        return this;
    }

    public Boolean getMulti()
    {
        return multi;
    }

    public void setMulti( Boolean multi )
    {
        this.multi = multi;
    }

    public KojiGetRpmParams withMulti( Boolean multi )
    {
        this.multi = multi;
        return this;
    }

    @Override
    public String toString()
    {
        return "KojiGetRpmParams{" +
                "strict=" + strict +
                ", multi=" + multi +
                ", " + super.toString() +
                "}";
    }
}
