package com.redhat.red.build.koji.model.xmlrpc;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by jdcasey on 8/8/16.
 */
public class KojiBuildArchiveCollection
    implements Iterable<KojiArchiveInfo>
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
        return archives == null ? Collections.emptyList() : archives;
    }

    @Override
    public Iterator<KojiArchiveInfo> iterator()
    {
        return getArchives().iterator();
    }

    @Override
    public void forEach( Consumer<? super KojiArchiveInfo> action )
    {
        getArchives().forEach( action );
    }

    @Override
    public Spliterator<KojiArchiveInfo> spliterator()
    {
        return getArchives().spliterator();
    }

    public Stream<KojiArchiveInfo> stream()
    {
        return getArchives().stream();
    }
}
