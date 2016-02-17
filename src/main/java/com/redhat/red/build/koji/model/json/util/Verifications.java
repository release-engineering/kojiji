package com.redhat.red.build.koji.model.json.util;

import java.util.Set;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by jdcasey on 2/16/16.
 */
public final class Verifications
{

    public static void checkString( String value, Set<String> missing, String format, Object... params )
    {
        if ( isEmpty( value ) )
        {
            missing.add( String.format( format, params ) );
        }
    }

    public static void checkNull( Object value, Set<String> missing, String format, Object... params )
    {
        if ( value == null )
        {
            missing.add( String.format( format, params ) );
        }
    }

    private Verifications(){}
}
