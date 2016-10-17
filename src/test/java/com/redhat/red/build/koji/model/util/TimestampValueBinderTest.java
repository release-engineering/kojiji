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
package com.redhat.red.build.koji.model.util;

import org.commonjava.rwx.binding.error.BindException;
import org.commonjava.rwx.binding.internal.xbr.XBRBindingContext;
import org.commonjava.rwx.error.XmlRpcException;
import org.commonjava.rwx.vocab.ValueType;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;

/**
 * Created by jdcasey on 9/19/16.
 */
public class TimestampValueBinderTest
{
    @Test
    public void parse_yyyyMMdd_hhmmss_noDecimals()
            throws Exception
    {
        Date result =
                (Date) new TimestampValueBinder( null, Date.class, new XBRBindingContext( Collections.emptyMap() ) ).getResult(
                        "2009-09-19 15:36:27", ValueType.STRING );

        System.out.println( result );
    }
}
