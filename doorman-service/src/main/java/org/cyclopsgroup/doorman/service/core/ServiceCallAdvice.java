package org.cyclopsgroup.doorman.service.core;

import java.util.Arrays;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

/**
 * Aspect that prints out every invocation
 */
@Aspect
@Service
public class ServiceCallAdvice
{
    @Around( "execution(* org.cyclopsgroup.doorman.service.core.*Service.*(..))" )
    public Object invoke( ProceedingJoinPoint point )
        throws Throwable
    {
        Signature sig = point.getSignature();
        String trace = "[" + RandomStringUtils.randomAlphabetic( 8 ) + "]";
        Log log = LogFactory.getLog( sig.getDeclaringType() );
        log.info( trace + " Invoking (" + sig + ") with " + Arrays.toString( point.getArgs() ) + " against "
            + point.getTarget() );
        long start = System.currentTimeMillis();
        boolean successful = false;
        Object result = null;
        try
        {
            result = point.proceed();
            successful = true;
            return result;
        }
        finally
        {
            long elapsed = System.currentTimeMillis() - start;
            if ( successful )
            {
                log.info( trace + " Invocation of " + sig.getName() + " succeeded and returned " + result + " after "
                    + elapsed + "ms" );
            }
            else
            {
                log.error( trace + " Invocation of " + sig.getName() + " failed after " + elapsed + "ms" );
            }
        }
    }
}
