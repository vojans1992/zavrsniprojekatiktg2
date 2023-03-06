package com.iktpreobuka.zavrsni.logging;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlobalLogger {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalLogger.class);

    
    @Around("execution(public * *(..)) && within(com.iktpreobuka.zavrsni..*)")
    private Object logAroundEveryPublicMethod(ProceedingJoinPoint pjp) throws Throwable {
    	
        LOG.info("Invoked method: " + pjp.getSignature());

        Object returnedVal = pjp.proceed();

        return returnedVal;
    }

    @AfterThrowing("execution(public * *(..)) && within(com.iktpreobuka.zavrsni..*)")
    private void logExceptionsMehtod(JoinPoint pjp) throws Throwable {
        LOG.error("Exception thrown by method: " + pjp.getSignature().getName() + " in class: " + pjp.getTarget().getClass().getName());
    }
    
    
}
