package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveType;
import org.commonjava.rwx.binding.anno.Contains;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Response;

import java.util.List;

/**
 * Created by jdcasey on 9/16/16.
 */
@Response
public class GetArchiveTypesResponse
{
    @DataIndex( 0 )
    @Contains( KojiArchiveType.class )
    private List<KojiArchiveType> archiveTypes;

    @IndexRefs( 0 )
    public GetArchiveTypesResponse( List<KojiArchiveType> archiveTypes )
    {
        this.archiveTypes = archiveTypes;
    }

    public List<KojiArchiveType> getArchiveTypes()
    {
        return archiveTypes;
    }

    public void setArchiveTypes( List<KojiArchiveType> archiveTypes )
    {
        this.archiveTypes = archiveTypes;
    }
}
