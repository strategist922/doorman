package org.cyclopsgroup.doorman.api;


/**
 * Real time configuration used by user and session service
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public interface UserSessionConfig
{
    /**
     * @return Service domain name
     */
    String getDomainName();

    /**
     * @return User session listener
     */
    UserEventListener getListener();
}
