package org.cyclopsgroup.doorman.client;

import org.cyclopsgroup.doorman.api.beans.UserSession;

public interface UserSessionProvider
{
    UserSession getUserSession();
}
