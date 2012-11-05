package org.cyclopsgroup.doorman.api.beans;

public class SessionCredential
    extends BaseBean
{
    public static SessionCredential fromToken( String token )
    {
        int separatorIndex = token.indexOf( ':' );
        if ( separatorIndex == -1 )
        {
            return new SessionCredential( token, null );
        }
        return new SessionCredential( token.substring( 0, separatorIndex ), token.substring( separatorIndex + 1,
                                                                                             token.length() ) );
    }

    private String sessionId;

    private String sessionSecret;

    public SessionCredential()
    {
    }

    public SessionCredential( String id, String secret )
    {
        this.sessionId = id;
        this.sessionSecret = secret;
    }

    /**
     * @return Session ID is a unique identifier of session. The value of session id is not secret
     */
    public final String getSessionId()
    {
        return sessionId;
    }

    /**
     * @return Secret is a long strong secret string attached to a session. It helps verify caller is authenticated.
     */
    public final String getSessionSecret()
    {
        return sessionSecret;
    }

    public final void setSessionId( String sessionId )
    {
        this.sessionId = sessionId;
    }

    public final void setSessionSecret( String sessionSecret )
    {
        this.sessionSecret = sessionSecret;
    }

    public String toToken()
    {
        StringBuilder s = new StringBuilder( sessionId );
        if ( sessionSecret != null )
        {
            s.append( ':' ).append( sessionSecret );
        }
        return s.toString();
    }
}
