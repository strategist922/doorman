package org.cyclopsgroup.doorman.client.web;

import javax.servlet.http.HttpServletRequest;

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
    private static final String DEFAULT_COOKIE_NAME = "doormanSessionToken";

    private static final String DEFAULT_SESSION_ATTRIBUTE = UserSession.class.getName();

    private static final int DEFAULT_SESSION_CHECKING_INTERVAL = 300;

    private String cookieName = DEFAULT_COOKIE_NAME;

    private final ReceptionService receptionService;

    private boolean redirectingToUrl;

    private String sessionAttribute = DEFAULT_SESSION_ATTRIBUTE;

    private int sessionCheckingInterval = DEFAULT_SESSION_CHECKING_INTERVAL;

    private final SessionService sessionService;

    private final String signInUrl;

    /**
     * @param sessionService Session service interface
     * @param signInUrl URL to redirect to to sign in
     */
    public SessionInjectionFilterContext( SessionService sessionService, ReceptionService receptionService,
                                          String signInUrl )
    {
        this.sessionService = sessionService;
        this.receptionService = receptionService;
        this.signInUrl = signInUrl;
    }

    /**
     * @return Name of session token cookie
     */
    public final String getCookieName()
    {
        return cookieName;
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
     * @return When destination requires user identity, page is redirected to this URL to let user sign in
     */
    public final String getSignInUrl()
    {
        return signInUrl;
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
        return false;
    }
}
