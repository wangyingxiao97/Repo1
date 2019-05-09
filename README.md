# 1.乐优商城介绍

## 1.1.项目介绍

- 乐优商城是一个全品类的电商购物网站（B2C）。
- 用户可以在线购买商品、加入购物车、下单、秒杀商品
- 可以评论已购买商品
- 管理员可以在后台管理商品的上下架、促销活动
- 管理员可以监控商品销售状况
- 客服可以在后台处理退款操作
- 希望未来3到5年可以支持千万用户的使用

## 1.2.系统架构

### 1.2.1.架构图

乐优商城架构缩略图，大图请参考课前资料：

DevOps

![lysc.png](assets/lysc.png)



nginx的负载均衡

CDN

### 1.2.2.系统架构解读

整个乐优商城可以分为两部分：后台管理系统、前台门户系统。

- 后台管理：

  - 后台系统主要包含以下功能：
    - 商品管理，包括商品分类、品牌、商品规格等信息的管理
    - 销售管理，包括订单统计、订单退款处理、促销活动生成等
    - 用户管理，包括用户控制、冻结、解锁等
    - 权限管理，整个网站的权限控制，采用JWT鉴权方案，对用户及API进行权限控制
    - 统计，各种数据的统计分析展示
  - 后台系统会采用前后端分离开发，而且整个后台管理系统会使用Vue.js框架搭建出单页应用（SPA）。
  - 预览图：

  ![](assets/leyou.png)

- 前台门户

  - 前台门户面向的是客户，包含与客户交互的一切功能。例如：
    - 搜索商品
    - 加入购物车
    - 下单
    - 评价商品等等
  - 前台系统我们会使用Nuxt结合Vue完成页面开发。出于SEO优化的考虑，我们将不采用单页应用。


无论是前台还是后台系统，都共享相同的微服务集群，包括：

- 商品微服务：商品及商品分类、品牌、库存等的服务
- 搜索微服务：实现搜索功能
- 订单微服务：实现订单相关
- 购物车微服务：实现购物车相关功能
- 用户中心：用户的登录注册等功能
- 认证中心：用户权限及服务权限认证
- Eureka注册中心
- Zuul网关服务
- Spring Cloud Config配置中心
- ...

## 1.3.技术选型

### 1.3.1.相关技术

前端技术：

- 基础的HTML5、CSS3、JavaScript（基于ES6标准）
- Vue.js 2.0以及基于Vue的UI框架：Vuetify
- 前端构建工具：WebPack
- 前端安装包工具：NPM
- Vue脚手架：Vue-cli
- Vue路由：vue-router
- ajax框架：axios
- 基于Vue的富文本框架：quill-editor

### 1.3.2 后端技术：

上面的技术组合可以在项目中解决以下电商中的典型问题：

- 利用Node.js及Vue.js技术栈，实现前后端分离开发

- 利用SpringCloud技术栈，实现真正的微服务实战开发，并且是基于SpringBoot2.0和SpringCloud最新版本

- 贴近真实的电商数据库设计，解决全品类电商的SPU和SKU管理问题

- 基于FastDFS解决大数据量的分布式文件存储问题

- 基于Elasticsearch高级聚合功能，实现商品的智能过滤搜索

- 基于LocalStorage实现离线客户端购物车，减轻服务端压力。

- 基于JWT技术及RSA非对称加密实现真正无状态的单点登录。

- 基于阿里大于实现SMS功能，解决电商短信通知问题

- 基于RabbitMQ实现可靠消息服务，解决服务间通信问题

- 使用微信SDK实现微信扫码支付，符合主流付款方式

- 基于Redis搭建高可用集群，实现可靠缓存服务即热点数据保存。

  redis持久化，集群，哨兵，主从，缓存击穿，热点key。

- 基于Thymeleaf实现页面模板和静态化，提高页面响应速度和并发能力

## 1.4 乐优商城微服务模块介绍

