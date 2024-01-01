package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component // <- 빈에 직접 등록안하고 컴포넌트-스캔 처리해도 됨
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") // <- Around: 모든 곳에 아래 메서드를 적용하겠음 ex. 만약 서비스만 하고 싶다면 (* hello.hellospring.service..*(..)) 를 파라미터로
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "MS");
        }
    }
}