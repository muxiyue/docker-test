package docker.test.redis.my;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

//定义切面
/////////@EnableAspectJAutoProxy
//@Component
//@Aspect()
public class MyCacheAspect {

    /**切面点*/
    private final String POINT_CUT = "execution(* docker.test..*(..))";
    @Pointcut(POINT_CUT)
    private void pointcut(){}

    //定义切入点
    @Pointcut(value="execution(* docker.test..*.person(..)) && args(a1,b2)", argNames = "a1,b2")
    public void beforePointcut(String a1, Integer b2) {}


    @Pointcut(value="execution(* docker.test..*.myPerson(..))")
    public void afterPointcut(){}


    // 参数传递
    @Before(value="beforePointcut(a,b)", argNames="a,b")
    public void beforecase1(String a,Integer b){
        System.out.println("1 a:" + a +" b:" + b);
    }

    // 参数传递
    //注意和beforecase1的区别是argNames的顺序交换了
    @Before(value="beforePointcut(b,a)", argNames="b,a")
    public void beforecase2(String b, Integer a){
        System.out.println("2 a:" + a +" b:" + b);
    }

    //定义通知



    //前置日志通知
    @Before(value = POINT_CUT)
    public void beforeAdvice2(JoinPoint joinPoint) {
        System.out.println("===========beforeAdvice2:" + joinPoint);
    }

    //后置日志通知
    @After(value = "afterPointcut()")
    public void afterAdvice(JoinPoint joinPoint) {
        System.out.println("===========after advice " + joinPoint);
    }

    @AfterThrowing(value = POINT_CUT,throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint,Throwable exception){
        //目标方法名：
        System.err.println(joinPoint.getSignature().getName());
        exception.printStackTrace();
    }


    // 表示标注了指定注解的目标类方法
    @Around(value = "@annotation(docker.test.redis.my.annotaion.MyCacheable)")
    public Object processTx(ProceedingJoinPoint jp) throws Throwable{
        System.out.println("执行目标方法之前，模拟开始事务...");
        Object rvt=jp.proceed(jp.getArgs());
        System.out.println("执行目标方法之后，模拟结束事务...");
        return rvt;
    }



    @Autowired DefaultListableBeanFactory beanFactory;

    /**
     * SpEL标示符
     */
    private static final String MARK = "$";



    private String parseStr(String expirationStr) {
        if (expirationStr.contains(MARK)) {
            expirationStr = beanFactory.resolveEmbeddedValue(expirationStr);
        }
        return expirationStr;
    }
}
