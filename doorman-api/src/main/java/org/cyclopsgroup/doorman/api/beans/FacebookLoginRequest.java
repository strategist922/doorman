package org.cyclopsgroup.doorman.api.beans;

public class FacebookLoginRequest
{
    private String accessCode;

    private String applicationId;

    private String applicationSecret;

    public final String getAccessCode()
    {
        return accessCode;
    }

    public final String getApplicationId()
    {
        return applicationId;
    }

    public final String getApplicationSecret()
    {
        return applicationSecret;
    }

    public final void setAccessCode( String accessCode )
    {
        this.accessCode = accessCode;
    }

    public final void setApplicationId( String applicationId )
    {
        this.applicationId = applicationId;
    }

    public final void setApplicationSecret( String applicationSecret )
    {
        this.applicationSecret = applicationSecret;
    }
}
