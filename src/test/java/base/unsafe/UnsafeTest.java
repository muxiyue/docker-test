package base.unsafe;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2020/2/5 下午1:43
 * @Modified By:
 * @Version:
 * @TaskId:
 */
@Slf4j
public class UnsafeTest {

    private static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


    @Test
    public void getUnsafe() {

        System.out.println(reflectGetUnsafe());

//        // 其一，从getUnsafe方法的使用限制条件出发，通过Java命令行命令
//        // -Xbootclasspath/a把调用Unsafe相关方法的类A所在jar包路径追加到默认的bootstrap路径中
//        // 使得A被引导类加载器加载，从而通过Unsafe.getUnsafe方法安全的获取Unsafe实例。
//        // java -Xbootclasspath/a:base.unsafe  // 其中path为调用Unsafe相关方法的类所在jar包路径
//        System.out.println(Unsafe.getUnsafe());
    }
}
