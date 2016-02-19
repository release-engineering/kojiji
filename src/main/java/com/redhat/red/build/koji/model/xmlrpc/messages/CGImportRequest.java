package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiXmlRpcConstants;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 2/19/16.
 */
@Request( method = "CGImport" )
public class CGImportRequest
{
    @DataIndex( 0 )
    private String metadataFilename = KojiXmlRpcConstants.METADATA_JSON_FILE;

    @DataIndex( 1 )
    private String dirname;

    @IndexRefs( 1 )
    public CGImportRequest( String dirname )
    {
        this.dirname = dirname;
    }

    public String getMetadataFilename()
    {
        return metadataFilename;
    }

    public void setMetadataFilename( String metadataFilename )
    {
        this.metadataFilename = metadataFilename;
    }

    public String getDirname()
    {
        return dirname;
    }

    public void setDirname( String dirname )
    {
        this.dirname = dirname;
    }
}
