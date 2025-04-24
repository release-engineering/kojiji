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
package com.redhat.red.build.koji.model.xmlrpc.messages;

import com.redhat.red.build.koji.model.xmlrpc.KojiMultiCallObj;
import org.commonjava.rwx.anno.DataIndex;
import org.commonjava.rwx.anno.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.redhat.red.build.koji.model.xmlrpc.messages.Constants.MULTI_CALL;

/**
 * Created by ruhan on 8/2/17.
 */
@Request( method = MULTI_CALL )
public class MultiCallRequest
{
    @DataIndex( 0 )
    private List<KojiMultiCallObj> multiCallObjs;

    public List<KojiMultiCallObj> getMultiCallObjs()
    {
        return multiCallObjs;
    }

    public void setMultiCallObjs( List<KojiMultiCallObj> multiCallObjs )
    {
        this.multiCallObjs = multiCallObjs;
    }

    // utils
    public static class Builder
    {
        private MultiCallRequest multiCallRequest = new MultiCallRequest();
        private List<KojiMultiCallObj> multiCallObjs = new ArrayList<>();

        public MultiCallRequest build()
        {
            multiCallRequest.setMultiCallObjs( multiCallObjs );
            return multiCallRequest;
        }

        public void addCallObj( String method, List<Object> params )
        {
            KojiMultiCallObj callObj = new KojiMultiCallObj();
            callObj.setMethodName( method );
            callObj.setParams( params );
            multiCallObjs.add( callObj );
        }

        public void addCallObj( String method, Object... params )
        {
            addCallObj( method, Arrays.asList( params ) );
        }
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }
}
