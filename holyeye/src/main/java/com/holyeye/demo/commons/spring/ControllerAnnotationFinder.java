package com.holyeye.demo.commons.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

/**
 * 스프링의 Interceptor에서 컨트롤러의 Annotation 정보를 검색합니다.<br/>
 * Spring 3.1 이상만 사용가능
 * 
 * @see org.springframework.web.method.HandlerMethod
 * 
 * @author holyeye
 * 
 */
public class ControllerAnnotationFinder {

	private Map<Method, Annotation> annotationMap = new HashMap<Method, Annotation>();

	public <A extends Annotation> A getAnnotation(Object handler, Class<A> returnedClass) {

		if(!(handler instanceof HandlerMethod)) {
			throw new IllegalArgumentException("HandlerMethod Type이 아닙니다. param=" + handler.getClass().getName());
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;

		Method method = handlerMethod.getMethod();

		// 컨트롤러 어노테이션 정보 설정
		if (!annotationMap.containsKey(method)) {

			A findAnnotation = findAnnotationByMethodOrClass(handlerMethod, returnedClass);
			annotationMap.put(method, findAnnotation);
		}

		return (A) annotationMap.get(method);

	}

	private <A extends Annotation> A findAnnotationByMethodOrClass(HandlerMethod targetMethod, Class<A> returnedClass) {

		A methodAnnotation = targetMethod.getMethodAnnotation(returnedClass);
		if (methodAnnotation != null) {
			return methodAnnotation;
		}

		Object targetControllerClass = targetMethod.getBean();
		A classAnnotation = AnnotationUtils.findAnnotation(targetControllerClass.getClass(), returnedClass);
		if (classAnnotation != null) {
			return classAnnotation;
		}

		return null;
	}

}
