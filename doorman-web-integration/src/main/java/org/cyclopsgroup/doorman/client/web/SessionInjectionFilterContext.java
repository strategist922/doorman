package org.cyclopsgroup.doorman.client.web;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.cyclopsgroup.doorman.api.ReceptionService;
import org.cyclopsgroup.doorman.api.SessionService;
import org.cyclopsgroup.doorman.api.beans.UserSession;

/**
 * Configuration for session injection filter
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 * @see {@link SessionInjectionFilter}
 */
public class SessionInjectionFilterContext
{
    private static final String DEFAULT_COOKIE_NAME = "cyclopsgroup-session-token";

    private static final String DEFAULT_LOGIN_URL = "/login.html";

    private static final String DEFAULT_SESSION_ATTRIBUTE = UserSession.class.getName();

    private static final int DEFAULT_SESSION_CHECKING_INTERVAL = 300;

    private static final Set<String> IGNORABLE_EXTENSIONS =
        Collections.unmodifiableSet( new HashSet<String>( Arrays.asList( "jpg", "gif", "png", "ico", "js" ) ) );

    private String cookieName = DEFAULT_COOKIE_NAME;

    private final String domainName;

    private String loginUrl = DEFAULT_LOGIN_URL;

    private final ReceptionService receptionService;

    private boolean redirectingToUrl;

    private String sessionAttribute = DEFAULT_SESSION_ATTRIBUTE;

    private int sessionCheckingInterval = DEFAULT_SESSION_CHECKING_INTERVAL;

    private final SessionService sessionService;

    /**
     * @param sessionService Session service interface
     * @param signInUrl URL to redirect to to sign in
     */
    public SessionInjectionFilterContext( String domainName, String serviceUrl, String signInUrl )
    {
        this.receptionService = JAXRSClientFactory.create( serviceUrl, ReceptionService.class );
        this.sessionService = JAXRSClientFactory.create( serviceUrl, SessionService.class );
        this.domainName = domainName;
        this.loginUrl = signInUrl;
    }

    /**
     * @return Name of session token cookie
     */
    public final String getCookieName()
    {
        return cookieName;
    }

    public final String getDomainName()
    {
        return domainName;
    }

    /**
     * @return When destination requires user identity, page is redirected to this URL to let user sign in
     */
    public final String getLoginUrl()
    {
        return loginUrl;
    }

    public final ReceptionService getReceptionService()
    {
        return receptionService;
    }

    /**
     * @return Name of session attribute that stores user session
     */
    public final String getSessionAttribute()
    {
        return sessionAttribute;
    }

    /**
     * Interval in seconds between which session is checked against server
     *
     * @return Seconds of interval
     */
    public final int getSessionCheckingInterval()
    {
        return sessionCheckingInterval;
    }

    /**
     * @return The session service associated with context
     */
    public final SessionService getSessionService()
    {
        return sessionService;
    }

    /**
     * @return True if sign in URL is redirected instead of forwarded
     */
    public final boolean isRedirectingToUrl()
    {
        return redirectingToUrl;
    }

    /**
     * @param sessionIdCookie {@link #getCookieName()}
     */
    public final void setCookieName( String name )
    {
        this.cookieName = name;
    }

    public final void setLoginUrl( String loginUrl )
    {
        this.loginUrl = loginUrl;
    }

    /**
     * @param redirectingToUrl True if sign in URL is redirected instead of forwarded
     */
    public final void setRedirectingToUrl( boolean redirectingToUrl )
    {
        this.redirectingToUrl = redirectingToUrl;
    }

    /**
     * @param sessionAttribute {@link #getSessionAttribute()}
     */
    public final void setSessionAttribute( String sessionAttribute )
    {
        this.sessionAttribute = sessionAttribute;
    }

    /**
     * @param sessionCheckingInterval {@link #getSessionCheckingInterval()}
     */
    public final void setSessionCheckingInterval( int sessionCheckingInterval )
    {
        this.sessionCheckingInterval = sessionCheckingInterval;
    }

    /**
     * A chance for user to define the filtering logic of http filter
     *
     * @param request Request to test
     * @return If true, fitler would bypass session injection and go straight for next step
     */
    public boolean shouldIgnorePath( HttpServletRequest request )
    {
        String pathInfo = StringUtils.trimToNull( request.getPathInfo() );
        if ( pathInfo == null )
        {
            return false;
        }
        int lastDot = pathInfo.lastIndexOf( '.' );
        if ( lastDot == -1 || lastDot == pathInfo.length() - 1 )
        {
            return false;
        }
        String extension = pathInfo.substring( lastDot + 1 ).toLowerCase();
        return IGNORABLE_EXTENSIONS.contains( extension );
    }
}
