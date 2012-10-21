package org.cyclopsgroup.doorman.service.core;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.cyclopsgroup.doorman.api.ReceptionService;
import org.cyclopsgroup.doorman.api.SessionCredentialProvider;
import org.cyclopsgroup.doorman.api.beans.SessionCredential;
import org.cyclopsgroup.doorman.api.beans.SessionState;
import org.cyclopsgroup.doorman.api.beans.SessionVerificationResponse;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class SessionCheckingAdvice
{
    private final SessionCredentialProvider provider;

    private final ReceptionService reception;

    private final UserSessionConfig config;

    @Autowired
    public SessionCheckingAdvice( SessionCredentialProvider provider, ReceptionService reception,
                                  UserSessionConfig config )
    {
        this.provider = provider;
        this.reception = reception;
        this.config = config;
    }

    @Around( "execution(* org.cyclopsgroup.doorman.service.core.DefaultSessionService.*(..))" )
    public Object invoke( ProceedingJoinPoint point )
        throws Throwable
    {
        if ( point.getArgs().length == 0 )
        {
            return point.proceed();
        }
        SessionCredential cred = provider.getSessionCredential();

        SessionVerificationResponse verification = reception.verifySession( config.getDomainName(), cred );
        if ( verification.getSessionState() != SessionState.LIVE )
        {
            throw WebApplicationUtils.exceptionOf( "Caller fails to provide expected credentials to make the call to "
                                                       + point.getSignature() + " with result "
                                                       + verification.getSessionState(),
                                                   Response.Status.NOT_ACCEPTABLE );
        }

        // TODO The final matching algorithm should be a lot more complicated that this
        String sessionId = (String) point.getArgs()[0];
        if ( !StringUtils.equals( sessionId, cred.getSessionId() ) )
        {
            throw WebApplicationUtils.exceptionOf( "Caller provides incorrect credential information for call "
                                                       + point.getSignature(), Response.Status.NOT_ACCEPTABLE );
        }
        return point.proceed();
    }
}