### 1.4.1 项目模块介绍:

**网关(api-gateway):** 

请求先经过网关-->网关从注册中心拉取服务(根据请求路径路由到相应的服务)

**Eureka服务注册中心(registry):** 

负责微服务的注册与发现

**商品管理模块(包含分类和品牌的管理)(item):**

后台管理商品的分类,品牌,规格参数以及商品的增删改操作->对外提供相应api接口给其它服务调用

**商品详情页模块(goods-page):**

采用thymeleaf加nginx实现商品详情页纯静态化,用户第一次访问该商品详情页时会在nginx的html目录下生成相应的静态html,同时nginx需要设置代理静态页面,如果nginx本地有就走nginx,没有反向代理到goods-page服务地址.使用rabbitmq与商品管理服务进行通信,商品管理服务item中商品被修改操作时使用rabbitmq通知商品详情服务进行静态页面的重新刷写.

**商品搜索模块(search):**

**订单模块(order):**

**购物车模块(cart):**

购物车模块分登录状态和未登录状态下添加购物车,未登录状态添加购物车是保存到浏览器的localStorage(H5新增的本地存储可以永久保存)里面(因为cookie的容量有限),使用carts为键,value为sku对象数组.添加前先获取localStorage里的购物车商品,如果已经存在则更新数量(两个数量相加),如果不存在则直接添加到localStorage里面.删除购物车则直接删除localStorage里的sku数组中对应的sku就行,在购物车列表页实现对商品数量的增加和减少.登录状态添加购物车,会添加到服务器的redis里面,redis是以用户的id为key,存储的是hash类型,hash中以商品的skuID为键,sku对象为value.当我们添加商品后会来到购物车列表页,在Vue的created函数中运行加载购物车列表的方法,先判断用户是否登录,如果已经登录,则把本地和远程的购物车合并到服务器redis中,如果远程中已经存在本地购物车中的商品,则以远程的为主,合并后展示数据并清除本地localstorage,同时也在登录状态实现了对远程购物车的增删改操作.前端也实现了购物车列表商品的可选操作,以便提交选中的商品到订单服务.

**图片上传模块(upload):** 

采用fastDfs作为图片上传服务器,fastDfs图片服务器使用image二级域名,上传图片的服务需要被其它服务调用,需要解决跨域的问题,在upload服务添加cors跨域允许. 图片上传的地址我们也使用了经过网关的地址,但是这样文件就会经过多次网路传输，造成不必要的网络负担,我们在不修改上传地址的时候,需要在网关中进行服务过滤,同时为了保持网关中前缀路径/api的一致,我们在nginx中使用rewrite指令重写了地址映射,当然我们也可以在upload的controller前加一个"/api",但是这样会显得不美观.

用户模块(user):

**用户中心(auth-center):**

采用客户端cookie无状态登录,使用JWT对用户信息进行加密后生成token,然后把这个token设置成cookie返回到客户端浏览器,这里使用了Feign调用了User服务的用户登录账号密码查询,然后把查询到的user信息进行简化生成token设置成cookie返回到客户端,再设置cookie当中遇到了几个问题,cookie无法正常提交,请求头里并没有cookie.

原因:1. 跨域请求cors配置类设置Credentials为true,以及添加前端域名允许

​	 2. cookie的domain是0.0.1,而我们访问是用域名访问的, 更改nginx配置让它不      	 要修改我们的host: nginx配置中添加  proxy_set_header Host $host;

​	同时zuul网关中也要设置 : add-host-header : true   #携带请求本身的host头信息,	

​	3. Zuul的敏感头过滤 : zuul内部会对一些敏感请求头进行过滤, 这里设置敏感头为空 : sensitive-header:        #禁止使用的头信息设置为空

在zuul网关中使用zuulFilter对用户状态进行判断是否已经登录.获取cookie中的token进行校验,如果没有则不放行,放回提示信息.

短信服务模块(sms-service):

