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
