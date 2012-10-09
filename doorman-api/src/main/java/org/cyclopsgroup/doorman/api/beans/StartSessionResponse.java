package org.cyclopsgroup.doorman.api.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyclopsgroup.doorman.api.SessionCredential;

@XmlRootElement( name = "StartSessionResponse" )
public class StartSessionResponse
{
    private String message;

    private SessionCredential sessionCredential;

    @XmlElement
    public final String getMessage()
    {
        return message;
    }

    @XmlElement
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
