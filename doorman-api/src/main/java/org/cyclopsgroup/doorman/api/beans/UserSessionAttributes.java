package org.cyclopsgroup.doorman.api.beans;


/**
 * User attributes attached to session
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class UserSessionAttributes
{
    private String acceptLanguage;

    private String ipAddress;

    private String userAgent;

    /**
     * @return Language accepted by client software
     */
    public final String getAcceptLanguage()
    {
        return acceptLanguage;
    }

    /**
     * @return IP address of client host
     */
    public final String getIpAddress()
    {
        return ipAddress;
    }

    /**
     * @return User agent of client software
     */
    public final String getUserAgent()
    {
        return userAgent;
    }

    /**
     * @param acceptLanguage {@link #getAcceptLanguage()}
     */
    public final void setAcceptLanguage( String acceptLanguage )
    {
        this.acceptLanguage = acceptLanguage;
    }

    /**
     * @param ipAddress {@link #getIpAddress()}
     */
    public final void setIpAddress( String ipAddress )
    {
        this.ipAddress = ipAddress;
    }

    /**
     * @param userAgent {@link #getUserAgent()}
     */
    public final void setUserAgent( String userAgent )
    {
        this.userAgent = userAgent;
    }
}
