package com.redhat.red.build.koji.model;

import java.io.InputStream;

/**
 * Created by jdcasey on 2/15/16.
 */
public class ImportFile
{
    private String filename;

    private InputStream stream;

    public ImportFile( String filename, InputStream stream )
    {
        this.filename = filename;
        this.stream = stream;
    }

}
