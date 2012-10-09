package org.cyclopsgroup.doorman.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.cyclopsgroup.doorman.api.beans.StartSessionRequest;
import org.cyclopsgroup.doorman.api.beans.StartSessionResponse;

@Path( "receiption" )
public interface ReceiptionService
{
    @Path( "/{domain}/startSession" )
    @POST
    StartSessionResponse startSession( @PathParam( "domain" )
                                       String domain, StartSessionRequest request );
}
