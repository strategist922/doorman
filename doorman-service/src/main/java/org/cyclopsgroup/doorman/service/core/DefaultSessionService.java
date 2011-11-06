package org.cyclopsgroup.doorman.service.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cyclopsgroup.caff.util.UUIDUtils;
import org.cyclopsgroup.doorman.api.SessionService;
import org.cyclopsgroup.doorman.api.User;
import org.cyclopsgroup.doorman.api.UserOperationResult;
import org.cyclopsgroup.doorman.api.UserSession;
import org.cyclopsgroup.doorman.api.UserSessionAttributes;
import org.cyclopsgroup.doorman.api.UserSessionConfig;
import org.cyclopsgroup.doorman.api.UserSignUpResult;
import org.cyclopsgroup.doorman.api.UserType;
import org.cyclopsgroup.doorman.service.dao.DAOFactory;
import org.cyclopsgroup.doorman.service.dao.UserDAO;
import org.cyclopsgroup.doorman.service.dao.UserSessionDAO;
import org.cyclopsgroup.doorman.service.security.PasswordStrategy;
import org.cyclopsgroup.doorman.service.storage.StoredUser;
import org.cyclopsgroup.doorman.service.storage.StoredUserSession;
import org.cyclopsgroup.doorman.service.storage.UserState;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of session service
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@Service
public class DefaultSessionService
    implements SessionService
{
    private static final Log LOG = LogFactory.getLog( DefaultSessionService.class );

    private final UserSessionConfig config;

    private final UserDAO userDao;

    private final UserSessionDAO userSessionDao;

    /**
     * @param daoFactory Factory instance that creates necessary DAOs
     * @param config Configuration that provides real time settings
     */
    @Autowired
    public DefaultSessionService( DAOFactory daoFactory, UserSessionConfig config )
    {
        this.userSessionDao = daoFactory.createUserSessionDAO();
        this.userDao = daoFactory.createUserDAO();
        this.config = config;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserOperationResult confirmSignUp( String sessionId, String userId, String token )
    {
        StoredUser user = userDao.get( userId );
        if ( user == null )
        {
            return UserOperationResult.NO_SUCH_IDENTITY;
        }
        if ( user.getUserState() != UserState.PENDING || !StringUtils.equals( token, user.getActivationToken() ) )
        {
            return UserOperationResult.AUTHENTICATION_FAILURE;
        }

        user.setActivationToken( null );
        user.setUserState( UserState.ACTIVE );
        userDao.saveUser( user );
        userSessionDao.updateUser( sessionId, user );
        return UserOperationResult.SUCCESSFUL;
    }

    private StoredUser createUserForSignUp( User user, UserType type )
    {
        String userId = UUIDUtils.randomStringId();
        StoredUser storedUser = new StoredUser();
        storedUser.copyFrom( user );

        storedUser.setUserId( userId );
        storedUser.setDomainName( config.getDomainName() );
        storedUser.setUserType( type );

        PasswordStrategy strategy = PasswordStrategy.valueOf( config.getPasswordStrategy() );
        storedUser.setPasswordStrategy( strategy );
        String storedPassword = strategy.encode( user.getPassword(), userId );
        storedUser.setPassword( storedPassword );

        DateTime now = new DateTime();
        storedUser.setCreationDate( now );
        storedUser.setLastModified( now );

        return storedUser;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserSession getSession( String sessionId )
    {
        StoredUserSession session = userSessionDao.pingSession( sessionId );
        if ( session == null )
        {
            return null;
        }
        return session.toUserSession();
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserSession pingSession( String sessionId )
    {
        StoredUserSession session = userSessionDao.pingSession( sessionId );
        if ( session == null )
        {
            throw new IllegalStateException( "Session " + sessionId + " doesn't exist" );
        }
        return session.toUserSession();
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserSignUpResult requestSignUp( String sessionId, User user )
    {
        StoredUser existingUser = userDao.findNonPendingUser( user.getUserName() );
        if ( existingUser != null )
        {
            return new UserSignUpResult( UserOperationResult.IDENTITY_EXISTED, user, null );
        }
        StoredUser storedUser = createUserForSignUp( user, UserType.LOCAL );
        String userId = storedUser.getUserId();
        storedUser.setUserState( UserState.PENDING );
        storedUser.setActivationToken( UUIDUtils.randomStringId() );
        userDao.saveUser( storedUser );

        LOG.info( "Sign up request " + userId + " is saved" );
        user.setUserId( userId );
        UserSignUpResult result =
            new UserSignUpResult( UserOperationResult.SUCCESSFUL, user, storedUser.getActivationToken() );
        config.getListener().signUpRequested( sessionId, result );
        return result;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserOperationResult signIn( String sessionId, String userName, String password )
    {
        StoredUser user = userDao.findNonPendingUser( userName );
        if ( user == null )
        {
            return UserOperationResult.NO_SUCH_IDENTITY;
        }
        if ( !StringUtils.equals( user.getPasswordStrategy().encode( password, user.getUserId() ), user.getPassword() ) )
        {
            return UserOperationResult.AUTHENTICATION_FAILURE;
        }
        userSessionDao.updateUser( sessionId, user );
        return UserOperationResult.SUCCESSFUL;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserOperationResult forceSignIn( String sessionId, String userName )
    {
        StoredUser user = userDao.findNonPendingUser( userName );
        if ( user == null )
        {
            return UserOperationResult.NO_SUCH_IDENTITY;
        }
        userSessionDao.updateUser( sessionId, user );
        return UserOperationResult.SUCCESSFUL;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserOperationResult signOut( String sessionId )
    {
        userSessionDao.updateUser( sessionId, null );
        return UserOperationResult.SUCCESSFUL;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserOperationResult signUp( String sessionId, User user, UserType type )
    {
        StoredUser existing = userDao.findNonPendingUser( user.getUserName() );
        if ( existing != null )
        {
            return UserOperationResult.IDENTITY_EXISTED;
        }
        StoredUser storedUser = createUserForSignUp( user, type == null ? UserType.LOCAL : type );
        storedUser.setUserState( UserState.ACTIVE );

        userDao.saveUser( storedUser );
        userSessionDao.updateUser( sessionId, storedUser );
        return UserOperationResult.SUCCESSFUL;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public UserSession startSession( String sessionId, UserSessionAttributes attributes )
    {
        Validate.notNull( sessionId, "Session ID can't be NULL" );

        StoredUserSession session = new StoredUserSession();
        session.setSessionId( sessionId );
        if ( attributes != null )
        {
            session.setAcceptLanguage( attributes.getAcceptLanguage() );
            session.setIpAddress( attributes.getIpAddress() );
            session.setUserAgent( attributes.getUserAgent() );
        }
        userSessionDao.createNew( session );
        return session.toUserSession();
    }
}
