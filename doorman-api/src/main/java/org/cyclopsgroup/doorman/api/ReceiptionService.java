package org.cyclopsgroup.doorman.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.cyclopsgroup.doorman.api.beans.StartSessionRequest;
import org.cyclopsgroup.doorman.api.beans.StartSessionResponse;

@Path( "receiption" )
public interface ReceiptionService
{
    @Path( "/{domain}/startSession" )
    @POST
    @Consumes( MediaType.APPLICATION_JSON )
    StartSessionResponse startSession( @PathParam( "domain" )
                                       String domain, StartSessionRequest request );
}
