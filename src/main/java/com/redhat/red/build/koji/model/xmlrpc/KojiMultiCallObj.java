package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import java.util.List;

/**
 * Created by ruhan on 8/2/17.
 */
@StructPart
public class KojiMultiCallObj
{
    @DataKey( "methodName" )
    private String methodName;

    @DataKey( "params" )
    private List<Object> params;

    public KojiMultiCallObj()
    {
    }

    public KojiMultiCallObj( String methodName )
    {
        this.methodName = methodName;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName( String methodName )
    {
        this.methodName = methodName;
    }

    public List<Object> getParams()
    {
        return params;
    }

    public void setParams( List<Object> params )
    {
        this.params = params;
    }
}
