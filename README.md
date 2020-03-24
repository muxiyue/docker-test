rm#### 版本1.0.0
> 阶段性修改........

### 构建镜像并且推送到镜像中心
export DOCKER_HOST=tcp://172.16.1.191:2376
mvn clean install dockerfile:build dockerfile:push -DskipTests

docker swarm elk 代码辅助，说明见：https://blog.csdn.net/qq_30062125