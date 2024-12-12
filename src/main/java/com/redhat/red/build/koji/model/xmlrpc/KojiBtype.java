package com.redhat.red.build.koji.model.xmlrpc;

public enum KojiBtype
{
    rpm,
    maven,
    win,
    image,
    npm;

    KojiBtype()
    {

    }

    public static KojiBtype fromString( String name )
    {
        for ( KojiBtype btype : values() )
        {
            if ( btype.name().equals( name ) )
            {
                return btype;
            }
        }

        throw new IllegalArgumentException( "Unknown build type: " + name );
    }

    @Override
    public String toString()
    {
        return name();
    }
}
