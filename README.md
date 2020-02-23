### 分布式秒杀系统

## 技术栈
* 前端：html+css+jquery
* 后端：springboot+mybatis
* 数据库：mysql8.0
* 中间件：rabbitmq，redis
* Rpc框架：dubbo
* 分布式事务：seata

## 项目模块
* 订单模块：用来创建订单和订单的查询
* 商品模块：用来减库存
* mq模块：生产者将发送需要订单号和商品号的消息
消费者将订单号和商品号组装、并减库存和创建订单
* common模块：存放一些实体类
* api模块：存放dubbo接口

## 项目访问
* localhost:8092/seckill/list
