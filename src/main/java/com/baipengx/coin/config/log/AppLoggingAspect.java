package com.baipengx.coin.config.log;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.baipengx.common.BaseLogger;

@Aspect
@Component
public class AppLoggingAspect extends BaseLogger{

    @Pointcut("execution(* com.baipengx..*(..))" +
            " && (@within(org.springframework.stereotype.Component)" +
            " || @within(org.springframework.stereotype.Controller)" +
            " || @within(org.springframework.stereotype.Service)" +
            " || @within(org.springframework.stereotype.Repository)" +
            " || @within(org.springframework.web.bind.annotation.RestController))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object logging(ProceedingJoinPoint point) throws Throwable {
        long time = 0L;
        // 此处判断可以节省准备参数的时间
        if (log.isDebugEnabled()) {
            log.debug("调用方法：" + point.getSignature().getDeclaringTypeName()
                    + "." + point.getSignature().getName()
                    + " 参数：" + Arrays.toString(point.getArgs()));
            time = System.currentTimeMillis();
        }
        Object result = null;
        try {
            result = point.proceed();
        } finally {
            if (log.isDebugEnabled())
                log.debug("结束方法：" + point.getSignature().getDeclaringTypeName()
                        + "." + point.getSignature().getName() + " 执行耗时：" + (System.currentTimeMillis() - time)
                        + "ms 返回值：" + result);
        }
        return result;
    }

}