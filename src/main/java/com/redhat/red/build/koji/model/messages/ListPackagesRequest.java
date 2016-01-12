package com.redhat.red.build.koji.model.messages;

import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 1/7/16.
 */
@Request( method="listPackages" )
public class ListPackagesRequest
{

    @DataIndex( 0 )
    private int tagId;

    @DataIndex( 1 )
    private int userId;

    @DataIndex( 2 )
    private int pkgId;

    @DataIndex( 3 )
    private String prefix;

    @DataIndex( 4 )
    private boolean inherited;

    @DataIndex( 5 )
    private boolean withDuplicates;

    @DataIndex( 6 )
    private int eventId;

    public int getTagId()
    {
        return tagId;
    }

    public void setTagId( int tagId )
    {
        this.tagId = tagId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId( int userId )
    {
        this.userId = userId;
    }

    public int getPkgId()
    {
        return pkgId;
    }

    public void setPkgId( int pkgId )
    {
        this.pkgId = pkgId;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix( String prefix )
    {
        this.prefix = prefix;
    }

    public boolean isInherited()
    {
        return inherited;
    }

    public void setInherited( boolean inherited )
    {
        this.inherited = inherited;
    }

    public boolean isWithDuplicates()
    {
        return withDuplicates;
    }

    public void setWithDuplicates( boolean withDuplicates )
    {
        this.withDuplicates = withDuplicates;
    }

    public int getEventId()
    {
        return eventId;
    }

    public void setEventId( int eventId )
    {
        this.eventId = eventId;
    }
}
