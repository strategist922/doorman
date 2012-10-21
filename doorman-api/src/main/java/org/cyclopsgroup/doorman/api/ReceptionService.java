package org.cyclopsgroup.doorman.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cyclopsgroup.doorman.api.beans.SessionCredential;
import org.cyclopsgroup.doorman.api.beans.SessionVerificationResponse;
import org.cyclopsgroup.doorman.api.beans.StartSessionRequest;
import org.cyclopsgroup.doorman.api.beans.StartSessionResponse;

@Path( "reception" )
public interface ReceptionService
{
    @Path( "/{domain}/startSession" )
    @POST
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    StartSessionResponse startSession( @PathParam( "domain" )
                                       String domain, StartSessionRequest request );

    @Path( "/{domain}/verifySession" )
    @POST
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    SessionVerificationResponse verifySession( @PathParam( "domain" )
                                               String domain, SessionCredential credential );
}
