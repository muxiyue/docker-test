package proxy.AspectJ;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AnnoAspect {

    @Pointcut("execution(* proxy.AspectJ.App.say(..))")
    public void jointPoint() {
    }

//    @Pointcut("execution(* proxy.AspectJ.App.innerSay(..))")
//    public void innerSay() {
//    }
//
//    @Around("innerSay()")
//    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("begin innerSay around say");
//        joinPoint.proceed();
//        System.out.println("begin innerSay around say");
//    }

    @Before("jointPoint()")
    public void before() {
        System.out.println("AnnoAspect before say");
    }


    @After("jointPoint()")
    public void after() {
        System.out.println("AnnoAspect after say");
    }

}