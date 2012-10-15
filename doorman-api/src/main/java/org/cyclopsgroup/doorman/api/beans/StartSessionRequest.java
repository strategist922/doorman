package org.cyclopsgroup.doorman.api.beans;

public class StartSessionRequest
    extends BaseBean
{
    private ClientDevice clientDevice;

    private String traceNumber;

    private String sessionId;

    public final String getSessionId()
    {
        return sessionId;
    }

    public final void setSessionId( String sessionId )
    {
        this.sessionId = sessionId;
    }

    public final ClientDevice getClientDevice()
    {
        return clientDevice;
    }

    public final String getTraceNumber()
    {
        return traceNumber;
    }

    public final void setClientDevice( ClientDevice clientDevice )
    {
        this.clientDevice = clientDevice;
    }

    public final void setTraceNumber( String traceNumber )
    {
        this.traceNumber = traceNumber;
    }
}
