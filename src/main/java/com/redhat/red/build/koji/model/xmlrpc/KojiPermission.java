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

/**
 * Created by jdcasey on 1/6/16.
 */
@StructPart
public class KojiPermission
{
    @DataKey( "name" )
    private String name;

    @DataKey( "id" )
    private int id;

    public KojiPermission( int id, String name )
    {
        this.name = name;
        this.id = id;
    }

    public KojiPermission()
    {
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "KojiPermission{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
