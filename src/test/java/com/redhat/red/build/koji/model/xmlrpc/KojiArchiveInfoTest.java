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

import org.commonjava.atlas.maven.ident.ref.SimpleArtifactRef;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jdcasey on 1/23/17.
 */
public class KojiArchiveInfoTest
{

    @Test
    public void testExtensionSearch()
    {
        KojiArchiveInfo archiveInfo = new KojiArchiveInfo();
        archiveInfo.setGroupId( "org.bar" );
        archiveInfo.setArtifactId( "foo" );
        archiveInfo.setVersion( "1.0" );

        archiveInfo.setTypeName( "jar" );
        archiveInfo.setTypeExtensions( "jar war rar sar plugin" );

        archiveInfo.setFilename( "foo-1.0.jar" );

        assertThat( archiveInfo.getExtension(), equalTo( "jar" ) );

        assertThat( archiveInfo.asArtifact(),
                    equalTo( new SimpleArtifactRef( "org.bar", "foo", "1.0", "jar", null ) ) );
    }

    @Test
    public void testEquality()
    {
        KojiArchiveInfo archiveInfo = new KojiArchiveInfo();
        KojiArchiveInfo copy = new KojiArchiveInfo();

        archiveInfo.setArchiveId(1234);
        copy.setArchiveId(1234);

        assertThat(archiveInfo, equalTo(copy));

        archiveInfo.setArchiveId(null);
        assertThat(archiveInfo, not(equalTo(copy)));
    }

    @Test
    public void testHashCode()
    {
        KojiArchiveInfo archiveInfo = new KojiArchiveInfo();
        archiveInfo.setArchiveId(null);
        try
        {
            // call hashCode when archiveId is null, make sure it doesn't throw an exception
            archiveInfo.hashCode();

            archiveInfo.setArchiveId(1234);
            assertThat(archiveInfo.hashCode(), not(equalTo(0)));
        } catch(NullPointerException e) {
            Assert.fail("Null pointer exception shouldn't be thrown when hashCode is called");
        }
    }
}
