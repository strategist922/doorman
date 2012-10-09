package org.cyclopsgroup.doorman.api.beans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum ClientDeviceType
{
    /**
     * Device with Android OS
     */
    @XmlEnumValue( "ANDROID" )
    ANDROID,
    /**
     * Web browser
     */
    @XmlEnumValue( "BROWSER" )
    BROWSER,
    /**
     * IPhone, IPad with IOS
     */
    @XmlEnumValue( "IOS" )
    IOS;
}
