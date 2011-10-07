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
     * Get a user with given userId
     *
     * @param userId Given user Id
     * @return Stored user POJO or null if user is not found
     */
    StoredUser get( String userId );

    /**
     * @param user User to update
     */
    void saveUser( StoredUser user );
}
