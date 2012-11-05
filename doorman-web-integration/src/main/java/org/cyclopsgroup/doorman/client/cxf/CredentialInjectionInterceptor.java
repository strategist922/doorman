package org.cyclopsgroup.doorman.client.cxf;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.cyclopsgroup.doorman.api.SessionCredentialProvider;
import org.cyclopsgroup.doorman.api.beans.SessionCredential;

public class CredentialInjectionInterceptor
    extends AbstractPhaseInterceptor<Message>
{
    private final SessionCredentialProvider provider;

    public CredentialInjectionInterceptor( SessionCredentialProvider provider )
    {
        super( Phase.PRE_STREAM );
        this.provider = provider;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void handleMessage( Message message )
    {
        SessionCredential credential = provider.getSessionCredential();
        if ( credential == null )
        {
            return;
        }
        MultivaluedMap<String, String> headers = new MetadataMap<String, String>();
        headers.add( SessionCredentialProvider.SESSION_CREDENTIAL_HEADER, credential.toToken() );
        message.put( Message.PROTOCOL_HEADERS, headers );
    }
}
