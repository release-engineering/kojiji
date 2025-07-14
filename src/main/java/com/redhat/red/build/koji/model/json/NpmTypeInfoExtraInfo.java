package com.redhat.red.build.koji.model.json;

import org.commonjava.rwx.anno.StructPart;

@StructPart
public class NpmTypeInfoExtraInfo extends EmptyTypeInfoExtraInfo {
    protected static NpmTypeInfoExtraInfo instance;

    public NpmTypeInfoExtraInfo() {
    }

    public static NpmTypeInfoExtraInfo getInstance() {
        if (instance == null) {
            // if instance is null, initialize
            instance = new NpmTypeInfoExtraInfo();
        }
        return instance;
    }
}
