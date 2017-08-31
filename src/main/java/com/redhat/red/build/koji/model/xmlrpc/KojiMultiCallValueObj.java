package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.rwx.anno.ArrayPart;
import org.commonjava.rwx.anno.DataIndex;

/**
 * Created by ruhan on 8/4/17.
 */
@ArrayPart
public class KojiMultiCallValueObj
{
    @DataIndex( 0 )
    private Object data;

    public Object getData()
    {
        return data;
    }

    public void setData( Object data )
    {
        this.data = data;
    }
}
