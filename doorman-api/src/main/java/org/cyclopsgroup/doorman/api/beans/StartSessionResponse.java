package org.cyclopsgroup.doorman.api.beans;

public class StartSessionResponse
    extends BaseBean
{
    private String message;

    private SessionCredential sessionCredential;

    public final String getMessage()
    {
        return message;
    }

    public final SessionCredential getSessionCredential()
    {
        return sessionCredential;
    }

    public final void setMessage( String message )
    {
        this.message = message;
    }

    public final void setSessionCredential( SessionCredential sessionCredential )
    {
        this.sessionCredential = sessionCredential;
    }
}
