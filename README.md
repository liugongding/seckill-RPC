### 分布式秒杀系统

## 技术栈
* 前端：html+css+jquery
* 后端：springboot+mybatis
* 数据库：mysql8.0
* 中间件：rabbitmq，redis
* Rpc框架：dubbo
* 分布式事务：seata

## 项目模块  
* **订单模块(seckill-order):**  
商品秒杀后创建订单和查询订单信息  

* **商品模块(seckill-commodity)：**  
1.用户登陆功能：  
这里用填写手机号来模拟，前端填写手机号保存到session中，防止同一用户对同一商品进行多次抢购  
2.秒杀接口地址暴露：  
这里我们采用商品订单号+md5盐化处理防止恶意刷单  
3.成功秒杀逻辑处理  
(1)秒杀成功的商品和对应的用户我们放到缓存中，缓存中的用户再次抢购直接返回重复秒杀  
(2)用户抢购的订单是否存在，如果存在则说明重复秒杀，否则我们创建订单和对商品的减库存处理  
(3)对于减库存的处理，如果秒杀活动结束了，就不需要减库存，返回活动结束  

* **mq模块(seckill-mq)：**  
(1)将商品id和用户手机号组成消息发送，消费者将这两者组装成订单号用于订单的创建  
(2)采用手动消息确认机制RabbitMQ推送消息给消费者时，会附带一个DeliveryTag，消费者就可以确定  
具体的哪那一条消息被成功消费，如果该消息未被消费我们每隔1min中重新投递该消息

* **common模块(seckill-common)：**        
存放一些实体类  
 **(1)dto**   
    接口暴露参数(Exposer)    
    秒杀返回的结果(SeckillResult)  
    执行秒杀参数(SeckillExecution)  
    执行秒杀状态参数(SeckillStatusExection)   
 **(2)entity**    
    订单(Order)  
    商品(Commodity  
    消息(User)  
    消息日志(BrokerMessageLog)      
 **(3)enums**    
    消息发送(ConstantEnum)    
    秒杀处理(HandleSeckillEnum)    
    秒杀状态(SeckillStateEnum)  
 **(4)exception**  
    秒杀重复异常(RepeatKillException)  
    秒杀结束异常(SeckillCloseException)  
    
* **api模块(seckill-api)：**  
存放dubbo接口   
 **(1)order**   
 订单接口(OrderServiceApi)  
 **(2)mq**  
 读取消息队列(ReadMqServiceApi)

## 优化方案  
* 一、页面缓存    
页面缓存的主要思路为，将一些用户经常请求的页面，例如list.html--商品列表页面，
存储到redis缓存中，在用户请求的时候直接在缓存中获取并返回，如果取缓存失败，
则利用thymeleaf的手动渲染，渲染后存入缓存，并且返回。我们可以很明显的知道，
不使用页面缓存的请求，每次都先访问数据库，然后经thymeleaf渲染，然后返回，
其中渲染的过程可能需要从磁盘中读取html模板，而使用页面缓存以后，直接在内存缓存中读取，
无需查库和渲染，只有失效的情况下才需要查库渲染，所以在些用户经常请求的页面中使用页面缓存优化，
可大大降低对数据库和服务器的压力  
* 二、对象缓存  
相对于页面缓存，对象缓存是个更细粒度的缓存，比如说在登录模块中的session中，
我们把session对应的user对象存储到redis缓存中，那么在需要user对象的页面中，
既不需要登录，也不需要更具cookie去查找数据库，只需要通过cookie在redis中获取user对象，
即可使用，同理，这样类型的缓存也会减小对数据库的压力
* 三、削峰限流
我们发放10件商品，大量流量用户情况下导致系统瘫痪，一件商品都没卖出，商品卖出率为0%
我们将用户请求放入一个队列中，假如某时间段有1000个http请求，在这个队列中我们只透过10个有效
用户请求，并且保证这10个请求99.9%到达服务端的消费者，这样商品卖出率为100%
