package org.cyclopsgroup.doorman.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Type of user, usually implies the way user is authenticated
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@XmlEnum
public enum UserType
{
    /**
     * User credential is maintained in local system
     */
    @XmlEnumValue( "LOCAL" )
    LOCAL,
    /**
     * User credential is only maintained by facebook
     */
    @XmlEnumValue( "FACEBOOK" )
    FACEBOOK;
}
