package web.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import docker.test.DockerTestApplication;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2020/3/24 上午9:32
 * @Modified By:
 * @Version:
 * @TaskId:
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { DockerTestApplication.class })
public class RedissonTest {

    @Autowired RedissonClient redisson;


    @Test
    public void testLock() throws InterruptedException {
        RLock lock = redisson.getLock("test");
        lock.lock(100, TimeUnit.SECONDS);

        System.out.println("main:" + Thread.currentThread().getId());

        // 跨线程无法释放。
        new Thread(()-> {
            System.out.println("thread:" + Thread.currentThread().getId());
            lock.unlock();
        }).start();

        Thread.sleep(1000);

        lock.lock();
        Thread.sleep(1000000);
        lock.unlock();
    }
}
