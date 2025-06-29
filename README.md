## 项目介绍　　
mmorpg，是一个用java编写的分布式高性能mmorpg手游服务端框架。项目提供各种支持快速二次开发的组件，以及对生产环境的服务进行管理的工具。同时，为了使用户能够快速上手，项目提供了若干常用业务功能作为演示。

## 项目特点  
* 使用状态同步实现pk技能同步  
* 允许socket/websocket同时接入，兼容手游/页游服务端架构    
* 有独立http后台网站，为游戏运维/运营提供支持  
* 分布式部署，无状态战斗服支持横向拓展
* 框架提供多种组件，可以直接二次开发业务逻辑  
* 提供热更机制以及jmx接口，方便对生产项目进行监控与维护    
* 尽可能使用spring/springboot组件进行搭建    


## ToDoList  
* 场景寻路及分屏算法  
* 主动技能/被动技能实现  
* buff系统  
* 更多基础设施与业务演示  

## 与jforgame项目的主要不同之处 
* JDK使用了版本17
* jforgame注重于造轮子，mmo注重于重用jforgame基础组件
* 使用spring-boot框架
* 依赖管理选用了gradle
* 重点用于演示场景及技能系统  
* 尝试使用一些新技术栈  

## 快速开始  
1. 使用git下载代码 git clone https://github.com/kingston-csj/mmorpg;  
2. 将代码导入带有gradle插件的IDE;    
3. 启动服务端，入口为ServerStartup类;  
4. 启动客户端，入口为ClientStartup类;  

欢迎star/fork，欢迎学习/使用，期待一起贡献代码！！

## 一起交流
如果您发现bug，或者有任何疑问，请提交issue !!  
我刚开通了知识星球，来向我提问吧~~
![](/screenshots/zsxq.jpg "知识星球")


## 免责申明
本项目只用于学习研究，禁止用于非法获利和商业活动。如产生法律纠纷与作者无关！！

