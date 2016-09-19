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
