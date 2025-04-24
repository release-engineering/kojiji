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

import com.redhat.red.build.koji.model.converter.KojiBooleanOrIntegerConverter;
import org.commonjava.rwx.anno.Converter;

@Converter( KojiBooleanOrIntegerConverter.class )
public class KojiBooleanOrInteger
{
    private Boolean booleanValue;

    private Integer integerValue;

    public KojiBooleanOrInteger()
    {

    }

    public KojiBooleanOrInteger( Boolean value )
    {
        this.booleanValue = value;
    }

    public KojiBooleanOrInteger( Integer value )
    {
        this.integerValue = value;
    }

    public Boolean getAsBoolean()
    {
        return booleanValue;
    }

    public void setAsBoolean( Boolean value )
    {
        this.booleanValue = value;
    }

    public Integer getAsInteger()
    {
        return integerValue;
    }

    public void setAsInteger( Integer value )
    {
        this.integerValue = value;
    }

    public static KojiBooleanOrInteger getFor( Object value )
    {
        if ( value == null )
        {
            return null;
        }

        if ( value instanceof Boolean )
        {
            return new KojiBooleanOrInteger( (Boolean) value );
        }

        if ( value instanceof Integer )
        {
            return new KojiBooleanOrInteger( (Integer) value );
        }

        return null;
    }

    @Override
    public String toString()
    {
        return "KojiBooleanOrInteger{" +
                "booleanValue='" + booleanValue + '\'' +
                ", integerValue=" + integerValue +
                '}';
    }
}
