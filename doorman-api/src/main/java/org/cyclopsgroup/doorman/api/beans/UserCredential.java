package org.cyclopsgroup.doorman.api.beans;

public class UserCredential
    extends BaseBean
{
    private String userName;

    private String password;

    public final String getUserName()
    {
        return userName;
    }

    public final void setUserName( String userName )
    {
        this.userName = userName;
    }

    public final String getPassword()
    {
        return password;
    }

    public final void setPassword( String password )
    {
        this.password = password;
    }
}
