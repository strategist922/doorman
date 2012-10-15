package org.cyclopsgroup.doorman.api;

import org.cyclopsgroup.doorman.api.beans.SessionCredential;

/**
 * An interface to provide session credential attached to current context
 */
public interface SessionCredentialProvider
{
    /**
     * @return Session credential attached to current context
     */
    SessionCredential getSessionCredential();
}
