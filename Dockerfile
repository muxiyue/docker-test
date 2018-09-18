# 拉取基础镜像
#FROM hub.c.163.com/library/java:8-jdk
FROM hub.c.163.com/library/tomcat:8.5.14-jre8
# 镜像的作者
MAINTAINER csp@xxx.com

#挂载目录，通过 VOLUME 指令创建的挂载点，无法指定主机上对应的目录，是自动生成的
#VOLUME ["/data1","/data2"]

#RUN ["mkdir", "-p", "/app"]

#结合maven插件dockerfile-maven-plugin的打包使用
ARG JAR_FILE

#ADD ${JAR_FILE} /app/app.jar

#放置到tomcat的webapps目录下
ADD ${JAR_FILE}  /usr/local/tomcat/webapps/app.war

#为后面的 RUN, CMD, ENTRYPOINT, ADD 或 COPY 指令设置镜像中的当前工作目录。
#WORKDIR  /usr/local/docker/test

#拷贝当前目录文件到容器/app
#COPY .  /app

#与 COPY 类似，从 build context 复制文件到镜像。不同的是，如果 src 是归档文件（tar, zip, tgz, xz 等），文件会被自动解压到 dest。
#ADD src dest

#设置环境变量，环境变量可被后面的指令使用
#ENV EVN_SET_TEST "WELCOME TO DOCKERFILE CONTAINER！'

##################
#  RUN、CDM、ENTRYPOINT 命令都包含两种格式：Shell 格式和 Exec 格式
#  CMD还可以放在ENTRYPOINT后，为其传递参数。
#####  shell 格式：######
##  底层会调用 /bin/sh -c <command>

# 在容器中运行指定的命令
#RUN echo $EVN_SET_TEST

# 容器启动命令 只有最后一个生效，CMD 可以被 docker run 之后的参数替换。
#只有最后一个生效
#CMD echo "CMD Hello world"

#配置容器启动时运行的命令
#ENTRYPOINT echo "ENTRYPOINT Hello, $EVN_SET_TEST"

######  Exec 格式： #####
## 当指令执行时，会直接调用 <command>，不会被 shell 解析
# ENTRYPOINT ["/bin/echo", "Hello, $EVN_SET_TEST"]
# 正确写法应该为：
# ENTRYPOINT ["/bin/sh", "-c", "echo Hello, $EVN_SET_TEST"]

# 为Exec 格式的ENTRYPOINT传递参数，结果输出Hello, $EVN_SET_TEST dockerfile world
# CMD ["dockerfile world"]

#只有最后一个生效
#ENTRYPOINT ["java","-jar","/app/app.jar"]





#表示哪个端口提供服务的提示，宿主机如果要访问，需要结合-P参数联合使用。
#EXPOSE 8080