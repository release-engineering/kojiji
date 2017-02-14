package com.redhat.red.build.koji.model.xmlrpc;

public enum KojiBuildState
{
    ALL(null),
    BUILDING(0),
    COMPLETE(1),
    DELETED(2),
    FAILED(3),
    CANCELED(4);

    private Integer value;

    private KojiBuildState( Integer value )
    {
        this.value = value;
    }

    /**
     * Gets the integer value used by koji for this state.
     */
    public Integer getValue()
    {
        return value;
    }

    /**
     * Gets the state by the given integer value.
     */
    public static KojiBuildState fromInteger( Integer value )
    {
        for ( KojiBuildState state : values() )
        {
            if ( value == null )
            {
                if ( state.getValue() == null )
                {
                    return state;
                }
            }
            else if ( value.equals( state.getValue() ) )
            {
                return state;
            }
        }
        throw new IllegalArgumentException( String.format( "Unknown KojiBuildState value: %i", value ) );
    }

}
