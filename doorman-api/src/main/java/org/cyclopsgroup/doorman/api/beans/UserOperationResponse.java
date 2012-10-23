package org.cyclopsgroup.doorman.api.beans;

public class UserOperationResponse
    extends BaseBean
{
    private UserOperationResult result;

    public UserOperationResponse()
    {
    }

    public UserOperationResponse( UserOperationResult result )
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
