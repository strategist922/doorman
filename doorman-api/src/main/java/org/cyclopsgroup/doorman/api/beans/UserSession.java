package org.cyclopsgroup.doorman.api.beans;

import org.cyclopsgroup.doorman.api.UnauthenticatedError;
import org.cyclopsgroup.doorman.api.User;
import org.joda.time.DateTime;

/**
 * A session attached to one physical client
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class UserSession
{
    private UserSessionAttributes attributes;

    private ClientDevice clientDevice;

    private DateTime creationDate;

    private DateTime lastActivity;

    private DateTime lastVerification;

    private boolean mobileDevice;

    private String sessionId;

    private String traceNumber;

    private User user;

    /**
     * Pass all values to another object
     *
     * @param other Another user session object
     */
    public void copyTo( UserSession other )
    {
        other.setAttributes( attributes );
        other.setCreationDate( creationDate );
        other.setLastActivity( lastActivity );
        other.setLastVerification( lastVerification );
        other.setSessionId( sessionId );
        other.setUser( user );
    }

    /**
     * @return Attributes attached to session
     */
    public final UserSessionAttributes getAttributes()
    {
        return attributes;
    }

    public final ClientDevice getClientDevice()
    {
        return clientDevice;
    }

    /**
     * @return Immutable creation date of session
     */
    public final DateTime getCreationDate()
    {
        return creationDate;
    }

    /**
     * @return Timestamp of last write operation of this session
     */
    public final DateTime getLastActivity()
    {
        return lastActivity;
    }

    /**
     * @return Last time the session is authenticated with good credential
     */
    public final DateTime getLastVerification()
    {
        return lastVerification;
    }

    /**
     * @return User POJO, which is never NULL. If user isn't authenticated yet, a {@link UnauthenticatedError} is thrown
     * @throws UnauthenticatedError When user is not authenticated
     */
    public User getRequiredUser()
        throws UnauthenticatedError
    {
        User u = user;
        if ( u == null )
        {
            throw new UnauthenticatedError();
        }
        return u;
    }

    /**
     * @return Id of this session
     */
    public final String getSessionId()
    {
        return sessionId;
    }

    public final String getTraceNumber()
    {
        return traceNumber;
    }

    /**
     * @return Attached user of this session
     */
    public final User getUser()
    {
        return user;
    }

    public final boolean isMobileDevice()
    {
        return mobileDevice;
    }

    /**
     * @param attributes {@link #getAttributes()}
     */
    public final void setAttributes( UserSessionAttributes attributes )
    {
        this.attributes = attributes;
    }

    public final void setClientDevice( ClientDevice clientDevice )
    {
        this.clientDevice = clientDevice;
    }

    /**
     * @param creationDate {@link #getCreationDate()}
     */
    public final void setCreationDate( DateTime creationDate )
    {
        this.creationDate = creationDate;
    }

    /**
     * @param lastActivity {@link #getLastActivity()}
     */
    public final void setLastActivity( DateTime lastActivity )
    {
        this.lastActivity = lastActivity;
    }

    /**
     * @param lastVerification {@link #getLastVerification()}
     */
    public final void setLastVerification( DateTime lastVerification )
    {
        this.lastVerification = lastVerification;
    }

    public final void setMobileDevice( boolean mobileDevice )
    {
        this.mobileDevice = mobileDevice;
    }

    /**
     * @param sessionId {@link #getSessionId()}
     */
    public final void setSessionId( String sessionId )
    {
        this.sessionId = sessionId;
    }

    public final void setTraceNumber( String traceNumber )
    {
        this.traceNumber = traceNumber;
    }

    /**
     * @param user {@link #getUser()}
     */
    public final void setUser( User user )
    {
        this.user = user;
    }
}
