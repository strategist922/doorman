package org.cyclopsgroup.doorman.api;


/**
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
public interface SimpleUserSessionConfigMBean
    extends UserSessionConfig
{
    /**
     * @param domainName Domain name of service
     */
    void setDomainName( String domainName );

    /**
     * @param listener User session listeners
     */
    void setListener( UserEventListener listener );
}
