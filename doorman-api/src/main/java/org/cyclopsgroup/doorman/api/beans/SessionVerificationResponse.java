package org.cyclopsgroup.doorman.api.beans;

import org.joda.time.DateTime;

public class SessionVerificationResponse
    extends BaseBean
{
    private DateTime expiresAt;

    private SessionState sessionState;

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

    public final void setExpiresAt( DateTime expiresAt )
    {
        this.expiresAt = expiresAt;
    }

    public final void setSessionState( SessionState sessionState )
    {
        this.sessionState = sessionState;
    }
}
