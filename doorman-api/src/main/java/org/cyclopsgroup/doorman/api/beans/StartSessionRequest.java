package org.cyclopsgroup.doorman.api.beans;

public class StartSessionRequest
{
    private ClientDevice clientDevice;

    private String traceNumber;

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
