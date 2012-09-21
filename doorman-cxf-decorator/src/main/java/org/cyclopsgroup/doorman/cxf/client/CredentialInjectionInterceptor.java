package org.cyclopsgroup.doorman.cxf.client;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.cyclopsgroup.doorman.api.SessionCredential;
import org.cyclopsgroup.doorman.api.SessionCredentialProvider;
import org.cyclopsgroup.doorman.cxf.CredentialUtils;

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
        headers.add( CredentialUtils.HTTP_HEADER_NAME, CredentialUtils.toString( credential ) );
        message.put( Message.PROTOCOL_HEADERS, headers );
    }
}
