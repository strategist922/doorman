package org.cyclopsgroup.doorman.client;

public class ThreadLocalProvider<T>
{
    private final ThreadLocal<T> local = new ThreadLocal<T>();

    public void bindObject( T object )
    {
        local.set( object );
    }

    public void unbindObject()
    {
        local.set( null );
    }

    public T getObject()
    {
        return local.get();
    }
}
