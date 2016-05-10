package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.util.ProjectVersionRefValueBinder;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiIdOrName;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.anno.DataIndex;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.IndexRefs;
import org.commonjava.rwx.binding.anno.Request;

/**
 * Created by jdcasey on 1/29/16.
 */
@Request( method="getBuild" )
public class ListBuildsRequest
{
    @DataIndex( 0 )
    private final KojiBuildQuery query;

    @IndexRefs( 0 )
    public ListBuildsRequest( KojiBuildQuery query )
    {
        this.query = query;
    }

    public ListBuildsRequest( ProjectVersionRef gav )
    {
        this.query = new KojiBuildQuery( gav );
    }

    public KojiBuildQuery getQuery()
    {
        return query;
    }
}
