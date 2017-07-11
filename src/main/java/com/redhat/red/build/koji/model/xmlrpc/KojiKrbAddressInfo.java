/**
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

import org.commonjava.rwx.binding.anno.ArrayPart;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.IndexRefs;

@ArrayPart
public class KojiKrbAddressInfo
{
    @DataIndex( 0 )
    private String serverAddress;

    @DataIndex( 1 )
    private int serverPort;

    @DataIndex( 2 )
    private String clientAddress;

    @DataIndex( 3 )
    private int clientPort;

    @IndexRefs( { 0, 1, 2, 3 } )
    public KojiKrbAddressInfo( String serverAddress, int serverPort, String clientAddress, int clientPort )
    {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
    }

    public String getServerAddress()
    {
        return serverAddress;
    }

    public void setServerAddress( String serverAddress )
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

    public String getClientAddress()
    {
        return clientAddress;
    }

    public void setClientAddress( String clientAddress )
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
        return "KojiKrbAddressInfo{serverAddress='" + serverAddress + "', serverPort=" + serverPort + ", clientAddress='"
                + clientAddress + "', clientPort=" + clientPort + "}";
    }
}
