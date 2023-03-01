package com.iktpreobuka.zavrsni.logging;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlobalLogger {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalLogger.class);

//    @Pointcut("within(com.iktpreobuka.zavrsni.controllers.UserController)")
//    private void everythingInMyApplication() {}
//
//    @Before("com.iktpreobuka.zavrsni.logging.MyAspect.everythingInMyApplication()")
//    public void logMethodName(JoinPoint joinPoint) {
//        LOG.info("Called {}", joinPoint.getSignature().getName());
//    }
//    @After("com.iktpreobuka.zavrsni.logging.MyAspect.everythingInMyApplication()")
//    public void logMethodName2(JoinPoint joinPoint) {
//        LOG.info("Exited {}", joinPoint.getSignature().getName());
//    }
    
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
