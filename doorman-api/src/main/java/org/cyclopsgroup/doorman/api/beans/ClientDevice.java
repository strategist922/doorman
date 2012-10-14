package org.cyclopsgroup.doorman.api.beans;


public class ClientDevice
{
    private String clientId;

    private ClientDeviceType deviceType;

    private String networkLocation;

    private String userAgent;

    public final String getClientId()
    {
        return clientId;
    }

    public final ClientDeviceType getDeviceType()
    {
        return deviceType;
    }

    public final String getNetworkLocation()
    {
        return networkLocation;
    }

    public final String getUserAgent()
    {
        return userAgent;
    }

    public final void setClientId( String clientId )
    {
        this.clientId = clientId;
    }

    public final void setDeviceType( ClientDeviceType deviceType )
    {
        this.deviceType = deviceType;
    }

    public final void setNetworkLocation( String ipAddress )
    {
        this.networkLocation = ipAddress;
    }

    public final void setUserAgent( String userAgent )
    {
        this.userAgent = userAgent;
    }
}
