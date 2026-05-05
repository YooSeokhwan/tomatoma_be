package com.tomatoma.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Controller 계층: public 메서드만 (생성자, private 제외)
    @Pointcut("execution(public * com.tomatoma.controller..*(..))")
    private void controllerLayer() {}

    // Service 계층: public 메서드만 (생성자, private 제외)
    @Pointcut("execution(public * com.tomatoma.service..*(..))")
    private void serviceLayer() {}

    /**
     * Controller 메서드 실행 시간 측정 + 요청/응답 로깅.
     * @Around: 메서드 실행 전후를 모두 감싸서 제어
     */
    @Around("controllerLayer()")
    public Object logControllerExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("[API 요청] {}.{}() - 파라미터: {}", className, methodName, Arrays.toString(args));

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.info("[API 완료] {}.{}() - 실행시간: {}ms", className, methodName, elapsed);
            return result;
        } catch (Throwable ex) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("[API 오류] {}.{}() - 실행시간: {}ms, 예외: {}", className, methodName, elapsed, ex.getMessage());
            throw ex;
        }
    }

    /**
     * Service 메서드에서 예외 발생 시 자동 로깅.
     * @AfterThrowing: 예외가 던져진 직후 실행
     */
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logServiceException(JoinPoint joinPoint, Exception ex) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.error("[서비스 예외] {}.{}() - {}: {}", className, methodName,
                ex.getClass().getSimpleName(), ex.getMessage());
    }
}
