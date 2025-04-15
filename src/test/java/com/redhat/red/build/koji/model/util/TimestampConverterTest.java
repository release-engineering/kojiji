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
package com.redhat.red.build.koji.model.util;

import com.redhat.red.build.koji.model.converter.TimestampConverter;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by jdcasey on 9/19/16.
 */
public class TimestampConverterTest
{
    @Test
    public void parse_yyyyMMdd_hhmmss_noDecimals()
            throws Exception
    {
        TimestampConverter c = new TimestampConverter();

        String dateString = "2015-02-24 16:03:34.451205";
        Date date = c.parse( dateString );
        String rendered = c.render( date ).toString();
        System.out.println();
        assertEquals( "2015-02-24 16:03:34", rendered);
    }
}
