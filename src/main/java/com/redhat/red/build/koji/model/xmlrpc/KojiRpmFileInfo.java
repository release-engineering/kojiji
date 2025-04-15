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
package com.redhat.red.build.koji.model.xmlrpc;

import java.util.Date;
import java.util.Objects;

import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataKey;
import org.commonjava.rwx.anno.StructPart;

import com.redhat.red.build.koji.model.converter.TimestampIntConverter;

@StructPart
public class KojiRpmFileInfo
{
    @DataKey( "digest" )
    private String digest;

    @DataKey( "digest_algo" )
    private String digestAlgo;

    @DataKey( "flags" )
    private Integer flags;

    @DataKey( "group" )
    private String group;

    @DataKey( "md5" )
    private String md5;

    @DataKey( "mode" )
    private Integer mode;

    @DataKey( "mtime")
    @Converter( TimestampIntConverter.class )
    private Date mtime;

    @DataKey( "name" )
    private String name;

    @DataKey( "size" )
    private Long size;

    @DataKey( "user" )
    private String user;

    public KojiRpmFileInfo()
    {

    }

    public String getDigest()
    {
        return digest;
    }

    public void setDigest( String digest )
    {
        this.digest = digest;
    }

    public String getDigestAlgo()
    {
        return digestAlgo;
    }

    public void setDigestAlgo( String digestAlgo )
    {
        this.digestAlgo = digestAlgo;
    }

    public Integer getFlags()
    {
        return flags;
    }

    public void setFlags( Integer flags )
    {
        this.flags = flags;
    }

    public String getGroup()
    {
        return group;
    }

    public void setGroup( String group )
    {
        this.group = group;
    }

    public String getMd5()
    {
        return md5;
    }

    public void setMd5( String md5 )
    {
        this.md5 = md5;
    }

    public Integer getMode()
    {
        return mode;
    }

    public void setMode( Integer mode )
    {
        this.mode = mode;
    }

    public Date getMtime()
    {
        return mtime;
    }

    public void setMtime( Date mtime )
    {
        this.mtime = mtime;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Long getSize()
    {
        return size;
    }

    public void setSize( Long size )
    {
        this.size = size;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser( String user )
    {
        this.user = user;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( super.equals( obj ) )
        {
            return true;
        }

        if ( !( obj instanceof KojiRpmFileInfo ) )
        {
            return false;
        }

        KojiRpmFileInfo that = (KojiRpmFileInfo) obj;

        return Objects.equals( this.digest, that.digest ) && Objects.equals( this.digestAlgo, that.digestAlgo ) && Objects.equals( this.flags, that.flags ) && Objects.equals( this.group, that.group ) && Objects.equals( this.md5, that.md5 ) && Objects.equals( this.mode, that.mode ) && Objects.equals( this.mtime, that.mtime ) && Objects.equals( this.name, that.name ) && Objects.equals( this.size, that.size ) && Objects.equals( this.user, that.user );
    }

    @Override
    public String toString()
    {
        return "KojiRpmFileInfo{" +
               "digest='" + digest +
               "', digestAlgo='" + digestAlgo +
               "', flags=" + flags +
               ", group=" + group +
               ", md5='" + md5 +
               "', mode=" + mode +
               ", mtime=" + mtime +
               ", name='" + name +
               ", size=" + size +
               ", user='" + user +
               "'}";
    }
}
