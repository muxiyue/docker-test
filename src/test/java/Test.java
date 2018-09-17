import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.SynthesizedAnnotation;

public class Test {

    @org.junit.Test public void test() throws ClassNotFoundException {
        Class<?> c = (Class.forName(SynthesizedAnnotation.class.getName(), false, Cacheable.class.getClassLoader()));
        System.out.println(c);
        System.out.println(c == SynthesizedAnnotation.class);
    }

}
