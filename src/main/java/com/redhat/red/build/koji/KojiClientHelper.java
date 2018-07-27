package com.redhat.red.build.koji;

import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallValueObj;
import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import com.redhat.red.build.koji.model.xmlrpc.messages.MultiCallRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.MultiCallResponse;
import org.commonjava.rwx.core.Registry;

import java.util.ArrayList;
import java.util.List;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.LIST_ARCHIVES;
import static com.redhat.red.build.koji.model.xmlrpc.messages.MultiCallRequest.getBuilder;

/**
 * This class provides advanced helper methods, e.g., using multicall to batch query operations.
 */
public class KojiClientHelper
{
    private final KojiClient client;

    private final Registry registry = Registry.getInstance();

    public KojiClientHelper( KojiClient client )
    {
        this.client = client;
    }

    /**
     * This method sends a list of KojiArchiveQuery queries, and get back the responses for those queries.
     * The responses are in a List and have the same order corresponding to the query objects,
     * i.e., if the requests contains [req-1, req-2, ...], the responses are [resp-1, resp-2, ...].
     *
     * If any query gets no result, its response would be an empty List.
     *
     * @param queries a list of KojiArchiveQuery queries
     * @param session
     * @return a list of response objects. Each response object is a List of KojiArchiveInfo instance.
     * @throws KojiClientException
     */
    public List<List<KojiArchiveInfo>> listArchives( List<KojiArchiveQuery> queries, KojiSessionInfo session )
                    throws KojiClientException
    {
        List<Object> args = new ArrayList<>();
        for ( KojiArchiveQuery query : queries )
        {
            args.add( registry.renderTo( query ) );
        }

        MultiCallRequest.Builder builder = getBuilder();
        args.forEach(( arg ) -> builder.addCallObj( LIST_ARCHIVES, arg ) );

        List<List<KojiArchiveInfo>> ret = new ArrayList<>();

        MultiCallResponse multiCallResponse = client.multiCall( builder.build(), session );
        List<KojiMultiCallValueObj> multiCallValueObjs = multiCallResponse.getValueObjs();

        multiCallValueObjs.forEach( v -> {
            Object data = v.getData();
            if ( data instanceof List )
            {
                List<KojiArchiveInfo> archiveInfoList = new ArrayList<>();
                List l = (List) data;
                l.forEach( element -> {
                    KojiArchiveInfo kojiArchiveInfo = registry.parseAs( element, KojiArchiveInfo.class );
                    archiveInfoList.add( kojiArchiveInfo );
                } );
                ret.add( archiveInfoList );
            }
        } );

        return ret;
    }
}
