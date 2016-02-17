package com.redhat.red.build.koji.model.xmlrpc.messages;

import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.Response;

/**
 * Created by jdcasey on 2/8/16.
 */
@Response
public class UploadResponse
{
    @DataKey( "fileverify" )
    private String checksumProtocol;

    @DataKey( "hexdigest" )
    private String checksum;

    @DataKey( "offset" )
    private long offset;

    @DataKey( "size" )
    private long size;

    public String getChecksumProtocol()
    {
        return checksumProtocol;
    }

    public void setChecksumProtocol( String checksumProtocol )
    {
        this.checksumProtocol = checksumProtocol;
    }

    public String getChecksum()
    {
        return checksum;
    }

    public void setChecksum( String checksum )
    {
        this.checksum = checksum;
    }

    public long getOffset()
    {
        return offset;
    }

    public void setOffset( long offset )
    {
        this.offset = offset;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize( long size )
    {
        this.size = size;
    }
}
