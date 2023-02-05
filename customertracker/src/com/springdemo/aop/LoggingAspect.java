package com.springdemo.aop;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	// settup logger
	
	private Logger myLogger=Logger.getLogger(getClass().getName());
	
	//setup pointcut declaration
	
	@Pointcut("execution(* com.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void appFlow() {}
	
	//add @Before advice
	
	@Before("appFlow()")
	public void before(JoinPoint theJoinPoint) {
		
		
		//display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=======> in @Before calling method: " + theMethod);
		
		
		//display the arguments to the method.
		
		// get the arguments
		Object[] args = theJoinPoint.getArgs();
		
		//loop throw and display the args
		
		for(Object tempArg : args) {
			myLogger.info("==========> argument: " + tempArg);
		}
		
		
		
	}
	
	//add @AfterReturning advice
	
	@AfterReturning(
			pointcut="appFlow()",
			returning="theResult"
			)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=======> in @AfterReturning: from method: " + theMethod);
		
		myLogger.info("=====> result : " + theResult);
		
		
		
	}
	
	
}
