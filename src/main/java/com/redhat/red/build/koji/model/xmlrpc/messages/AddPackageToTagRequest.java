package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 8/8/16.
 */
@Request( method = "packageListAdd" )
public class AddPackageToTagRequest
{
    @DataIndex( 0 )
    private KojiIdOrName tag;

    @DataIndex( 1 )
    private String packageName;

    @DataIndex( 2 )
    private String ownerName;

    public AddPackageToTagRequest( String tagName, String packageName, String ownerName)
    {
        this.tag = new KojiIdOrName( tagName );
        this.packageName = packageName;
        this.ownerName = ownerName;
    }

    public AddPackageToTagRequest( int tagId, String packageName, String ownerName)
    {
        this.tag = new KojiIdOrName( tagId );
        this.packageName = packageName;
        this.ownerName = ownerName;
    }

    public KojiIdOrName getTag()
    {
        return tag;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setTag( KojiIdOrName tag )
    {
        this.tag = tag;
    }

    public void setPackageName( String packageName )
    {
        this.packageName = packageName;
    }

    public void setOwnerName( String ownerName )
    {
        this.ownerName = ownerName;
    }
}
