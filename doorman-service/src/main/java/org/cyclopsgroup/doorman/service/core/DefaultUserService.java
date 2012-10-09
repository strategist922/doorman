package org.cyclopsgroup.doorman.service.core;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.cyclopsgroup.doorman.api.ListUserRequest;
import org.cyclopsgroup.doorman.api.User;
import org.cyclopsgroup.doorman.api.UserOperationResult;
import org.cyclopsgroup.doorman.api.UserService;
import org.cyclopsgroup.doorman.api.UserSessionConfig;
import org.cyclopsgroup.doorman.api.UserType;
import org.cyclopsgroup.doorman.api.Users;
import org.cyclopsgroup.doorman.service.dao.DAOFactory;
import org.cyclopsgroup.doorman.service.dao.UserDAO;
import org.cyclopsgroup.doorman.service.security.PasswordStrategy;
import org.cyclopsgroup.doorman.service.storage.StoredUser;
import org.cyclopsgroup.doorman.service.storage.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Server side implementation of user service
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class DefaultUserService
    implements UserService
{
    private static WebApplicationException exceptionOf( String message, Response.Status status )
    {
        String errorMessage = message + " (HTTP response status: " + status.getStatusCode() + ")";
        Response error = Response.status( status ).entity( errorMessage ).build();
        return new WebApplicationException( new RuntimeException( message ), error );
    }

    private final UserSessionConfig config;

    private final UserDAO userDao;

    /**
     * @param daoFactory DAO factory that creates DAOs
     * @param config User session config
     */
    @Autowired
    public DefaultUserService( DAOFactory daoFactory, UserSessionConfig config )
    {
        userDao = daoFactory.createUserDAO();
        this.config = config;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional( readOnly = true )
    public UserOperationResult authenticate( String userName, String secureCredential )
    {
        StoredUser user = userDao.findNonPendingUser( userName );
        if ( user == null )
        {
            return UserOperationResult.NO_SUCH_IDENTITY;
        }
        if ( StringUtils.equals( user.getPasswordStrategy().encode( secureCredential, user.getUserId() ),
                                 user.getPassword() ) )
        {
            return UserOperationResult.SUCCESSFUL;
        }
        return UserOperationResult.AUTHENTICATION_FAILURE;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void changeCredential( String userName, String secureCredential )
    {
        StoredUser user = getRequiredNonPendingUser( userName );
        PasswordStrategy strategy = PasswordStrategy.valueOf( config.getPasswordStrategy() );
        user.setPasswordStrategy( strategy );
        String newPassword = strategy.encode( secureCredential, user.getUserId() );
        user.setPassword( newPassword );
        user.setUserType( UserType.LOCAL );
        userDao.saveUser( user );
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional( readOnly = true )
    public User get( String userName )
    {
        return getRequiredNonPendingUser( userName ).toUser();
    }

    private StoredUser getRequiredNonPendingUser( String userName )
    {
        StoredUser user = userDao.findNonPendingUser( userName );
        if ( user == null )
        {
            throw exceptionOf( "User " + userName + " is not found", Response.Status.NOT_FOUND );
        }
        return user;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional( readOnly = true )
    public Users list( ListUserRequest request )
    {
        List<User> users = new ArrayList<User>( request.getUserNames().size() );
        for ( String userName : request.getUserNames() )
        {
            User user = get( userName );
            if ( user != null )
            {
                users.add( user );
            }
        }
        return new Users( users );
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional( readOnly = true )
    public UserOperationResult ping( String userName )
    {
        StoredUser storedUser = userDao.findNonPendingUser( userName );
        if ( storedUser == null || storedUser.getUserState() == UserState.PENDING )
        {
            return UserOperationResult.NO_SUCH_IDENTITY;
        }
        return UserOperationResult.SUCCESSFUL;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void update( String userName, User user )
    {
        StoredUser storedUser = userDao.findNonPendingUser( userName );
        if ( storedUser == null )
        {
            Response error = Response.status( Status.NOT_FOUND ).entity( "User " + userName + " not found" ).build();
            throw new WebApplicationException( error );
        }
        storedUser.copyFrom( user );
        userDao.saveUser( storedUser );
    }
}
