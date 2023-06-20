​															**高性能响应式的API网关ship-gate**

# 描述

这是一个基于SpringWebFlux和Netty开发的高性能响应式的API网关，主要为了学习交流。

# 项目结构

- Ship-admin: 网关管理后台，支持路由规则配置，实例权重配置等。
- Ship-server: 网关服务端，核心模块。
- Ship-ship-client-spring-boot-starter: 网关客户端，自动向注册中心注册服务信息。
- Ship-gate-example: 如何使用网关的示例
- Ship-common: 一个公共的代码，比如POJO，常量和工具类等。

# 特性

- 支持自定义您自己的插件
- 支持A/B测试和灰度发布  
- 支持多种负载均衡策略   
- 支持HTTP协议，包括 GET,PUT,POST 等
- 支持基于版本的动态路由规则
- 支持接口鉴权 (not implemented)
- 支持集群化部署
- 支持服务限流，基于Sentinel实现
# 用法

## 1.nacos

你可以参考这个(https://nacos.io/en-us/docs/quick-start.html) 去安装nacos。

## 2.创建数据库

首先，使用项目里的ship.sql去创建数据库

## 3. 启动ship-admin

你需要修改application.yml文件中的一些配置

| 配置项                 | 是否必须 | 描述                         |
| --------------------------- | ---- | ---------------------------- |
| server.port                 | 是  | Ship-admin的启动端口号        |
| spring.datasource.*         | 是  | 数据库配置                   |
| nacos.discovery.server-addr | 是  | Nacos服务端的地址           |

然后本地启动ship-admin，后台登录地址是http://localhost:9001/user/login/page ，默认账号admin/1234。

## 4. 创建后端服务

创建一个springboot项目并在pom.xml添加以下依赖

```xml
 <dependency>
            <groupId>cn.sp</groupId>
            <artifactId>ship-client-spring-boot-starter</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```

接着在application.yml文件修改一些配置

| 配置项                 | 是否必须 | 描述                                                         |
| --------------------------- | ---- | ------------------------------------------------------------ |
| nacos.discovery.server-addr | 是  | 注册中心nacos的地址                                            |
| ship.http.app-name          | 是  | 后台服务的appName,如：order,请求上下文和appName保持一致|
| ship.http.version           | 是  | 后台服务的版本,如:gray_1.0                                   |
| ship.http.port              | 是  | 项目的端口号,与sever.port一致                     |
| ship.http.admin-url         | 是  | ship-admin的访问URL,如：127.0.0.1:9001                     |

如果你认为这太麻烦，你可以直接使用ship-gate-example工程去测试。

## 5. ship-server

这是ship-gate最重要的部分，同样的你必须配置一些选项。

| 配置项                      | 是否必须 | 可选项            | 描述                                      |
| -------------------------------- | ---- | ------------------------ | ----------------------------------------- |
| nacos.discovery.server-addr      | 是  |                          | 注册中心nacos的地址                              |
| ship.gate.load-balance           | 否   | random,round,weightRound | 负载均衡算法，默认round（轮询）      |
| ship.gate.time-out-millis        | 否   |                          | 网关请求超时时间，默认3000ms |
| ship.gate.cache-refresh-interval | 否   |                          | 缓存刷新间隔，默认 10s                               |

最后启动ship-sever，祝你玩得开心！


# 架构设计

![网关设计](http://wwww.2ysp.top/gateway.png)

# 环境要求

- JDK1.8+

- MySQL

- Nacos 1.3+

  
如果您想了解更多详细信息或有任何问题，请[点击此处](https://www.cnblogs.com/2YSP/p/14223892.html)或者及时与我联系。



  





