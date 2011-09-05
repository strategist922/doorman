package org.cyclopsgroup.doorman.service.dao;

import org.cyclopsgroup.doorman.service.storage.StoredUser;

/**
 * DAO of {@link StoredUser} and {@link StoredUserSignUpRequest}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public interface UserDAO
{
    /**
     * Find a non-pending user with given user name or unique ID
     *
     * @param nameOrId Given user name or ID
     * @return User model or NULL if no such user is found
     */
    StoredUser findNonPendingUser( String nameOrId );

    /**
     * Find pending user with given activation token
     *
     * @param token Activation token
     * @return User POJO or NULL if user is not found
     */
    StoredUser findPendingUserWithToken( String token );

    /**
     * @param user User to update
     */
    void saveUser( StoredUser user );
}
