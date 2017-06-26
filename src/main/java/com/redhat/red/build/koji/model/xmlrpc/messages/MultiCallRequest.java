package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallObj;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import java.util.List;

/**
 * Created by ruhan on 8/2/17.
 */
@Request( method = "multiCall" )
public class MultiCallRequest
{
    @DataIndex( 0 )
    private List<KojiMultiCallObj> multiCallObjs;

    public List<KojiMultiCallObj> getMultiCallObjs()
    {
        return multiCallObjs;
    }

    public void setMultiCallObjs( List<KojiMultiCallObj> multiCallObjs )
    {
        this.multiCallObjs = multiCallObjs;
    }
}
