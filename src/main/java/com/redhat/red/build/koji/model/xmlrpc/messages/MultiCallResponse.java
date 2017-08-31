package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallValueObj;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Response;

import java.util.List;

/**
 * Created by ruhan on 8/4/17.
 */
@Response
public class MultiCallResponse
{
    @DataIndex( 0 )
    private List<KojiMultiCallValueObj> valueObjs;

    public List<KojiMultiCallValueObj> getValueObjs()
    {
        return valueObjs;
    }

    public void setValueObjs( List<KojiMultiCallValueObj> valueObjs )
    {
        this.valueObjs = valueObjs;
    }
}
