package org.cyclopsgroup.doorman.cxf.server;

import org.cyclopsgroup.doorman.api.SessionCredential;
import org.cyclopsgroup.doorman.api.SessionCredentialProvider;

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
