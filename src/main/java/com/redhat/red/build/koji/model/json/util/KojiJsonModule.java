package com.redhat.red.build.koji.model.json.util;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.redhat.red.build.koji.model.json.BuildSource;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.maven.atlas.ident.ref.SimpleProjectVersionRef;

import java.util.Date;

/**
 * Created by jdcasey on 2/10/16.
 */
public class KojiJsonModule
        extends SimpleModule
{
    public KojiJsonModule(){
        addSerializer( ProjectVersionRef.class, new MavenGAVSerializer( ProjectVersionRef.class ) );
        addSerializer( SimpleProjectVersionRef.class, new MavenGAVSerializer( SimpleProjectVersionRef.class ) );
        addDeserializer( ProjectVersionRef.class, new MavenGAVDeserializer( SimpleProjectVersionRef.class ) );
        addSerializer( BuildSource.class, new BuildSourceSerializer() );
        addDeserializer( BuildSource.class, new BuildSourceDeserializer() );
        addSerializer( Date.class, new SecondsSinceEpochSerializer() );
        addDeserializer( Date.class, new SecondsSinceEpochDeserializer() );
    }
}
