package com.Hrms.Configuration;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Aspect
@Component
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class LoggingAdvicePayroll {
	Logger log = LoggerFactory.getLogger(LoggingAdvicePayroll.class);

	@Pointcut(value = "execution(* com.Hrms.Payroll.*.*.*(..) )")
	public void myPointcut() {

	}

	@Around("myPointcut()")
	public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		// mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().toString();
		Object[] array = pjp.getArgs();
		log.info("Incoming Request");

		log.info(
				"===============================================================================================================");
		log.info(
				"===============================================================================================================");

		log.info("method invoked " + className + " : " + methodName + "()" + "arguments : "
				+ mapper.writeValueAsString(array));

		Object object = pjp.proceed();
		log.info("Outgoing Response");

		log.info(
				"===============================================================================================================");
		log.info(
				"===============================================================================================================");

		log.info(className + " : " + methodName + "()" + "Response : " + mapper.writeValueAsString(object));

		return object;
	}

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void controller() {

	}

	@Before("controller()&& args(..,request)")
	public void logBefore(JoinPoint joinPoint, HttpServletRequest request) {

		log.info("Entering in Method :  " + joinPoint.getSignature().getName());
		log.info("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
		log.info("Target class : " + joinPoint.getTarget().getClass().getName());

		if (null != request) {
			log.info("Start Header Section of request ");
			log.info("Method Type : " + request.getMethod());
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				log.info("Header Name: " + headerName + " Header Value : " + headerValue);
			}
			log.info("Request Path info :" + request.getServletPath());
			log.info("End Header Section of request ");
		}
	}

}
