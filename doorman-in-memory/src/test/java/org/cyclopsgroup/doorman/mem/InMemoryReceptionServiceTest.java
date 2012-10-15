package org.cyclopsgroup.doorman.mem;

import static org.junit.Assert.assertEquals;

import org.cyclopsgroup.doorman.api.beans.ClientDevice;
import org.cyclopsgroup.doorman.api.beans.ClientDeviceType;
import org.cyclopsgroup.doorman.api.beans.SessionCredential;
import org.cyclopsgroup.doorman.api.beans.StartSessionRequest;
import org.cyclopsgroup.doorman.api.beans.StartSessionResponse;
import org.junit.Before;
import org.junit.Test;

public class InMemoryReceptionServiceTest
{
    private InMemoryReceptionService receiption;

    @Before
    public void setUpService()
    {
        receiption = new InMemoryReceptionService( new Storage() );
    }

    /**
     * Verify start session logic
     */
    @Test
    public void testStartSession()
    {
        StartSessionRequest request = new StartSessionRequest();
        request.setTraceNumber( "trace-number" );

        ClientDevice device = new ClientDevice();
        device.setClientId( "clientId" );
        device.setDeviceType( ClientDeviceType.ANDROID );
        device.setUserAgent( "somewhere over the rainbow" );
        request.setClientDevice( device );

        StartSessionResponse response = receiption.startSession( "test", request );
        SessionCredential first = response.getSessionCredential();

        SessionCredential second = receiption.startSession( "test", request ).getSessionCredential();
        assertEquals( first, second );
    }
}
