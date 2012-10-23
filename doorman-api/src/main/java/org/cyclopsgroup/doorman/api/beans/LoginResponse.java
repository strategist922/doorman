package org.cyclopsgroup.doorman.api.beans;

public class LoginResponse
    extends BaseBean
{
    private UserOperationResult result;

    private String userId;

    public LoginResponse()
    {
    }

    public LoginResponse( UserOperationResult result, String userId )
    {
        this.result = result;
        this.userId = userId;
    }

    public final UserOperationResult getResult()
    {
        return result;
    }

    public final String getUserId()
    {
        return userId;
    }

    public final void setResult( UserOperationResult result )
    {
        this.result = result;
    }

    public final void setUserId( String userId )
    {
        this.userId = userId;
    }
}
