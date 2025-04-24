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
