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

import java.util.List;

public class KojiRpmBuildList
{
    private List<KojiRpmInfo> rpms;

    private List<KojiBuildInfo> builds;

    public KojiRpmBuildList( List<KojiRpmInfo> rpms, List<KojiBuildInfo> builds )
    {
        this.rpms = rpms;
        this.builds = builds;
    }

    public List<KojiRpmInfo> getRpms()
    {
        return rpms;
    }

    public void setRpms( List<KojiRpmInfo> rpms )
    {
        this.rpms = rpms;
    }

    public List<KojiBuildInfo> getBuilds()
    {
        return builds;
    }

    public void setBuilds( List<KojiBuildInfo> builds )
    {
        this.builds = builds;
    }

    @Override
    public String toString()
    {
        return "KojiRpmBuild{" +
               "rpms=" + rpms +
               ", builds=" +
               builds +
               "}";
    }
}
