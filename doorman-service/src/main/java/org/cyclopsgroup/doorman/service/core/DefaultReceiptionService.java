package org.cyclopsgroup.doorman.service.core;

import org.cyclopsgroup.caff.util.UUIDUtils;
import org.cyclopsgroup.doorman.api.ReceiptionService;
import org.cyclopsgroup.doorman.api.SessionCredential;
import org.cyclopsgroup.doorman.api.beans.StartSessionRequest;
import org.cyclopsgroup.doorman.api.beans.StartSessionResponse;
import org.cyclopsgroup.doorman.service.dao.DAOFactory;
import org.cyclopsgroup.doorman.service.dao.UserSessionDAO;
import org.cyclopsgroup.doorman.service.storage.StoredUserSession;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of receiption service
 */
@Service( "receiptionService" )
public class DefaultReceiptionService
    implements ReceiptionService
{
    private UserSessionDAO sessionDao;

    @Autowired
    public DefaultReceiptionService( DAOFactory daos )
    {
        sessionDao = daos.createUserSessionDAO();
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public StartSessionResponse startSession( String domain, StartSessionRequest request )
    {
        StoredUserSession session = new StoredUserSession();
        DateTime now = new DateTime();
        session.setCreationDate( now );
        session.setIpAddress( request.getClientDevice().getNetworkLocation() );
        session.setUserAgent( request.getClientDevice().getUserAgent() );

        String sessionId = UUIDUtils.randomStringId();
        session.setSessionId( sessionId );
        sessionDao.createNew( session );

        StartSessionResponse response = new StartSessionResponse();
        response.setMessage( "Session is created" );

        SessionCredential cred = new SessionCredential();
        cred.setSessionId( sessionId );
        response.setSessionCredential( cred );
        return response;
    }
}
