package docker.test.mbean.spring;

import docker.test.redis.my.annotaion.MyCacheable;
import org.springframework.beans.BeansException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CacheConfig(cacheNames={"test"})
public class PersonController implements ApplicationContextAware {

    private PersonController selfProxy;

    @GetMapping("/cache/person")
    public Person person(String name, Integer desc) {
        System.out.println("begin person");
        return selfProxy.getPerson(name, desc);
    }

    //    对于使用@Cacheable标注的方法，Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，
    //    如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
    //    @CachePut也可以声明一个方法支持缓存功能。与@Cacheable不同的是使用@CachePut标注的方法在执行前不会
    //    去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
    //@CacheEvict是用来标注在需要清除缓存元素的方法或类上的
    //    @Caching注解可以让我们在一个方法或者类上同时指定多个Spring Cache相关的注解。 批量处理
    //    其拥有三个属性：cacheable、put和evict，分别用于指定@Cacheable、@CachePut和@CacheEvict。
    @Cacheable(key="'{getPersonTest}' + #p0")
    public Person getPerson(String name, Integer desc) {
        Person person = new Person();
        person.setName(name);
        person.setDesc(desc != null ? desc.toString() : "");
        return person;
    }

    @GetMapping("/cache/myPerson")
    public Person myPerson(String name, String desc) {
        System.out.println("begin myPerson");
        return selfProxy.getMyPerson(name, desc);
    }

    @MyCacheable(key="TestCache")
    public Person getMyPerson(String name, String desc) {
        Person person = new Person();
        person.setName(name);
        person.setDesc(desc);
        return person;
    }


    @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        selfProxy = applicationContext.getBean(PersonController.class);
    }
}