package org.cyclopsgroup.doorman.api.beans;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

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

    /**
     * @inheritDoc
     */
    @Override
    public String toString()
    {
        String masked = StringUtils.repeat( "*", password.length() );
        return new ToStringBuilder( this ).append( "userName", userName ).append( "password", masked ).toString();
    }
}
