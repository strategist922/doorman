package org.cyclopsgroup.doorman.api.beans;

/**
 * Enumeration of possible user and session operation results
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public enum UserOperationResult
{
    /**
     * Operation failed because identity can't be authenticated correctly
     */
    AUTHENTICATION_FAILURE( false ),
    /**
     * Usually for signing up, the desired identity already exists
     */
    IDENTITY_EXISTED( false ),
    /**
     * Nothing happened
     */
    NO_OP( true ),
    /**
     * Expected identity doesn't exist
     */
    NO_SUCH_IDENTITY( false ),
    /**
     * Operation is successful
     */
    SUCCESSFUL( true );

    private final boolean successful;

    private UserOperationResult( boolean successful )
    {
        this.successful = successful;
    }

    /**
     * @return True if result is positive
     */
    public final boolean isSuccessful()
    {
        return successful;
    }
}
