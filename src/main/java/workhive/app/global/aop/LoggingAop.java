package workhive.app.global.aop;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;
import workhive.app.AppApplication;
import workhive.app.global.aop.log.LogTrace;
import workhive.app.global.aop.log.TraceStatus;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAop {

    private final LogTrace logTrace;

    @Pointcut("execution(* workhive.app.*.service..*(..))")
    private void logService(){}


    @Pointcut("execution(* workhive.app.*.controller..*(..)) && !execution(* workhive.app.*.controller.advice..*(..))")
    private void logController(){}

    @Pointcut("execution(* workhive.app.*repository..*(..))")
    private void logRepository(){}


    @Around("logService() || logRepository() || logController()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;

        String message = getMessage(joinPoint);
        if (StringUtils.isNotEmpty(message)) {
            try {
                status = logTrace.begin(message);
                Object result = joinPoint.proceed();
                logTrace.end(status);
                return result;
            } catch (RuntimeException e) {
                logTrace.exception(status, e);
                throw e;
            }
        } else {
            return joinPoint.proceed();
        }
    }


    private String getMessage(ProceedingJoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        Class<?> targetClass = getTargetClass(target);

        String rootPackageName = AppApplication.class.getPackageName();
        String packageName = targetClass.getPackageName();

        if (packageName.startsWith(rootPackageName)) {
            String className = targetClass.getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            return String.format("%s.%s.%s", packageName, className, methodName);
        }else {
            return "";
        }
    }

    private Class<?> getTargetClass(Object target) {
        if (AopUtils.isAopProxy(target)) {
            return AopProxyUtils.ultimateTargetClass(target);
        } else {
            return target.getClass();
        }
    }
}
