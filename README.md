## 项目介绍　　
mmorpg，是一个用java编写的轻量级高性能mmorpg手游服务端框架。项目提供各种支持快速二次开发的组件，以及对生产环境的服务进行管理的工具。同时，为了使用户能够快速上手，项目提供了若干常用业务功能作为演示。

## 项目特点  
* 使用状态同步实现pk技能同步  
* 允许socket/websocket同时接入，兼容手游/页游服务端架构    
* 有独立http后台网站，为游戏运维/运营提供支持  
* 框架提供多种组件，可以直接二次开发业务逻辑  
* 提供热更机制以及jmx接口，方便对生产项目进行监控与维护      



## 第三方技术栈 
名称 | 用途 | 官网  
----|------|----     
Netty | nio socket 框架 | [http://netty.io/](http://netty.io/)  
spring | 依赖注入 | [spring](https://github.com/spring-projects/spring-framework) 
springboot | 快速搭建spring应用 | [spring-boot](https://github.com/spring-projects/spring-boot)  
groovy | 基于类替换的热更新 | [http://www.groovy-lang.org/](http://www.groovy-lang.org/)　　  
hibernate | 强大的orm框架 | [http://hibernate.org/orm/](http://hibernate.org/orm/) 
gradle | 依赖管理及项目构建 | [https://gradle.org/](https://gradle.org/)  


## ToDoList  
* 玩家数据缓存及持久化策略  
* 场景寻路及分屏算法  
* 主动技能/被动技能实现  
* buff系统  
* 更多基础设施与业务演示  

## 与jforgame项目的主要不同之处 
* 使用spring-boot集大成框架，力求最大限度重用其组件
* 网络io选用了Netty, 依赖管理选用了gradle
* 使用了spring管理对象注入  
* 重点用于演示场景及技能系统  
* hibernate,logback等第三方库由spring-boot全家桶提供


## 快速开始  
1. 使用git下载代码 git clone https://github.com/kingston-csj/mmorpg;  
2. 将代码导入带有gradle插件的IDE;    
3. 启动服务端，入口为ServerStartup类;  
4. 启动客户端，入口为ClientStartup类;


欢迎star/fork，欢迎学习/使用，期待一起贡献代码！！

## 一起交流
如果您发现bug，或者有任何疑问，请提交issue !!  
或者加入交流QQ群：780465774

