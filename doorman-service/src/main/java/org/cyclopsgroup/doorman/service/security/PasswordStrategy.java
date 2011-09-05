package org.cyclopsgroup.doorman.service.security;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Enum of supported approaches to store and match password
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public enum PasswordStrategy
{
    /**
     * Plain text password is preserved
     */
    PLAIN
    {
        @Override
        public String encode( String password, String userId )
        {
            return password;
        }
    },
    /**
     * Password is encoded with MD5 32
     */
    MD5
    {
        @Override
        public String encode( String password, String userId )
        {
            return DigestUtils.md5Hex( userId + ":" + password );
        }
    };

    /**
     * Encode password to value to store
     *
     * @param password Plain text password
     * @param userId Id of user to store password for
     * @return Encoded password
     */
    public abstract String encode( String password, String userId );
}
