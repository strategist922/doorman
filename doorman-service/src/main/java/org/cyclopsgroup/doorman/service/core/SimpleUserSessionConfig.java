package org.cyclopsgroup.doorman.service.core;

import org.cyclopsgroup.doorman.api.AbstractUserEventListener;
import org.cyclopsgroup.doorman.api.UserEventListener;

/**
 * Real time configuration for user and session service implementations
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public class SimpleUserSessionConfig
    implements UserSessionConfig
{
    private static final String DEFAULT_DOMAIN_NAME = "default";

    private static final String DEFAULT_PASSWORD_STRATEGY = "MD5";

    private static final UserEventListener NO_OP_LISTENER = new AbstractUserEventListener()
    {
    };

    private String domainName = DEFAULT_DOMAIN_NAME;

    private UserEventListener listener = NO_OP_LISTENER;

    private String passwordStrategy = DEFAULT_PASSWORD_STRATEGY;

    /**
     * @inheritDoc
     */
    @Override
    public final String getDomainName()
    {
        return domainName;
    }

    /**
     * @inheritDoc
     */
    @Override
    public final UserEventListener getListener()
    {
        return listener;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getPasswordStrategy()
    {
        return passwordStrategy;
    }

    public final void setDomainName( String domainName )
    {
        this.domainName = domainName;
    }

    public final void setListener( UserEventListener listener )
    {
        this.listener = listener;
    }

    public void setPasswordStrategy( String strategy )
    {
        this.passwordStrategy = strategy;
    }
}
