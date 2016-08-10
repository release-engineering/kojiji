package com.redhat.red.build.koji.model.xmlrpc;

import java.util.List;
import java.util.Set;

/**
 * Created by jdcasey on 8/8/16.
 */
public class KojiBuildArchiveCollection
{
    private KojiBuildInfo buildInfo;

    private List<KojiArchiveInfo> archives;

    public KojiBuildArchiveCollection( KojiBuildInfo buildInfo, List<KojiArchiveInfo> archives )
    {
        this.buildInfo = buildInfo;
        this.archives = archives;
    }

    public KojiBuildInfo getBuildInfo()
    {
        return buildInfo;
    }

    public List<KojiArchiveInfo> getArchives()
    {
        return archives;
    }
}
