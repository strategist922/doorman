package org.cyclopsgroup.doorman.api.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "StartSessionRequest" )
public class StartSessionRequest
{
    private ClientDevice clientDevice;

    private String traceNumber;

    @XmlElement
    public final ClientDevice getClientDevice()
    {
        return clientDevice;
    }

    @XmlElement
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
