* [alpha,针对企业级项目的快速构建](#alpha针对企业级项目的快速构建)
      * [1. <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-application">alpha-application</a>](#1-alpha-application)
         * [1.1 说明](#11-说明)
         * [1.2 子模块](#12-子模块)
            * [1.2.1 <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-application/alpha-application-example">alpha-application-example</a>](#121-alpha-application-example)
      * [2. <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-business">alpha-business</a>](#2-alpha-business)
         * [2.1 说明](#21-说明)
         * [2.2 子模块](#22-子模块)
            * [2.2.1 <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-business/common-business-model">common-business-model</a>](#221-common-business-model)
            * [2.2.2 <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-business/common-business-service">common-business-service</a>](#222-common-business-service)
      * [3. <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-tools">alpha-tools</a>](#3-alpha-tools)
         * [3.1 说明](#31-说明)
         * [3.2 子模块](#32-子模块)
            * [3.2.1 <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-tools/alpha-distribute-lock">alpha-distribute-lock</a>](#321-alpha-distribute-lock)
            * [3.2.2 <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-tools/alpha-job">alpha-job</a>](#322-alpha-job)
            * [3.2.3 <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-tools/alpha-redis">alpha-redis</a>](#323-alpha-redis)
            * [3.2.4 <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-tools/alpha-shiro">alpha-shiro</a>](#324-alpha-shiro)
            * [3.2.5 <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-tools/alpha-queue">alpha-queue</a>](#325-alpha-queue)
      * [4. <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-web">alpha-web</a>](#4-alpha-web)
         * [4.1 说明](#41-说明)
         * [4.2 子模块](#42-子模块)
            * [4.2.1 <a href="https://github.com/coutPKprintf/alpha/tree/master/alpha-web/alpha-web-example">alpha-web-example</a>](#421-alpha-web-example)
      * [5. <a href="https://github.com/coutPKprintf/alpha/tree/master/common-application">common-application</a>](#5-common-application)
      * [6. <a href="https://github.com/coutPKprintf/alpha/tree/master/common-config">common-config</a>](#6-common-config)
      * [7. <a href="https://github.com/coutPKprintf/alpha/tree/master/common-enums">common-enums</a>](#7-common-enums)
      * [8. <a href="https://github.com/coutPKprintf/alpha/tree/master/common-mybatis">common-mybatis</a>](#8-common-mybatis)
      * [9. <a href="https://github.com/coutPKprintf/alpha/tree/master/common-util">common-util</a>](#9-common-util)
      * [10. <a href="https://github.com/coutPKprintf/alpha/tree/master/common-web">common-web</a>](#10-common-web)
      * [11. <a href="https://github.com/coutPKprintf/alpha/tree/master/db">db</a>](#11-db)
      * [12. <a href="https://github.com/coutPKprintf/alpha/tree/master/docs">docs</a>](#12-docs)
      * [13. <a href="https://github.com/coutPKprintf/alpha/tree/master/script">script</a>](#13-script)

#alpha,针对企业级项目的快速构建
<pre>
    为了快速构建企业项目,此项目实现了基本模块的拆分与实现. 其中主要功能实现了有如下 :
        1. redission分布式锁的aop实现[alpha-tools/alpha-distribute-lock]
        2. elastic-job分布式定时任务的整合[alpha-tools/alpha-job]
        3. alpha-redis分布式缓存实现[alpha-tools/alpha-redis]
        4. shiro统一权限与登录[alpha-tools/alpha-shiro] 
        5. mybatis集成及分页插件集成[common-mybatis]
        6. excel通用导入导出注解形式实现[common-util]
        7. fastjson统一序列化的设计,自动将下划线的参数序列化成头峰式的变量[common-web/fastjson]
        8. hibernate-validator统一校验器的集成. 基础校验器的实现, 如身份证,手机号等.[common-web/validator]
        9. 异常处理的设计,错误码,返回值的设计[common-web/result,common-web/exception]
        10. 整合CGLIB实现对类的AOP,对注解@Transactional对Controller有效[common-mybatis/config].
        11. 整合rabbitmq队列,实现简化消息队列的推送及监听.[alpha-tools/alpha-queue]
</pre>
##1. [alpha-application](https://github.com/coutPKprintf/alpha/tree/master/alpha-application)
### 1.1 说明
<pre>
    本模块主要实现所有非web应用的项目. 列如定时任务项目等.
</pre>

### 1.2 子模块
#### 1.2.1 [alpha-application-example](https://github.com/coutPKprintf/alpha/tree/master/alpha-application/alpha-application-example)
<pre>
    本模块是非web项目的一个启动样例.
</pre>

##2. [alpha-business](https://github.com/coutPKprintf/alpha/tree/master/alpha-business)
### 2.1 说明
<pre>
    本模块主要实现项目中所有的业务.
</pre>

### 2.2 子模块
#### 2.2.1 [common-business-model](https://github.com/coutPKprintf/alpha/tree/master/alpha-business/common-business-model)
<pre>
    本模块主要实现项目中业务中依赖的公共model.
</pre>

#### 2.2.2 [common-business-service](https://github.com/coutPKprintf/alpha/tree/master/alpha-business/common-business-service)
<pre>
    本模块主要实现项目中公共依赖的服务类,需要保持事务性的集约服务.
</pre>

## 3. [alpha-tools](https://github.com/coutPKprintf/alpha/tree/master/alpha-tools)
### 3.1 说明
<pre>
    本模块主要实现项目中的使用工具. 如redis,elastic-job定时任务,分布式锁.
</pre>

### 3.2 子模块
#### 3.2.1 [alpha-distribute-lock](https://github.com/coutPKprintf/alpha/tree/master/alpha-tools/alpha-distribute-lock)
<pre>
    本模块主要实现项目中需要的分布式锁的功能.
</pre>

#### 3.2.2 [alpha-job](https://github.com/coutPKprintf/alpha/tree/master/alpha-tools/alpha-job)
<pre>
    本模块主要整合elastic-job分布式定时任务的使用.
</pre>

#### 3.2.3 [alpha-redis](https://github.com/coutPKprintf/alpha/alpha/tree/master/alpha-tools/alpha-redis)
<pre>
    本模块主要整合spring-redis和redission分布式.
</pre>

#### 3.2.4 [alpha-shiro](https://github.com/coutPKprintf/alpha/tree/master/alpha-tools/alpha-shiro)
<pre>
   本模块主要整合shiro,实现权限验证和登录验证, 本项目依赖alpha-redis模块做会话的缓存.
</pre>

#### 3.2.5 [alpha-queue](https://github.com/coutPKprintf/alpha/tree/master/alpha-tools/alpha-queue)
<pre>
   本模块主要整合rabbitmq,实现消息队列的使用.
</pre>

## 4. [alpha-web](https://github.com/coutPKprintf/alpha/tree/master/alpha-web)
### 4.1 说明
<pre>
    本模块主要实现所有web应用的项目.
</pre>

### 4.2 子模块
#### 4.2.1 [alpha-web-example](https://github.com/coutPKprintf/alpha/tree/master/alpha-web/alpha-web-example)
<pre>
    本模块是web项目的一个启动样例.
</pre>

## 5. [common-application](https://github.com/coutPKprintf/alpha/tree/master/common-application)
<pre>
    本模块主要实现所有非web应用的公共部分.
</pre>

## 6. [common-config](https://github.com/coutPKprintf/alpha/tree/master/common-config)
<pre>
    本模块主要实现项目中依赖的公共配置.
</pre>

## 7. [common-enums](https://github.com/coutPKprintf/alpha/tree/master/common-enums)
<pre>
    本模块主要实现项目中依赖的公共枚举.
</pre>

## 8. [common-mybatis](https://github.com/coutPKprintf/alpha/tree/master/common-mybatis)
<pre>
    本模块主要整合mybatis,CGLib,分页插件pageHelper,实现关系数据库的访问.
</pre>

## 9. [common-util](https://github.com/coutPKprintf/alpha/tree/master/common-util)
<pre>
    本模块主要整合一些通用的工具类.
</pre>

## 10. [common-web](https://github.com/coutPKprintf/alpha/tree/master/common-web)
<pre>
    本模块主要实现所有web应用的公共部分. 包括公共参数解析,异常定义,restful接口定义,参数校验器.
</pre>

## 11. [db](https://github.com/coutPKprintf/alpha/tree/master/db)
<pre>
    此模块会增量的保存对数据的增量更新sql.
</pre>

## 12. [docs](https://github.com/coutPKprintf/alpha/tree/master/docs)
<pre>
    此模块是整个项目开发过程中的文档存放位置.
</pre>

## 13. [script](https://github.com/coutPKprintf/alpha/tree/master/script)
<pre>
    此模块主要用于脚本存放, 包括发版,打包,部署脚本等.
</pre>
