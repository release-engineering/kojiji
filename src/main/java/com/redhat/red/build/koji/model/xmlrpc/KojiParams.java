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

/**
 * The __starstar ** operator has to do with how python handles arguments, where the function parameters get passed as
 * a python dict (xml-rpc struct) instead of as normal. Presumably, the query is supposed to work either way,
 * but we are not handling the case where __starstar is false.
 */
@StructPart
public class KojiParams
{
    public static final String __STARSTAR = "__starstar";

    @DataKey( __STARSTAR )
    protected boolean enabled = true;

    public boolean isEnabled()
    {
        return enabled;
    }

    public boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

    @Override
    public String toString()
    {
        return "KojiParams{" +
                "enabled=" + enabled +
                "}";
    }

}
