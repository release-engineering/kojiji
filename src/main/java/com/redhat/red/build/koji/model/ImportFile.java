/*
 * Copyright (C) 2015 Red Hat, Inc. (jcasey@redhat.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.red.build.koji.model;

import java.io.InputStream;

/**
 * Created by jdcasey on 2/15/16.
 */
public class ImportFile
{
    private String filePath;

    private InputStream stream;

    private long size;

    public ImportFile( String filePath, InputStream stream, long size )
    {
        this.filePath = filePath;
        this.stream = stream;
        this.size = size;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public InputStream getStream()
    {
        return stream;
    }

    public long getSize()
    {
        return size;
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
