package org.cyclopsgroup.doorman.mem;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cyclopsgroup.doorman.api.beans.UserSession;

public class Storage
{
    private static class SessionEntry
    {
        private final UserSession session;

        private final String sessionSecret;

        private SessionEntry( UserSession session, String sessionSecret )
        {
            this.session = session;
            this.sessionSecret = sessionSecret;
        }
    }

    private final ConcurrentMap<String, String> sessionIdMap = new ConcurrentHashMap<String, String>();

    private final ConcurrentMap<String, SessionEntry> sessions = new ConcurrentHashMap<String, SessionEntry>();

    void addSession( String domain, UserSession session, String sessionSecret )
    {
        String key = domain + "/" + session.getClientDevice().getClientId() + "/" + session.getTraceNumber();
        sessions.put( session.getSessionId(), new SessionEntry( session, sessionSecret ) );
        sessionIdMap.put( key, session.getSessionId() );
    }

    UserSession findSessionByClientId( String domain, String clientId, String trace )
    {
        String key = domain + "/" + clientId + "/" + trace;
        String sessionId = sessionIdMap.get( key );
        if ( sessionId == null )
        {
            return null;
        }
        return findSessionById( sessionId );
    }

    UserSession findSessionById( String sessionId )
    {
        SessionEntry entry = sessions.get( sessionId );
        return entry == null ? null : entry.session;
    }

    String getSessionSecret( String sessionId )
    {
        SessionEntry entry = sessions.get( sessionId );
        return entry == null ? null : entry.sessionSecret;
    }

    void updateSession( UserSession session )
    {
        String secret = getSessionSecret( session.getSessionId() );
        if ( secret == null )
        {
            throw new IllegalArgumentException( "Session " + session.getSessionId() + " does not exist" );
        }
        sessions.put( session.getSessionId(), new SessionEntry( session, secret ) );
    }
}
