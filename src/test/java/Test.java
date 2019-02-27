import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.SynthesizedAnnotation;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Test {

    @org.junit.Test public void test() throws ClassNotFoundException {
        Class<?> c = (Class.forName(SynthesizedAnnotation.class.getName(), false, Cacheable.class.getClassLoader()));
        System.out.println(c);
        System.out.println(c == SynthesizedAnnotation.class);
    }

    @org.junit.Test
    public void test2() {
        System.out.println(UUID.randomUUID().toString().length());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString().replace("-", "").toLowerCase());
    }

    @org.junit.Test
    public void test3() {
        System.out.println("aaaaxxx".hashCode());
        System.out.println("aaa22222".hashCode());
    }

    @org.junit.Test
    public void testFind() {
        String aaa = ",1111,222,333,444,555,666,777,88,999,00000,00000,1111,ffddf,aff,,";
        List list = Arrays.asList(aaa.split(","));
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            aaa.indexOf("bbbbbbb");
        }
        System.out.println("字符串查找  cost " + (System.currentTimeMillis() - begin) + "ms");


        begin = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list.indexOf("bbbbbbb");
        }
        System.out.println("列表查找  cost " + (System.currentTimeMillis() - begin) + "ms");

    }
}
