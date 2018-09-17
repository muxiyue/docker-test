package docker.test.redis.my.annotaion;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyCacheable {
    String key() default "";
    String expireTimes() default "";
}
