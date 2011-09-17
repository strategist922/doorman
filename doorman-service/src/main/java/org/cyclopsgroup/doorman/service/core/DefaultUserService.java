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
import org.cyclopsgroup.doorman.api.Users;
import org.cyclopsgroup.doorman.service.dao.DAOFactory;
import org.cyclopsgroup.doorman.service.dao.UserDAO;
import org.cyclopsgroup.doorman.service.storage.StoredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Server side implementation of user service
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@Service
public class DefaultUserService
    implements UserService
{
    private static WebApplicationException exceptionOf( String message, Response.Status status )
    {
        String errorMessage = message + " (HTTP response status: " + status.getStatusCode() + ")";
        Response error = Response.status( status ).entity( errorMessage ).build();
        return new WebApplicationException( new RuntimeException( message ), error );
    }

    private final UserDAO userDao;

    /**
     * @param daoFactory DAO factory that creates DAOs
     */
    @Autowired
    public DefaultUserService( DAOFactory daoFactory )
    {
        userDao = daoFactory.createUserDAO();
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
    @Transactional( readOnly = true )
    public User get( String userName )
    {
        StoredUser user = userDao.findNonPendingUser( userName );
        if ( user == null )
        {
            throw exceptionOf( "User " + userName + " is not found", Response.Status.NOT_FOUND );
        }
        return user.toUser();
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
        if ( storedUser == null )
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
