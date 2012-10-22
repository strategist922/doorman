package org.cyclopsgroup.doorman.api.beans;

import org.joda.time.DateTime;

public class SessionVerificationResponse
    extends BaseBean
{
    private DateTime expiresAt;

    private SessionState sessionState;

    private String userId;

    private String userName;

    public SessionVerificationResponse()
    {

    }

    public SessionVerificationResponse( SessionState state, DateTime expireAt )
    {
        this.sessionState = state;
        this.expiresAt = expireAt;
    }

    public final DateTime getExpiresAt()
    {
        return expiresAt;
    }

    public final SessionState getSessionState()
    {
        return sessionState;
    }

    public final String getUserId()
    {
        return userId;
    }

    public final String getUserName()
    {
        return userName;
    }

    public final void setExpiresAt( DateTime expiresAt )
    {
        this.expiresAt = expiresAt;
    }

    public final void setSessionState( SessionState sessionState )
    {
        this.sessionState = sessionState;
    }

    public final void setUserId( String userId )
    {
        this.userId = userId;
    }

    public final void setUserName( String userName )
    {
        this.userName = userName;
    }
}
