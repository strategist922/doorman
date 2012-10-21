package org.cyclopsgroup.doorman.cxf;

import org.cyclopsgroup.doorman.api.beans.SessionCredential;

public class CredentialUtils
{
    private CredentialUtils()
    {
    }

    public static SessionCredential credentialFrom( String string )
    {
        int separatorIndex = string.indexOf( ':' );
        if ( separatorIndex == -1 )
        {
            return new SessionCredential( string, null );
        }
        return new SessionCredential( string.substring( 0, separatorIndex ), string.substring( separatorIndex + 1,
                                                                                               string.length() ) );
    }

    public static String toString( SessionCredential credential )
    {
        StringBuilder s = new StringBuilder( credential.getSessionId() );
        if ( credential.getSessionSecret() != null )
        {
            s.append( ':' ).append( credential.getSessionSecret() );
        }
        return s.toString();
    }
}
