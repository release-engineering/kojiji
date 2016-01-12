package com.redhat.red.build.koji.model.util;

/**
 * Created by jdcasey on 1/8/16.
 */
public class ValueHolder<T>
{
    private T value;

    public T getValue()
    {
        return value;
    }

    public void setValue( T value )
    {
        this.value = value;
    }
}
