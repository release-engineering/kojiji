package com.redhat.red.build.koji.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.commonjava.maven.atlas.ident.ref.ProjectVersionRef;
import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.SkipNull;
import org.commonjava.rwx.binding.anno.StructPart;

import static com.redhat.red.build.koji.model.json.KojiJsonConstants.EXTERNAL_BUILD_ID;
import static com.redhat.red.build.koji.model.json.KojiJsonConstants.TYPE_INFO;

/**
 * Created by jdcasey on 9/15/16.
 */
@StructPart
@SkipNull
public class BuildExtraInfo
{
    @JsonProperty( TYPE_INFO )
    @DataKey( TYPE_INFO )
    private TypeInfo typeInfo;

    @JsonProperty( EXTERNAL_BUILD_ID )
    @DataKey( EXTERNAL_BUILD_ID )
    private String externalBuildId;

    public BuildExtraInfo(){}

    public BuildExtraInfo( TypeInfo typeInfo )
    {
        this.typeInfo = typeInfo;
    }

    public BuildExtraInfo( ProjectVersionRef gav )
    {
        this(new TypeInfo( new MavenExtraInfo( gav ) ));
    }


    public String getExternalBuildId()
    {
        return externalBuildId;
    }

    public void setExternalBuildId( String externalBuildId )
    {
        this.externalBuildId = externalBuildId;
    }

    public TypeInfo getTypeInfo()
    {
        return typeInfo;
    }

    public void setTypeInfo( TypeInfo typeInfo )
    {
        this.typeInfo = typeInfo;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildExtraInfo ) )
        {
            return false;
        }

        BuildExtraInfo that = (BuildExtraInfo) o;

        return getTypeInfo() != null ?
                getTypeInfo().equals( that.getTypeInfo() ) :
                that.getTypeInfo() == null;

    }

    @Override
    public int hashCode()
    {
        return getTypeInfo() != null ? getTypeInfo().hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return "BuildMavenInfo{" +
                "typeInfo=" + typeInfo +
                '}';
    }
}
