package org.cyclopsgroup.doorman.api.beans;

public class UserLoginResponse
    extends BaseBean
{
    private UserOperationResult result;

    public UserLoginResponse()
    {
    }

    public UserLoginResponse( UserOperationResult result )
    {
        this.result = result;
    }

    public final UserOperationResult getResult()
    {
        return result;
    }

    public final void setResult( UserOperationResult result )
    {
        this.result = result;
    }
}
