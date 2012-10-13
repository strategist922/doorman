package org.cyclopsgroup.doorman.service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings( "serial" )
public class PingServlet
    extends HttpServlet
{
    private static final Log LOG = LogFactory.getLog( PingServlet.class );

    /**
     * @inheritDoc
     */
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )
        throws IOException, ServletException
    {
        WebApplicationContextUtils.getRequiredWebApplicationContext( getServletContext() );
        response.setContentType( "text/plain" );
        response.getWriter().write( "healthy" );
        response.getWriter().flush();
        response.getWriter().close();
        LOG.info( "ping returned" );
    }
}
