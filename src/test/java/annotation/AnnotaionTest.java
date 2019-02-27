package annotation;

import docker.test.DockerTestApplication;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2019/2/22 下午2:01
 * @Modified By:
 */
public class AnnotaionTest {

    @Test
    public void test() {
        Class<DockerTestApplication> clazz = DockerTestApplication.class;
        // 得到类注解
        SpringBootApplication myClassAnnotation = clazz.getAnnotation(SpringBootApplication.class);

//        // 得到构造方法注解
//        Constructor<TestAnnotation> cons = clazz.getConstructor(new Class[]{});
//        MyConstructorAnnotation myConstructorAnnotation = cons.getAnnotation(MyConstructorAnnotation.class);
//        System.out.println(myConstructorAnnotation.desc() + " "+ myConstructorAnnotation.uri());
//
//        // 获取方法注解
//        Method method = clazz.getMethod("setId", new Class[]{int.class});
//        MyMethodAnnotation myMethodAnnotation = method.getAnnotation(MyMethodAnnotation.class);
//        System.out.println(myMethodAnnotation.desc() + " "+ myMethodAnnotation.uri());
//        // 获取字段注解
//        Field field = clazz.getDeclaredField("id");
//        MyFieldAnnotation myFieldAnnotation = field.getAnnotation(MyFieldAnnotation.class);
//        System.out.println(myFieldAnnotation.desc() + " "+ myFieldAnnotation.uri());
    }
}
