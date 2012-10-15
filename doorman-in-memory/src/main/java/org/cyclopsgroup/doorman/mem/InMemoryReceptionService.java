package org.cyclopsgroup.doorman.mem;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.Validate;
import org.cyclopsgroup.doorman.api.ReceptionService;
import org.cyclopsgroup.doorman.api.beans.SessionCredential;
import org.cyclopsgroup.doorman.api.beans.StartSessionRequest;
import org.cyclopsgroup.doorman.api.beans.StartSessionResponse;
import org.cyclopsgroup.doorman.api.beans.UserSession;
import org.joda.time.DateTime;

public class InMemoryReceptionService
    implements ReceptionService
{
    private final Storage storage;

    public InMemoryReceptionService( Storage storage )
    {
        this.storage = storage;
    }

    /**
     * @inheritDoc
     */
    @Override
    public StartSessionResponse startSession( String domain, StartSessionRequest request )
    {
        Validate.notNull( request.getClientDevice(), "Client device can not be NULL" );
        Validate.notNull( domain, "Domain can't be NULL" );
        Validate.notNull( request.getTraceNumber(), "Trace number can't be NULL" );
        Validate.notNull( request.getClientDevice().getClientId(), "Client ID can't be NULL" );

        UserSession session =
            storage.findSessionByClientId( domain, request.getClientDevice().getClientId(), request.getTraceNumber() );
        DateTime now = new DateTime();

        boolean newSession = session == null;
        String secret;
        if ( newSession )
        {
            session = new UserSession();
            if ( request.getSessionId() == null )
            {
                session.setSessionId( UUID.randomUUID().toString() );
            }
            else
            {
                session.setSessionId( request.getSessionId() );
            }
            session.setTraceNumber( request.getTraceNumber() );
            session.setCreationDate( now );

            secret = RandomStringUtils.randomAlphabetic( 64 );
        }
        else
        {
            if ( request.getSessionId() != null && !request.getSessionId().equals( session.getSessionId() ) )
            {
                throw new IllegalArgumentException(
                                                    "Session ID conflicting, a different session with same clientId and trace already exists" );
            }
            secret = storage.getSessionSecret( session.getSessionId() );
        }

        session.setClientDevice( request.getClientDevice() );
        session.setLastActivity( now );

        if ( newSession )
        {
            storage.addSession( domain, session, secret );
        }

        StartSessionResponse response = new StartSessionResponse();
        response.setMessage( "Session " + session.getSessionId() + " is created" );
        response.setSessionCredential( new SessionCredential( session.getSessionId(), secret ) );

        return response;
    }
}
