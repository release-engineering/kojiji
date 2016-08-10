package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.StructPart;

/**
 * Created by jdcasey on 8/5/16.
 */
@StructPart
public class KojiPackageInfo
{
    @DataKey( "owner" )
    private String owner;

    @DataKey( "owner_id" )
    private Integer ownerId;

    @DataKey( "package_name" )
    private String packageName;

    @DataKey( "package_id" )
    private Integer packageId;

    @DataKey( "tag_id" )
    private Integer tagId;

    @DataKey( "tag_name" )
    private String tagName;

    @DataKey( "blocked" )
    private boolean blocked;

    public String getOwner()
    {
        return owner;
    }

    public void setOwner( String owner )
    {
        this.owner = owner;
    }

    public Integer getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId( Integer ownerId )
    {
        this.ownerId = ownerId;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName( String packageName )
    {
        this.packageName = packageName;
    }

    public Integer getPackageId()
    {
        return packageId;
    }

    public void setPackageId( Integer packageId )
    {
        this.packageId = packageId;
    }

    public Integer getTagId()
    {
        return tagId;
    }

    public void setTagId( Integer tagId )
    {
        this.tagId = tagId;
    }

    public String getTagName()
    {
        return tagName;
    }

    public void setTagName( String tagName )
    {
        this.tagName = tagName;
    }

    public boolean isBlocked()
    {
        return blocked;
    }

    public void setBlocked( boolean blocked )
    {
        this.blocked = blocked;
    }
}
