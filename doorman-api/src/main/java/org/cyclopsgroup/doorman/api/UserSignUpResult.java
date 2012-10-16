package org.cyclopsgroup.doorman.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyclopsgroup.doorman.api.beans.UserOperationResult;

/**
 * Response of sign up operation
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@XmlRootElement( name = "UserSignUpResponse" )
public class UserSignUpResult
{
    private UserOperationResult result;

    private String token;

    private User user;

    /**
     * Default constructor called by JAXB
     */
    public UserSignUpResult()
    {
    }

    /**
     * @param result Result enumeration
     * @param user Originally requested user
     * @param token Secret token
     */
    public UserSignUpResult( UserOperationResult result, User user, String token )
    {
        this.result = result;
        this.user = user;
        this.token = token;
    }

    /**
     * @return Enumerated result that indicates what happened for sign up operation
     */
    @XmlElement
    public final UserOperationResult getResult()
    {
        return result;
    }

    /**
     * @return A secret token to finish sign up
     */
    public final String getToken()
    {
        return token;
    }

    /**
     * The originally requested user
     *
     * @return User information
     */
    @XmlElement
    public final User getUser()
    {
        return user;
    }

    /**
     * @param result {@link #getResult()}
     */
    public final void setResult( UserOperationResult result )
    {
        this.result = result;
    }

    /**
     * @param token {@link #getToken()}
     */
    public final void setToken( String token )
    {
        this.token = token;
    }

    /**
     * Set user information
     *
     * @param user User information
     */
    public final void setUser( User user )
    {
        this.user = user;
    }
}
