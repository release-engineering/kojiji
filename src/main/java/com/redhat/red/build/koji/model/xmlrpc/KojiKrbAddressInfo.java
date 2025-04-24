/*
 * Copyright (C) 2015 Red Hat, Inc.
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

import java.net.InetAddress;

import org.commonjava.rwx.anno.ArrayPart;
import org.commonjava.rwx.anno.Converter;
import org.commonjava.rwx.anno.DataIndex;

import com.redhat.red.build.koji.model.converter.InetAddressConverter;

@ArrayPart
public class KojiKrbAddressInfo
{
    @DataIndex( 0 )
    @Converter( InetAddressConverter.class )
    private InetAddress serverAddress;

    @DataIndex( 1 )
    private int serverPort;

    @DataIndex( 2 )
    @Converter( InetAddressConverter.class )
    private InetAddress clientAddress;

    @DataIndex( 3 )
    private int clientPort;

    public KojiKrbAddressInfo( InetAddress serverAddress, int serverPort, InetAddress clientAddress, int clientPort )
    {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
    }

    public KojiKrbAddressInfo()
    {
    }

    public InetAddress getServerAddress()
    {
        return serverAddress;
    }

    public void setServerAddress( InetAddress serverAddress )
    {
        this.serverAddress = serverAddress;
    }

    public int getServerPort()
    {
        return serverPort;
    }

    public void setServerPort( int serverPort )
    {
        this.serverPort = serverPort;
    }

    public InetAddress getClientAddress()
    {
        return clientAddress;
    }

    public void setClientAddress( InetAddress clientAddress )
    {
        this.clientAddress = clientAddress;
    }

    public int getClientPort()
    {
        return clientPort;
    }

    public void setClientPort( int clientPort )
    {
        this.clientPort = clientPort;
    }

    @Override
    public String toString()
    {
        return "KojiKrbAddressInfo{serverAddress='" + serverAddress.getHostAddress() + "', serverPort=" + serverPort + ", clientAddress='"
                + clientAddress.getHostAddress() + "', clientPort=" + clientPort + "}";
    }
}
