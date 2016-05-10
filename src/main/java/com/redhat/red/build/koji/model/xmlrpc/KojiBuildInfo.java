package com.redhat.red.build.koji.model.xmlrpc;

import org.commonjava.rwx.binding.anno.DataKey;
import org.commonjava.rwx.binding.anno.StructPart;

/**
 * Created by jdcasey on 1/29/16.
 */
@StructPart
public class KojiBuildInfo
{
    @DataKey( "id" )
    private int id;

    @DataKey( "package_id" )
    private int packageId;

    @DataKey( "package_name" )
    private String name;

    @DataKey( "version" )
    private String version;

    @DataKey( "release" )
    private String release;

    /*
    TODO: Implement the following fields, once we know what the data types are (and care about them):
      epoch
      nvr
      state
      task_id: ID of the task that kicked off the build
      owner_id: ID of the user who kicked off the build
      owner_name: name of the user who kicked off the build
      volume_id: ID of the storage volume
      volume_name: name of the storage volume
      creation_event_id: id of the create_event
      creation_time: time the build was created (text)
      creation_ts: time the build was created (epoch)
      completion_time: time the build was completed (may be null)
      completion_ts: time the build was completed (epoch, may be null)
     */

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public int getPackageId()
    {
        return packageId;
    }

    public void setPackageId( int packageId )
    {
        this.packageId = packageId;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public String getRelease()
    {
        return release;
    }

    public void setRelease( String release )
    {
        this.release = release;
    }

    public String toString()
    {
        return String.format( "KojiBuildInfo[%s-%s-%s]", getName(), getVersion().replace( '-', '_' ), getRelease() );
    }
}
