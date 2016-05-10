package com.redhat.red.build.koji.model.xmlrpc;

import com.redhat.red.build.koji.model.util.ProjectVersionRefValueBinder;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.binding.anno.Converter;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.StructPart;

/**
 * Created by jdcasey on 5/6/16.
 */
@StructPart
public class KojiBuildQuery
    extends KojiQuery
{
    @DataKey( value = "type" )
    private String type = "maven";

    @DataKey( value="typeInfo" )
    @Converter( value = ProjectVersionRefValueBinder.class )
    private ProjectVersionRef gav;

    public KojiBuildQuery(){}

    public KojiBuildQuery( ProjectVersionRef gav )
    {
        this.gav = gav;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public ProjectVersionRef getGav()
    {
        return gav;
    }

    public void setGav( ProjectVersionRef gav )
    {
        this.gav = gav;
    }
}
