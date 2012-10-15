package org.cyclopsgroup.doorman.service.core;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyclopsgroup.caff.util.UUIDUtils;
import org.cyclopsgroup.doorman.api.ReceptionService;
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
@Service( "receptionService" )
public class DefaultReceptionService
    implements ReceptionService
{
    private static final Log LOG = LogFactory.getLog( DefaultReceptionService.class );

    private UserSessionDAO sessionDao;

    @Autowired
    public DefaultReceptionService( DAOFactory daos )
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
        if ( request.getClientDevice() == null )
        {
            throw WebApplicationUtils.exceptionOf( "Client device is not described", Response.Status.BAD_REQUEST );
        }
        if ( StringUtils.isBlank( request.getClientDevice().getClientId() )
            || StringUtils.isBlank( request.getTraceNumber() ) )
        {
            throw WebApplicationUtils.exceptionOf( "Client ID or Trace Number is missing", Response.Status.BAD_REQUEST );
        }
        StoredUserSession session =
            sessionDao.findByTrace( request.getClientDevice().getClientId(), request.getTraceNumber() );
        DateTime now = new DateTime();
        if ( session == null )
        {
            session = new StoredUserSession();
            session.setSessionId( UUIDUtils.randomStringId() );
            LOG.info( "Creating new session " + session.getSessionId() );
            session.setClientId( request.getClientDevice().getClientId() );
            session.setTraceNumber( request.getTraceNumber() );
            session.setCreationDate( now );
        }
        else
        {
            LOG.info( "Update existing session " + session.getSessionId() );
        }
        session.setIpAddress( request.getClientDevice().getNetworkLocation() );
        session.setClientDeviceType( request.getClientDevice().getDeviceType() );
        session.setUserAgent( request.getClientDevice().getUserAgent() );
        session.setLastModified( now );

        sessionDao.saveOrUpdate( session );

        StartSessionResponse response = new StartSessionResponse();
        response.setMessage( "Session is created" );

        SessionCredential cred = new SessionCredential();
        cred.setSessionId( session.getSessionId() );
        response.setSessionCredential( cred );
        return response;
    }
}
