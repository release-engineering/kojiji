package com.redhat.red.build.koji;

/**
 * Created by jdcasey on 7/24/17.
 */
public class KojijiErrorInfo
{
    private final String filepath;

    private final Exception error;

    private final boolean temporary;

    public KojijiErrorInfo( final String filepath, final Exception error, final boolean temporary )
    {
        this.filepath = filepath;
        this.error = error;
        this.temporary = temporary;
    }

    public String getFilepath()
    {
        return filepath;
    }

    public Exception getError()
    {
        return error;
    }

    public boolean isTemporary()
    {
        return temporary;
    }

    @Override
    public String toString()
    {
        return "KojijiErrorInfo{" + "filepath='" + filepath + '\'' + ", error=" + error + ", temporary=" + temporary
                + '}';
    }
}
