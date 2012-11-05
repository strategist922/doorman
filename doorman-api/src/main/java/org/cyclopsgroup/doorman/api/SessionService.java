package org.cyclopsgroup.doorman.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cyclopsgroup.doorman.api.beans.FacebookLoginRequest;
import org.cyclopsgroup.doorman.api.beans.LoginResponse;
import org.cyclopsgroup.doorman.api.beans.UserCredential;
import org.cyclopsgroup.doorman.api.beans.UserOperationResponse;
import org.cyclopsgroup.doorman.api.beans.UserOperationResult;
import org.cyclopsgroup.doorman.api.beans.UserSession;

/**
 * The facade service that manages user authentication, session management and user management
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@Path( "session" )
public interface SessionService
{
    /**
     * Confirm a sign up request with given token
     *
     * @param sessionId Current session ID
     * @param userId Id of user to confirm
     * @param token Token to confirm
     * @return Opeation result
     */
    @POST
    @Path( "/{sessionId}/confirm/{userId}/{token}" )
    @Consumes( MediaType.TEXT_PLAIN )
    @Produces( MediaType.TEXT_PLAIN )
    UserOperationResult confirmSignUp( @PathParam( "sessionId" )
                                       String sessionId, @PathParam( "userId" )
                                       String userId, @PathParam( "token" )
                                       String token );

    /**
     * Get details of current session
     *
     * @param sessionId Id of current session
     * @return Session model
     */
    @GET
    @Path( "/{sessionId}" )
    @Consumes( MediaType.TEXT_PLAIN )
    @Produces( MediaType.APPLICATION_JSON )
    UserSession getSession( @PathParam( "sessionId" )
                            String sessionId );

    /**
     * Sign in and associated user with current session
     *
     * @param sessionId Id of current session
     * @param user User to sign in
     * @param password Password for authentication
     * @return Operation result
     */
    @POST
    @Path( "/{sessionId}/login" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    LoginResponse login( @PathParam( "sessionId" )
                         String sessionId, UserCredential credential );

    @POST
    @Path( "/{sessionId}/loginWithFacebook" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    LoginResponse loginWithFacebook( String sessionId, FacebookLoginRequest request );

    /**
     * Sign out from current session
     *
     * @param sessionId Session ID of current session
     * @return Operation result
     */
    @POST
    @Path( "/{sessionId}/logout" )
    @Consumes( MediaType.TEXT_PLAIN )
    @Produces( MediaType.APPLICATION_JSON )
    UserOperationResponse logout( @PathParam( "sessionId" )
                                  String sessionId );

    /**
     * Update existing session
     *
     * @param sessionId ID of session to update
     * @return Current user session
     */
    @POST
    @Path( "/{sessionId}/ping" )
    @Consumes( MediaType.TEXT_PLAIN )
    @Produces( MediaType.TEXT_PLAIN )
    UserSession pingSession( @PathParam( "sessionId" )
                             String sessionId );

    /**
     * Create request for new user sign up. Request needs to be confirmed, {@link #confirmSignUp(String, String)},
     * before user is created
     *
     * @param sessionId Current session ID
     * @param user User details
     * @return Sign up operation result
     */
    @POST
    @Path( "/{sessionId}/requestUser" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.TEXT_PLAIN )
    UserSignUpResult requestSignUp( @PathParam( "sessionId" )
                                    String sessionId, User user );

    /**
     * Sign up new user directly with request/confirm process
     *
     * @param sessionId Current user session Id
     * @param user User request to sign up
     * @param type Type of user to sign up
     * @return Operation result
     */
    @POST
    @Path( "/{sessionId}/signUp" )
    @Consumes( MediaType.TEXT_PLAIN )
    @Produces( MediaType.TEXT_PLAIN )
    UserOperationResult signUp( @PathParam( "sessionId" )
                                String sessionId, User user, @MatrixParam( "type" )
                                @DefaultValue( "LOCAL" )
                                UserType type );
}
