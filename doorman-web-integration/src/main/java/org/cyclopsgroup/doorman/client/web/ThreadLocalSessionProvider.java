package org.cyclopsgroup.doorman.client.web;

import org.cyclopsgroup.doorman.api.beans.UserSession;
import org.cyclopsgroup.doorman.client.ThreadLocalProvider;
import org.cyclopsgroup.doorman.client.UserSessionProvider;

public class ThreadLocalSessionProvider
    extends ThreadLocalProvider<UserSession>
    implements UserSessionProvider
{
    /**
     * @inheritDoc
     */
    @Override
    public UserSession getUserSession()
    {
        return getObject();
    }
}
