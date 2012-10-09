package org.cyclopsgroup.doorman.api.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "ClientDevice" )
public class ClientDevice
{
    private String userAgent;

    private ClientDeviceType deviceType;

    private String networkLocation;

    private String clientId;

    @XmlElement
    public final String getClientId()
    {
        return clientId;
    }

    public final void setClientId( String clientId )
    {
        this.clientId = clientId;
    }

    @XmlElement
    public final String getUserAgent()
    {
        return userAgent;
    }

    @XmlElement
    public final ClientDeviceType getDeviceType()
    {
        return deviceType;
    }

    @XmlElement
    public final String getNetworkLocation()
    {
        return networkLocation;
    }

    public final void setUserAgent( String userAgent )
    {
        this.userAgent = userAgent;
    }

    public final void setDeviceType( ClientDeviceType deviceType )
    {
        this.deviceType = deviceType;
    }

    public final void setNetworkLocation( String ipAddress )
    {
        this.networkLocation = ipAddress;
    }
}
