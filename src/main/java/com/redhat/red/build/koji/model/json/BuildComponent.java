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
package com.redhat.red.build.koji.model.json;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.redhat.red.build.koji.model.util.BuildComponentValueBinder;
import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.TYPE;

/**
 *
 * @author Honza Brázdil &lt;jbrazdil@redhat.com&gt;
 */
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = TYPE)
@JsonSubTypes({
    @JsonSubTypes.Type(value = FileBuildComponent.class, name = "file"),
    @JsonSubTypes.Type(value = RPMBuildComponent.class, name = "rpm")})
@StructPart
@Converter( BuildComponentValueBinder.class )
public abstract class BuildComponent {

    public static abstract class Builder<T extends BuildComponent> implements SectionBuilder<T>, VerifiableBuilder<T> {

        private BuildRoot.Builder parent;

        public Builder() {
        }

        public Builder(BuildRoot.Builder parent) {
            this.parent = parent;
        }

        public BuildRoot.Builder parent() {
            return parent;
        }

        @Override
        public T build() throws VerificationException {
            Set<String> missing = new HashSet<>();
            findMissingProperties("%s", missing);
            if (!missing.isEmpty()) {
                throw new VerificationException(missing);
            }

            return unsafeBuild();
        }

        public abstract String getIdentifier();

    }
}
