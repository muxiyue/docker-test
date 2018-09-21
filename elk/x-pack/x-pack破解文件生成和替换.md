### 生成x-pack-core-6.4.0.jar 
> 当前目录下的即为生成好的，hub.c.163.com/muxiyue/elasticsearch:6.4.0-crack中已经替换。
替换路径为/usr/share/elasticsearch/modules/x-pack-core
#### 1.编译

```
javac -Djava.ext.dirs=lib/ org/elasticsearch/license/LicenseVerifier.java

javac -Djava.ext.dirs=lib/ org/elasticsearch/xpack/core/XPackBuild.java

```

#### 2.覆盖打包

```
jar uvf x-pack-core-6.4.0.jar org/elasticsearch/license/LicenseVerifier.class
jar uvf x-pack-core-6.4.0.jar org/elasticsearch/xpack/core/XPackBuild.class

```

### 获取证书

获取 license 证书

①https://license.elastic.co/registration 填些用户名，邮箱（重要，获取下载链接），Country选择China，其他信息随意填写

②打开邮箱获取的地址，将下载后的文件改名为license.json

③修改文件中的内容，将两个属性改为

将 "type":"basic" 替换为 "type":"platinum"    # 基础版变更为铂金版

将 "expiry_date_in_millis":1561420799999替换为 "expiry_date_in_millis":3107746200000    # 1年变为50年

④使用curl替换 license(license.json指的是刚刚下载修改属性后的证书，要开启elasticsearch服务)

curl -u elastic:elastic -H 'Content-Type: application/json' -XPUT '172.16.1.191:9200/_xpack/license?acknowledge=true' -d @license.json


⑤若失败，修改配置文件

xpack.security.enabled: false

破解成功后改回来

xpack.security.enabled: true

xpack.security.transport.ssl.enabled: true

⑥上传后查看证书时间

curl -u elastic:elastic -XGET http://172.16.1.191:9200/_license

curl -u elastic:elastic -XGET http://172.16.1.191:9200/_xpack/security/user

curl -u elastic:elastic 172.16.1.191:9200/_cat/nodes?v



### CA证书生成

> 进入容器的默认目录 /usr/share/elasticsearch
```
# Enter IP Addresses 127.0.0.1
# Enter DNS names  elasticsearch
./bin/x-pack/certgen
unzip certificate-bundle.zip -d config
```