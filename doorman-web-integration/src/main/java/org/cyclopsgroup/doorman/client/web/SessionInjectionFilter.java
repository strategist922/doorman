package org.cyclopsgroup.doorman.client.web;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyclopsgroup.doorman.api.UnauthenticatedError;
import org.cyclopsgroup.doorman.api.beans.ClientDevice;
import org.cyclopsgroup.doorman.api.beans.ClientDeviceType;
import org.cyclopsgroup.doorman.api.beans.SessionCredential;
import org.cyclopsgroup.doorman.api.beans.StartSessionRequest;
import org.cyclopsgroup.doorman.api.beans.UserSession;
import org.joda.time.DateTime;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.NestedServletException;

/**
 * A filter that figures out user session based on local cookie, create session if it doesn't exist, and store session
 * in cookie
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class SessionInjectionFilter
    implements Filter
{
    private static final String DEFAULT_CONTEXT_BEAN = "sessionInjectionFilterContext";

    private static final Log LOG = LogFactory.getLog( SessionInjectionFilter.class );

    private static String getParameter( FilterConfig config, String paramName, String defaultValue )
    {
        String value = config.getInitParameter( paramName );
        if ( StringUtils.isBlank( value ) )
        {
            value = defaultValue;
        }
        return value;
    }

    private SessionInjectionFilterContext context;

    private ThreadLocalSessionProvider sessionProvider;

    public void destroy()
    {
    }

    /**
     * @inheritDoc
     */
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        DateTime now = new DateTime();
        HttpServletRequest req = (HttpServletRequest) request;

        if ( context.shouldIgnorePath( req ) )
        {
            chain.doFilter( request, response );
            return;
        }

        UserSession session = (UserSession) req.getSession().getAttribute( context.getSessionAttribute() );
        if ( session == null
            || session.getLastActivity().plusSeconds( context.getSessionCheckingInterval() ).isBefore( now ) )
        {
            LOG.info( "Session is empty or expired, looking for cookie " + context.getCookieName()
                + " from request cookies: " + Arrays.toString( req.getCookies() ) );
            Cookie sessionCookie = null;
            if ( req.getCookies() != null )
            {
                for ( Cookie c : req.getCookies() )
                {
                    if ( c.getName().equals( context.getCookieName() ) )
                    {
                        sessionCookie = c;
                    }
                }
            }
            LOG.info( "Found cookie " + ToStringBuilder.reflectionToString( sessionCookie ) );
            if ( sessionCookie != null )
            {
                SessionCredential cred = SessionCredential.fromToken( sessionCookie.getValue() );
                session = context.getSessionService().getSession( cred.getSessionId() );
                if ( session != null )
                {
                    LOG.info( "Found existing session from session service: " + session );
                }
            }

            if ( session == null || sessionCookie == null )
            {
                ClientDevice client = new ClientDevice();
                client.setClientId( InetAddress.getLocalHost().getHostName() );
                client.setDeviceType( ClientDeviceType.BROWSER );
                client.setNetworkLocation( InetAddress.getLocalHost().getHostAddress() );
                client.setUserAgent( req.getHeader( "User-Agent" ) );

                StartSessionRequest start = new StartSessionRequest();
                start.setTraceNumber( now.toString() );
                start.setClientDevice( client );

                LOG.info( "Start new session" );
                SessionCredential cred = context.getReceptionService().startSession( "", start ).getSessionCredential();
                sessionCookie = new Cookie( context.getCookieName(), cred.toToken() );
                sessionCookie.setMaxAge( 7 * 24 * 3600 );
                ( (HttpServletResponse) response ).addCookie( sessionCookie );

                LOG.info( "Attach new user session to session" );
                session = new UserSession();
                session.setClientDevice( client );
                session.setCreationDate( now );
                session.setLastActivity( now );
                session.setSessionId( cred.getSessionId() );
                session.setTraceNumber( now.toString() );
                session.setLastVerification( now );

                req.getSession().setAttribute( context.getSessionAttribute(), session );
            }
            req.getSession().setAttribute( context.getSessionAttribute(), session );
        }
        sessionProvider.bindObject( session );
        try
        {
            chain.doFilter( request, response );
        }
        catch ( UnauthenticatedError e )
        {
            forwardToSignInUrl( req, (HttpServletResponse) response );
        }
        catch ( NestedServletException e )
        {
            if ( e.getCause() instanceof UnauthenticatedError )
            {
                forwardToSignInUrl( req, (HttpServletResponse) response );
            }
            else
            {
                throw e;
            }
        }
        finally
        {
            sessionProvider.unbindObject();
        }
    }

    private void forwardToSignInUrl( HttpServletRequest req, HttpServletResponse resp )
        throws IOException, ServletException
    {
        StringBuffer url = req.getRequestURL();
        if ( StringUtils.isNotBlank( req.getQueryString() ) )
        {
            url.append( "?" + req.getQueryString() );
        }
        String signInUrl = context.getLoginUrl();
        if ( signInUrl.indexOf( "{contextPath}" ) != -1 )
        {
            signInUrl = StringUtils.replace( signInUrl, "{contextPath}", req.getContextPath() );
        }
        if ( context.isRedirectingToUrl() )
        {
            resp.sendRedirect( signInUrl + "?redirectTo=" + resp.encodeRedirectURL( url.toString() ) );
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher( signInUrl );
        ParameterOverridingRequest request = new ParameterOverridingRequest( req );
        request.setParameter( "redirectTo", url.toString() );
        dispatcher.forward( request, resp );
    }

    /**
     * @inheritDoc
     */
    public void init( FilterConfig filterConfig )
        throws ServletException
    {
        String name = getParameter( filterConfig, "contextBean", DEFAULT_CONTEXT_BEAN );
        LOG.info( "Name of filter context bean in context is " + name );
        WebApplicationContext applicationContext =
            WebApplicationContextUtils.getRequiredWebApplicationContext( filterConfig.getServletContext() );
        context =
            (SessionInjectionFilterContext) applicationContext.getBean( name, SessionInjectionFilterContext.class );
        sessionProvider = applicationContext.getBean( ThreadLocalSessionProvider.class );
    }
}
