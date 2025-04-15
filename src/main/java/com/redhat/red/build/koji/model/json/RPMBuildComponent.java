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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.commonjava.rwx.anno.DataKey;

import java.util.Objects;
import java.util.Set;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.ARCH;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.EPOCH;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.NAME;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.RELEASE;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.SIGMD5;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.SIGNATURE;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.VERSION;
import static com.redhat.red.build.koji.model.json.util.Verifications.checkNull;

@JsonTypeName("rpm")
public class RPMBuildComponent extends BuildComponent
{

    @JsonProperty(NAME)
    @DataKey( NAME )
    private String name;

    @JsonProperty(VERSION)
    @DataKey( VERSION )
    private String version;

    @JsonProperty(RELEASE)
    @DataKey( RELEASE )
    private String release;

    @JsonProperty(EPOCH)
    @DataKey( EPOCH )
    private Integer epoch;

    @JsonProperty(ARCH)
    @DataKey( ARCH )
    private String arch;

    @JsonProperty(SIGMD5)
    @DataKey( SIGMD5 )
    private String sigmd5;

    @JsonProperty(SIGNATURE)
    @DataKey( SIGNATURE )
    private String signature;

    public RPMBuildComponent() {
        super("rpm");
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getRelease() {
        return release;
    }

    public Integer getEpoch() {
        return epoch;
    }

    public String getArch() {
        return arch;
    }

    public String getSigmd5() {
        return sigmd5;
    }

    public String getSignature() {
        return signature;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public void setRelease( String release )
    {
        this.release = release;
    }

    public void setEpoch( Integer epoch )
    {
        this.epoch = epoch;
    }

    public void setArch( String arch )
    {
        this.arch = arch;
    }

    public void setSigmd5( String sigmd5 )
    {
        this.sigmd5 = sigmd5;
    }

    public void setSignature( String signature )
    {
        this.signature = signature;
    }

    public static class Builder extends BuildComponent.Builder<RPMBuildComponent> {

        private final RPMBuildComponent target = new RPMBuildComponent();

        public Builder(String name) {
            target.name = name;
        }

        public Builder(String name, BuildRoot.Builder parent) {
            super(parent);
            target.name = name;
        }

        public Builder withVersion(String version) {
            target.version = version;
            return this;
        }

        public Builder withRelease(String release) {
            target.release = release;
            return this;
        }

        public Builder withEpoch(Integer epoch) {
            target.epoch = epoch;
            return this;
        }

        public Builder withArch(StandardArchitecture arch) {
            target.arch = arch.name();
            return this;
        }

        public Builder withArch(String arch) {
            target.arch = arch;
            return this;
        }

        public Builder withSigmd5(String sigmd5) {
            target.sigmd5 = sigmd5;
            return this;
        }

        public Builder withSignature(String signature) {
            target.signature = signature;
            return this;
        }

        @Override
        public void findMissingProperties(String prefix, Set<String> missingProperties) {
            checkNull(target.name, missingProperties, prefix, NAME);
            checkNull(target.version, missingProperties, prefix, VERSION);
            checkNull(target.release, missingProperties, prefix, RELEASE);
            checkNull(target.arch, missingProperties, prefix, ARCH);
            checkNull(target.sigmd5, missingProperties, prefix, SIGMD5);
        }

        @Override
        public RPMBuildComponent unsafeBuild() {
            return target;
        }

        @Override
        public String getIdentifier() {
            return target.name;
        }

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.version);
        hash = 97 * hash + Objects.hashCode(this.release);
        hash = 97 * hash + Objects.hashCode(this.epoch);
        hash = 97 * hash + Objects.hashCode(this.arch);
        hash = 97 * hash + Objects.hashCode(this.sigmd5);
        hash = 97 * hash + Objects.hashCode(this.signature);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RPMBuildComponent other = (RPMBuildComponent) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        if (!Objects.equals(this.release, other.release)) {
            return false;
        }
        if (!Objects.equals(this.epoch, other.epoch)) {
            return false;
        }
        if (!Objects.equals(this.arch, other.arch)) {
            return false;
        }
        if (!Objects.equals(this.sigmd5, other.sigmd5)) {
            return false;
        }
        if (!Objects.equals(this.signature, other.signature)) {
            return false;
        }
        return true;
    }

}
