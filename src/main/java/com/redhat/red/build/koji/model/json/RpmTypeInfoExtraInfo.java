package com.redhat.red.build.koji.model.json;

import org.commonjava.rwx.anno.StructPart;

@StructPart
public class RpmTypeInfoExtraInfo extends EmptyTypeInfoExtraInfo {
    protected static RpmTypeInfoExtraInfo instance;

    public RpmTypeInfoExtraInfo() {
    }

    public static RpmTypeInfoExtraInfo getInstance() {
        if (instance == null) {
            // if instance is null, initialize
            instance = new RpmTypeInfoExtraInfo();
        }
        return instance;
    }
}
