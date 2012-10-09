package org.cyclopsgroup.doorman.cxf.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyclopsgroup.doorman.api.SessionCredential;
import org.cyclopsgroup.doorman.cxf.CredentialUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet filter that retrieve credential information from HTTP request header, create session credential out of it and
 * attach credential to a thread local.
 */
public class SpringThreadLocalCredentialFilter
    implements Filter
{
    private static final Log LOG = LogFactory.getLog( SpringThreadLocalCredentialFilter.class );

    private ThreadLocalCredentialProvider provider;

    /**
     * @inheritDoc
     */
    @Override
    public void destroy()
    {
    }

    /**
     * @inheritDoc
     */
    @Override
    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain )
        throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        String header = request.getHeader( CredentialUtils.HTTP_HEADER_NAME );
        if ( header == null )
        {
            LOG.warn( "HTTP header " + CredentialUtils.HTTP_HEADER_NAME + " is not specified" );
            chain.doFilter( req, res );
            return;
        }

        SessionCredential cred = CredentialUtils.credentialFrom( header );
        provider.attach( cred );
        try
        {
            chain.doFilter( req, res );
        }
        finally
        {
            provider.detach();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void init( FilterConfig config )
        throws ServletException
    {
        WebApplicationContext context =
            WebApplicationContextUtils.getWebApplicationContext( config.getServletContext() );
        String providerBean = config.getInitParameter( "providerBean" );
        if ( providerBean == null )
        {
            LOG.info( "Looking for provider in spring context with type " + ThreadLocalCredentialProvider.class );
            provider = context.getBean( ThreadLocalCredentialProvider.class );
        }
        else
        {
            LOG.info( "Looking for provider in spring context with name " + providerBean );
            provider = context.getBean( providerBean, ThreadLocalCredentialProvider.class );
        }
    }
}
