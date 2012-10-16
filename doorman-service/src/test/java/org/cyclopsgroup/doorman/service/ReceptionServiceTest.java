package org.cyclopsgroup.doorman.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.cyclopsgroup.doorman.api.ReceptionService;
import org.cyclopsgroup.doorman.api.beans.ClientDevice;
import org.cyclopsgroup.doorman.api.beans.ClientDeviceType;
import org.cyclopsgroup.doorman.api.beans.SessionCredential;
import org.cyclopsgroup.doorman.api.beans.StartSessionRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration( locations = { "classpath:unit-test-context.xml" } )
public class ReceptionServiceTest
    extends AbstractJUnit4SpringContextTests
{
    private ReceptionService reception;

    @Before
    public void setUpReception()
    {
        reception = applicationContext.getBean( ReceptionService.class );
    }

    @Test
    public void testStartSession()
    {
        StartSessionRequest start = new StartSessionRequest();
        start.setTraceNumber( "trace" );

        ClientDevice client = new ClientDevice();
        client.setClientId( "client" );
        client.setDeviceType( ClientDeviceType.ANDROID );
        client.setNetworkLocation( "1.2.3.4" );
        start.setClientDevice( client );

        SessionCredential cred1 = reception.startSession( "test", start ).getSessionCredential();
        assertNotNull( cred1.getSessionId() );
        assertNotNull( cred1.getSessionSecret() );

        SessionCredential cred2 = reception.startSession( "test", start ).getSessionCredential();
        assertEquals( cred1, cred2 );
    }
}
