package com.redhat.red.build.koji.model.json;

/**
 * Created by jdcasey on 2/10/16.
 */
public class BuildSource
{
    private String url;

    private String revision;

    public BuildSource( String url, String revision )
    {
        this.url = url;
        this.revision = revision;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getRevision()
    {
        return revision;
    }

    public void setRevision( String revision )
    {
        this.revision = revision;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BuildSource ) )
        {
            return false;
        }

        BuildSource that = (BuildSource) o;

        if ( getUrl() != null ? !getUrl().equals( that.getUrl() ) : that.getUrl() != null )
        {
            return false;
        }
        return !( getRevision() != null ? !getRevision().equals( that.getRevision() ) : that.getRevision() != null );

    }

    @Override
    public int hashCode()
    {
        int result = getUrl() != null ? getUrl().hashCode() : 0;
        result = 31 * result + ( getRevision() != null ? getRevision().hashCode() : 0 );
        return result;
    }
}
