package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 8/8/16.
 */
@Request( method = "packageListRemove" )
public class RemovePackageFromTagRequest
{
    @DataIndex( 0 )
    private KojiIdOrName tag;

    @DataIndex( 1 )
    private String packageName;

    @IndexRefs( {0,1} )
    public RemovePackageFromTagRequest( String tagName, String packageName)
    {
        this.tag = new KojiIdOrName( tagName );
        this.packageName = packageName;
    }

    @IndexRefs( {0,1} )
    public RemovePackageFromTagRequest( int tagId, String packageName)
    {
        this.tag = new KojiIdOrName( tagId );
        this.packageName = packageName;
    }

    public KojiIdOrName getTag()
    {
        return tag;
    }

    public String getPackageName()
    {
        return packageName;
    }
}
