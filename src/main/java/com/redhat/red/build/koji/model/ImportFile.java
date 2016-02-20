package com.redhat.red.build.koji.model;

import java.io.InputStream;

/**
 * Created by jdcasey on 2/15/16.
 */
public class ImportFile
{
    private String filePath;

    private InputStream stream;

    public ImportFile( String filePath, InputStream stream )
    {
        this.filePath = filePath;
        this.stream = stream;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public InputStream getStream()
    {
        return stream;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof ImportFile ) )
        {
            return false;
        }

        ImportFile that = (ImportFile) o;

        if ( !getFilePath().equals( that.getFilePath() ) )
        {
            return false;
        }
        return getStream().equals( that.getStream() );

    }

    @Override
    public int hashCode()
    {
        int result = getFilePath().hashCode();
        result = 31 * result + getStream().hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "ImportFile[" + filePath + ']';
    }
}
