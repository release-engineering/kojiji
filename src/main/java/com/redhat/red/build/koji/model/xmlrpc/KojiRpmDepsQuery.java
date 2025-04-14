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
package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

@StructPart
public class KojiRpmDepsQuery
                extends KojiQuery
{
    @DataKey( "depType" )
    private Integer depType;

    public KojiRpmDepsQuery()
    {

    }

    public Integer getDepType()
    {
        return depType;
    }

    public void setDepType( Integer depType )
    {
        this.depType = depType;
    }

    public KojiRpmDepsQuery withDepType( Integer depType )
    {
        this.depType = depType;
        return this;
    }

    @Override
    public String toString()
    {
        return "KojiRpmDepsQuery{" +
                "depType=" + depType +
                ", " + super.toString() +
                "}";
    }
}
