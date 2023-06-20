**High Performance,Responsive API Gateways**

![](https://img.shields.io/github/stars/2YSP/ship-gate.svg)
![](https://img.shields.io/github/forks/2YSP/ship-gate.svg)
![](https://img.shields.io/github/release/2YSP/ship-gate.svg)

<a href="https://github.com/2YSP/ship-gate/blob/master/README_CN.MD">中文版</a>
# Description

This is a  high Performance,Responsive API Gateway based on SpringWebFlux and Netty.

Now it only supports HTTP protocol forward,it is for learning communication only.

# Project structure

- Ship-admin: gateway management console,supports config route rule etc.
- Ship-server: gateway server,core function module.
- Ship-ship-client-spring-boot-starter: gateway client,automatically register service information to the registration center.
- Ship-gate-example: an example of how to use the gateway
- Ship-common: some common codes,like pojo,constants,tools etc.

# Features

- Support custom your own plugins
- Support A/B test and grayscale publishing
- Support multiple load balancing strategies
- Support HTTP protocol,include GET,PUT,POST etc.
- Support version-based dynamic routing rules
- Support interface authentication (not implemented)
- Support cluster deployment
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

  





