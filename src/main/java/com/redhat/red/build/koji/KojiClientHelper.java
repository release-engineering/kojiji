package com.redhat.red.build.koji;

import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiArchiveQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildType;
import com.redhat.red.build.koji.model.xmlrpc.KojiBuildTypeQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiPackageInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiPackageQuery;
import com.redhat.red.build.koji.model.xmlrpc.KojiSessionInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagInfo;
import com.redhat.red.build.koji.model.xmlrpc.KojiTagQuery;
import com.redhat.red.build.koji.model.xmlrpc.messages.MultiCallRequest;
import com.redhat.red.build.koji.model.xmlrpc.messages.MultiCallResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.redhat.red.build.koji.KojiClientUtils.parseMultiCallResponseToLists;
import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.LIST_ARCHIVES;
import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.LIST_BTYPES;
import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.LIST_BUILDS;
import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.LIST_PACKAGES;
import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.LIST_TAGS;

/**
 * This class provides advanced helper methods, e.g., using multicall to batch query operations.
 */
public class KojiClientHelper
{
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    private final KojiClient client;

    public KojiClientHelper( KojiClient client )
    {
        this.client = client;
    }

    /**
     * This method sends a list of KojiArchiveQuery queries and return the responses for those queries.
     * The responses are in a List and have the same order corresponding to the query objects,
     * i.e., if the requests contains [req-1, req-2, ...], the responses are [resp-1, resp-2, ...].
     *
     * If any query gets no result, its response would be an empty List.
     * If any query fails, its response would be null.
     */
    public List<List<KojiArchiveInfo>> listArchives( List<KojiArchiveQuery> queries, KojiSessionInfo session )
                    throws KojiClientException
    {
        return list( LIST_ARCHIVES, queries, KojiArchiveInfo.class, session );
    }

    /**
     * This method sends a list of KojiBuildQuery queries and return the responses for those queries.
     */
    public List<List<KojiBuildInfo>> listBuilds( List<KojiBuildQuery> queries, KojiSessionInfo session )
                    throws KojiClientException
    {
        return list( LIST_BUILDS, queries, KojiBuildInfo.class, session );
    }

    /**
     * This method sends a list of KojiBuildTypeQuery queries and return the responses for those queries.
     */
    public List<List<KojiBuildType>> listBuildTypes( List<KojiBuildTypeQuery> queries, KojiSessionInfo session )
                    throws KojiClientException
    {
        return list( LIST_BTYPES, queries, KojiBuildType.class, session );
    }

    /**
     * This method sends a list of KojiPackageQuery queries and return the responses for those queries.
     */
    public List<List<KojiPackageInfo>> listPackages( List<KojiPackageQuery> queries, KojiSessionInfo session )
                    throws KojiClientException
    {
        return list( LIST_PACKAGES, queries, KojiPackageInfo.class, session );
    }

    /**
     * This method sends a list of KojiTagQuery queries and return the responses for those queries.
     */
    public List<List<KojiTagInfo>> listTags( List<KojiTagQuery> queries, KojiSessionInfo session )
                    throws KojiClientException
    {
        return list( LIST_TAGS, queries, KojiTagInfo.class, session );
    }

    /**
     * This method sends a list of build Ids and return the tags for each build.
     */
    public List<List<KojiTagInfo>> listTagsByIds( List<Integer> buildIds, KojiSessionInfo session )
                    throws KojiClientException
    {
        return list( LIST_TAGS, buildIds, KojiTagInfo.class, session );
    }

    private  <R> List<List<R>> list( String method, List args, Class<R> resultType, KojiSessionInfo session )
                    throws KojiClientException
    {
        MultiCallResponse multiCallResponse = client.multiCall( method, args, session );
        return parseMultiCallResponseToLists( multiCallResponse, resultType );
    }

}
