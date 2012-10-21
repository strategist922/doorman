package org.cyclopsgroup.doorman.cxf.server;

import org.cyclopsgroup.doorman.api.SessionCredentialProvider;
import org.cyclopsgroup.doorman.api.beans.SessionCredential;

public class ThreadLocalCredentialProvider
    implements SessionCredentialProvider
{
    private final ThreadLocal<SessionCredential> local = new ThreadLocal<SessionCredential>();

    void attach( SessionCredential cred )
    {
        local.set( cred );
    }

    void detach()
    {
        local.remove();
    }

    /**
     * @inheritDoc
     */
    @Override
    public SessionCredential getSessionCredential()
    {
        return local.get();
    }
}
