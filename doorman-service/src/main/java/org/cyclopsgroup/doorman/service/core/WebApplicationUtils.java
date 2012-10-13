package org.cyclopsgroup.doorman.service.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebApplicationUtils
{
    @SuppressWarnings( "serial" )
    private static class InternalServerException
        extends Exception
    {
        private InternalServerException( String message )
        {
            super( message );
        }
    }

    private static final String FALL_BACK_HOST_NAME = "unknown-host.nowhere.com";

    static final String HOSTNAME;

    private static final Log LOG = LogFactory.getLog( WebApplicationUtils.class );
    static
    {
        String hostName;
        try
        {
            hostName = InetAddress.getLocalHost().getHostName();
        }
        catch ( UnknownHostException e )
        {
            LOG.error( "Can't figure out host name", e );
            hostName = FALL_BACK_HOST_NAME;
        }
        HOSTNAME = hostName;
    }

    static WebApplicationException exceptionOf( String message, Response.Status status )
    {
        return exceptionOf( new InternalServerException( message ), status );
    }

    static WebApplicationException exceptionOf( Throwable cause, Response.Status status )
    {
        LOG.error( "Creating WebApplicationException with response status " + status + ", " + status.getStatusCode(),
                   cause );
        return new WebApplicationException( cause, status );
    }

    private WebApplicationUtils()
    {
    }
}
