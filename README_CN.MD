​															**高性能响应式的API网关ship-gate**

# 描述

这是一个基于SpringWebFlux和Netty开发的高性能响应式的API网关，主要为了学习交流。

# 项目结构

- Ship-admin: 网关管理后台，支持路由规则配置，实例权重配置等。
- Ship-server: 网关服务端，核心模块。
- Ship-ship-client-spring-boot-starter: 网关客户端，自动向注册中心注册服务信息。
- Ship-gate-example: 如何使用网关的示例
- Ship-common: 一个公共的代码，比如POJO，常量和工具类等。

# Features

- 支持自定义您自己的插件
- 支持A/B测试和灰度发布  
- 支持多种负载均衡策略   
- 支持HTTP协议，包括 GET,PUT,POST 等
- 支持基于版本的动态路由规则
- 支持接口鉴权 (not implemented)
- 支持集群化部署
- 支持服务限流，基于Sentinel实现
# Usage

## 1.nacos

You can refer to this(https://nacos.io/en-us/docs/quick-start.html) to install the nacos.

## 2.create db

Use the ship.sql in the project to create database first.

## 3. ship-admin

You need change some config in application.yml.

| config item                 | Must | Desc                         |
| --------------------------- | ---- | ---------------------------- |
| server.port                 | Yes  | Ship-admin start port        |
| spring.datasource.*         | Yes  | Db config                    |
| nacos.discovery.server-addr | Yes  | address of nacos             |

then start the ship-admin,the login address of management backend  is http://localhost:9001/user/login/page ,default account  admin/1234.

## 4. backend project

New a springboot project and dependency in pom.xml.

```xml
 <dependency>
            <groupId>cn.sp</groupId>
            <artifactId>ship-client-spring-boot-starter</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```

Next change some config in application.yml.

| config item                 | Must | Desc                                                         |
| --------------------------- | ---- | ------------------------------------------------------------ |
| nacos.discovery.server-addr | Yes  | address of nacos                                             |
| ship.http.app-name          | Yes  | your app name of backend project,eg: order,the context-path is same with app-name|
| ship.http.version           | Yes  | version of app,eg:gray_1.0                                   |
| ship.http.port              | Yes  | the port of project,same with sever.port                     |
| ship.http.admin-url         | Yes  | the url of ship-admin,eg: 127.0.0.1:9001                     |

If you think it's trouble,you can just use the ship-gate-example for test.

## 5. ship-server

It's most important part of ship-gate,similarly you must config some option.

| config item                      | Must | Option value             | Desc                                      |
| -------------------------------- | ---- | ------------------------ | ----------------------------------------- |
| nacos.discovery.server-addr      | Yes  |                          | address of nacos                          |
| ship.gate.load-balance           | No   | random,round,weightRound | load balance algorithm,default round      |
| ship.gate.time-out-millis        | No   |                          | time out millis of gateway,default 3000ms |
| ship.gate.cache-refresh-interval | No   |                          | default 10s                               |

Finally start ship-sever and just have fun!



# Architecture diagram

![网关设计](http://wwww.2ysp.top/gateway.png)

# Prerequisite

- JDK1.8+

- MySQL

- Nacos 1.3+

  

If you want to know more details or have any question,please [click here](https://www.cnblogs.com/2YSP/p/14223892.html) or contact me in time.

  





