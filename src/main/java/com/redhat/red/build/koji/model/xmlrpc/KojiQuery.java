package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.StructPart;

/**
 * Created by jdcasey on 5/6/16.
 */
@StructPart
public class KojiQuery
{
    @DataKey( "__starstar" )
    private boolean enabled = true;

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }
}
