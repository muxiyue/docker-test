package docker.test.redis.redisson;

import lombok.Data;
import lombok.ToString;

/**
 * @author Abbot
 * @des redis 池配置
 * @date 2018/10/18 10:43
 **/
@Data
@ToString
public class RedisPoolProperties {

    private int maxIdle;

    private int minIdle;

    private int maxActive;

    private int maxWait;

    private int connTimeout;

    private int soTimeout;

    /**
     * 池大小
     */
    private  int size;

}