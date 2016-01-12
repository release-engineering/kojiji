package com.redhat.red.build.koji.model.messages;

import com.redhat.red.build.koji.model.KojiTagInfo;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 1/11/16.
 */
@Request( method="createTag" )
public class CreateTagRequest
{
    @DataIndex( 0 )
    private String tagName;

    @DataIndex( 1 )
    private KojiTagInfo tagInfo;

    @IndexRefs( 1 )
    public CreateTagRequest( KojiTagInfo info )
    {
        this.tagInfo = info;
        this.tagName = info.getName();
    }

    public String getTagName()
    {
        return tagName;
    }

    public void setTagName( String tagName )
    {
        this.tagName = tagName;
    }

    public KojiTagInfo getTagInfo()
    {
        return tagInfo;
    }
}
