## 模块结构介绍
```
├ zero-cloud -- 智珠基础平台服务
├── zero-gateway -- 微服务网关 80
├── zero-auth -- 客户鉴权服务 8000
├── zero-common -- 基础服务工具包
├ 
├── zero-back -- 后台管理模块
├   ├── zero-user -- 用户管理 8100
├   ├── zero-system -- 系统模块 8102
├   ├── zero-desk -- 工作台模块 8103
├   ├── zero-flow -- 工作流引擎 8106
├   ├── zero-resource -- 资源模块 8107
├   ├── zero-report -- 报表模块 8108
├   └── zero-log -- 日志模块 8109
├ 
├── zero-back-api -- 后台管理模块api封装
├   ├── zero-desk-api -- 工作台api
├   ├── zero-dict-api -- 字典api
├   ├── zero-flow-api -- 工作流api
├   ├── zero-resource-api -- 资源api
├   ├── zero-scope-api -- 数据权限api
├   ├── zero-system-api -- 系统api
├   └── zero-user-api -- 用户api
├
├── zero-broker -- 保险经纪展业
├
├── zero-develop -- 代码生成 8002
├── zero-job-admin -- 任务调度平台 8000
└── zero-swagger -- 客户开户服务 8000

```
    日常开发中，主要在业务管理模块中迭代需求，api模块主要为其他业务模块提供本模块的API，微服务间内部调用。

    注意：子工程模块已规划建好，如有需要再新建子工程，需要先通过团队评审。


# 开发手册

## 目录结构规范
```
以资讯服务为例，如需对内部其他模块暴露接口：
├── mktinfo-core
├── com.minigod.zero.mktinfo.core -- 根目录
├    ├── controller -- API接口层
├    ├── service -- 业务逻辑层
├    └── mapper -- 数据持久化（含xml）
├
├── mktinfo-core-api --资讯服务内部api
├── com.minigod.zero.mktinfo.core -- 根目录
├    ├── cache -- 缓存对象
├    ├── entity -- 持久化对象
├    ├── dto -- 数据传输对象
├    ├── vo -- 视图层对象
└──  └── feign -- 内部api

如果无需对内部其他模块暴露接口：
├── mktinfo-core
├── com.minigod.zero.mktinfo.core -- 根目录
├    ├── controller -- API接口层
├    ├── service -- 业务逻辑层
├    ├── mapper -- 数据持久化（含xml）
├    ├── cache -- 缓存对象
├    ├── entity -- 持久化对象
├    ├── dto -- 数据传输对象
└──  └── vo -- 视图层对象
说明：每个大业务模块都有各自的common工具封装包
```
## 代码分支规范
* ！本次是基于零号行情系统迁移重构，没有需求文档，需要大家开发过程中先去看代码熟悉原有业务逻辑，并输出新的设计与接口文档 ！
  ![img_5.png](doc/other/images/readme-img8.png)
1. 版本经理基于master建版本分支，如：zero-mkt1.0.0（暂不带上线日期），dev、stg、prd环境部署打包都从版本分支拉代码
2. 开发人员基于版本分支建自己的版本开发分支，如：zero-mkt1.0.0_changchun，版本完成上线后删除开发分支，下个版本新建
3. 开发人员完成需求编码后先本地Commit，然后从版本分支pull代码，解决代码合并冲突后再push到自己的开发分支
4. 开发人员自行申请将自己开发分支的代码合并到版本分支进行联调或测试部署，需选择版本经理对代码合并进行审核
5. UAT验收通过后对版本分支代码进行封板，不允许再提交或合并代码；版本上线后由版本经理将版本分支合并到master分支
6. 如果存在版本并行情况，后上线的版本需要从master归并代码到版本分支（含数据库同步），解决冲突通知开发，并更新测试环境
7. 数据库脚本统一放到/doc/sql目录下面，按版本建目录，脚本文件命名规范：服务名_执行序号_脚本类型_表名_开发人员.sql



* 说明：运维CICD部署根据版本号从GitLab拉版本分支代码打包，并从数据库脚本目录拿sql脚本执行
* 注意：已执行的脚本如果需要调整请写回滚脚本在下次部署时修正，不要去改原来的脚本

##一、API篇

###1.1 新建API接口放到controller目录：
```
@RestController
@RequestMapping("api")
public class TestController {
	@GetMapping("info")
	public R<String> info(String name) {
		return R.data("Hello, My name is: " + name);
	}
}
```
注意：

a. 返回给前端的结果数据统一用R对象的方法进行封装

b. 查询数据类接口请用 @GetMapping 注解，删除数据类接口请用 @DeleteMapping 注解，其他接口用 @PostMapping 注解！

c. 通过MktinfoCoreApplication类启动服务，直接访问地址：`http://localhost:8218/api/info?name=zero`

通过微服务网关访问：`http://localhost/mktinfo-core/api/info?name=zero (令牌缺失，鉴权失败)` 请看下节。

###1.2 API鉴权

* 鉴权模块基于 JWT 封装，每次接收到请求，会拦截到需要鉴权的API信息，并对其请求头携带的Token进行认证。

a. 除网关外还需启动`AuthApplication`、`UserApplication`、 `LogApplication`、 `SystemApplication`四个服务。

b. 通过postman调用认证接口：`http://localhost/zero-auth/oauth/token` 参数如下：
```
Headers:
Authorization: Basic c3dvcmQ6c3dvcmRfc2VjcmV0
Content-Type: application/x-www-form-urlencoded
Tenant-Id: 000000
Body:
grant_type: password
username: admin
password: 21232f297a57a5a743894a0e4a801fc3
```
c. 从返回的Json中取到 `tokenType` 和 `accessToken`，重新设置请求头即可成功访问自建的API:  `http://localhost/mktinfo-core/api/info?name=zero`
```
Headers:
Authorization: Basic c3dvcmQ6c3dvcmRfc2VjcmV0
Zero-Auth: #tokenType + ' ' + #accessToken
```
* Authorization的值`c3dvcmQ6c3dvcmRfc2VjcmV0`为应用注册信息（zero_client表）clientId:clientSecret串转换为的base64编码，

* Web客户端保存位置：website.js 配置项 clientId 和 clientSecret

* Tenant-Id为入驻的租户ID，用户密码为md5加密后的值，token_type通常值为：bearer

###1.3 API放行

在Nacos上的配置文件`zero.yaml`中配置即可绕过Token校验：
```
zero:
    secure:
        skip-url:
            - /test/**
            - /api/info
```
###1.4 细粒度鉴权

* 通过`@PreAuth`注解可以为通过了Token鉴权的请求配置第二层鉴权，可以加载类或方法上
```
    @GetMapping("info")
    @PreAuth("hasRole('administrator')") //角色表zero_role中的角色别名字段role_alias
    public R<String> info(String name) {
        return R.data("Hello, My name is: " + name);
    }
```
###1.5 获取当前用户

a. 通过`AuthUtil.getUser()`获取:
```
    @GetMapping("userInfo")
	public R<String> userInfo() {
		ZeroUser zeroUser = AuthUtil.getUser();
		return R.data("Login user info: " + JSONObject.toJSONString(zeroUser));
	}
```
b. 通过参数注入`ZeroUser`获取:
```
    @GetMapping("userInfo")
    @PreAuth("hasRole('administrator')")
	public R<String> userInfo(ZeroUser zeroUser) {
		return R.data("Login user info: " + JSONObject.toJSONString(zeroUser));
	}
```
###1.6 数据缓存

通过`@Cacheable`注解可以将方法返回的数据对象缓存到Redis中，对于改动少，查询频繁的数据，须放缓存
```
    @GetMapping("info")
    @PreAuth("hasRole('administrator')")
    @Cacheable(cacheNames = "user-info", key = "#name")
    public R<String> info(String name) {
        return R.data("Hello, My name is: " + name);
    }
    // key定义规范："#root.targetClass.name +\":\"+ #root.methodName +\":\"+ #name"
```
###1.7 API聚合文档

a. 启动`SwaggerApplication`服务，需要在配置文件中配置网关地址

b. 在应用模块中引入`zero-starter-swagger`依赖并配置需要扫描包:
```
    <dependency>
        <groupId>com.minigod.zero</groupId>
        <artifactId>zero-starter-swagger</artifactId>
    </dependency>
    
    swagger:
        base-packages:
            - com.minigod.zero.auth
            - com.minigod.zero.mktinfo
```
c. API聚合文档访问地址：`http://localhost:18000/doc.html`

d. 代码样例
```
接口类:
@RestController
@RequestMapping("api")
@Api(tags = "演示接口")
public class DemoController {
    @GetMapping("info")
    @ApiOperation(value = "查看信息", notes = "接口描述")
    @ApiOperationSupport(order = 1)
    public R<String> info(String name) {
        return R.data("Hello, My Name Is: " + name);
    }
}
实体类:
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Param对象", description = "Param对象")
public class Param extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "参数名")
	private String paramName;

	@ApiModelProperty(value = "参数键")
	private String paramKey;
}
```
e. 基本使用

    界面左上角可以切换业务模块，左侧菜单前面四个是公共菜单，文档管理菜单下可以设置全局参数，个性化设置需开启动态请求参数

    Swagger Models菜单下可以看到所有接口用到的实体对象，Authorize菜单里可以为接口统一设置请求头参数：
```
Authorization: Basic c3dvcmQ6c3dvcmRfc2VjcmV0
Zero-Auth: #tokenType + ' ' + #accessToken
Tenant-Id: 000000
```
    Zero-Auth可以通过授权模块的token-endpoint接口获取(postAccessToken)，参数同**1.2-b**

###1.8 Lombok
```
统一通过@Data注解生成get、set方法，并加上@EqualsAndHashCode(callSuper = true)注解
统一通过@slf4j注解引入log对象，Controller层尽量不要打日志
```

##二、CURD篇
a. 建表规范

* 表名前缀：行情管理（zero_mktmgr_） 行情核心（zero_mktquot_） 行情资讯（zero_mktinfo_） 行情转码（zero_mkttsc_）

* 主键命名为id，类型：bigint（64），生成采用snowflake算法（Long）；日期类型：datetime，字典字段类型：int，字符串：varchar

* 表和字段都要有描述，字典字段需要描述键值对，关键字段需要加索引、唯一约束或联合索引，禁止表之间有建外键关系，命名、长度等需合理

b. 建Entity

* entity目录：所有字段都需要加注释，通过`@Data`实现set/get方法，类上加Mybatis-Plus注解`@TableName("表名")`

* 主键字段上需要加注解以实现自动采用雪花算法生成主键：`@TableId(value = "id", type = IdType.ASSIGN_ID)`

c. 建Service和Mapper

* Service接口类放service目录，需继承`BaseService`，实现类放impl目录，mapper接口和xml都放到mapper目录，需继承`BaseMapper`

d. 建Controller

* 引用Service对象建议在类上加注解`@AllArgsConstructor`，取代`@Autowired`

* 这部分代码可以自动生成，就不过多介绍，分页是借助`IPage`对象，逻辑删除不用另外写方法，只需要在Entity中为控制逻辑删除的字段加上注解就行：
```
    /**
    * 是否已删除
    */
   @TableLogic
   private Integer isDeleted;
   
```
##三、代码生成

* 本地启动服务zero-mgr工程中的DevelopApplication服务，后台管理界面`研发工具>>配置数据源`（已统一配好）

* 在`数据模型设计`菜单新增需要生成代码的数据表，并进行`模型配置`，这块需要确认每个字段的前端样式

* 在`代码生成`菜单新增代码生成的配置，然后选中点击`代码生成`按钮即可生成前后端代码，相关配置项建议：

![img.png](doc/other/images/readme-img1.png)
![img.png](doc/other/images/readme-img9.png)
* 基础业务选项这里要注意下：
* 如果数据表没有BaseEntity的通用字段，需选择否，生成的代码不会继承BaseEntity和BaseService
* 建议数据表尽量按BaseEntity加上通用字段，主键需命名为id

##四、日志记录

a. 全局异常日志会记录在表`zero_log_error`中

b. API日志需要在接口上加`@ApiLog`注解，记录在表`zero_log_api`中

##五、远程调用

* 统一使用Feign来进行微服务内部的远程调用，如果对其他模块提供接口请单独建api模块，主要放实体类和feign接口

* 模拟场景：zero-A服务提供接口给zero-B服务调用

a. 新增zero-A-api模块，建Feign接口
```
@FeignClient(
   //定义Feign指向的service-id
   value = CommonConstant.APPLICATION_DEMO_NAME 
)
public interface BlogClient{

   /**
    * 接口前缀
    */
   String API_PREFIX = "/api/blog";

   /**
    * 获取详情
    *
    * @param id 主键
    * @return
    */
   @GetMapping(API_PREFIX + "/detail")
   R<Blog> detail(@RequestParam("id") Integer id);

}
```
b. 在zero-A模块下写feign接口的实现方法
```
@RestController
@AllArgsConstructor
public class BlogClientImpl implements BlogClient {

   private BlogService service;

   @Override
   @GetMapping(API_PREFIX + "/detail")
   public R<Blog> detail(Integer id) {
      return R.data(service.getById(id));
   }

}
```
c. zero-B模块下引用zero-A-api实现接口调用
```
    <dependency>
        <groupId>com.minigod.zero</groupId>
        <artifactId>zero-A-api</artifactId>
    </dependency>
    
    // Controller
    @Autowired
    private BlogClient client;

    @GetMapping("/blog-detail")
    public R<Blog> blogDetail(Integer id) {
       R<Blog> result = client.detail(id);
       return result;
    }
```

##六、API传输加解密
![img.png](doc/other/images/readme-img4.png)
a. 依赖包引用
```
    <dependency>
        <groupId>org.springblade</groupId>
        <artifactId>blade-starter-api-crypto</artifactId>
    </dependency>
```
b. 通过注解方式实现加解密
![img_1.png](doc/other/images/readme-img5.png)
c. 配置说明
![img_2.png](doc/other/images/readme-img6.png)
```需开启jackson text-plain支持
zero:
  api:
    crypto:
      # 启用报文加密配置
      enabled: true
      # 使用AesUtil.genAesKey()生成
      aes-key: "O2BEeIv399qHQNhD6aGW8R8DEj4bqHXm"
      # 使用DesUtil.genDesKey()生成
      des-key: "jMVCBsFGDQr1USHo"
  jackson:
    # 支持text文本请求,与报文加密同时开启
    support-text-plain: true
```

##七、SDK工具

### 7.1 后台数据字典：`DictCache.java`

* 需要引入api依赖包：
```
    <dependency>
        <groupId>com.minigod.zero</groupId>
        <artifactId>zero-dict-api</artifactId>
    </dependency>
```

### 7.1 Redis工具服务类：`com.minigod.zero.core.redis.cache.ZeroRedis.java`

### 7.2 分布式缓存工具类：`com.minigod.zero.core.cache.utils.CacheUtil.java`

### 7.3 分布式锁：方法上加`@RedisLock`注解即可

### 7.4 Protobuf序列化工具：`com.minigod.zero.core.tool.utils.ProtostuffUtil.java`

### 7.5. 消息推送

a. 前端调短信验证码接口

* 短信验证码发送：`/zero-resource/sms/endpoint/send-validate`

* 短信验证码校验：`/zero-resource/sms/endpoint/validate-message`

b. 后台调短信相关接口

* 引入api依赖包调feign接口：`ISmsClient.java`
```
    <dependency>
        <groupId>com.minigod.zero</groupId>
        <artifactId>zero-resource-api</artifactId>
    </dependency>
```
c. 后台调邮件推送接口

### 7.6 上传下载

* 前端直接调用，接口类：`OssEndpoint.java`

### 7.7 导入导出

* EasyExcel工具类：`com.minigod.zero.core.excel.util.ExcelUtil.java`

a. 实体类准备
```
    @DateTimeFormat("yyyy/M/dd")
    @ExcelProperty({"时间"})
    private Date exportDate;
    
    ExcelUtil.export(response, "免费用户数据","免费用户数据表", list, FreeUser.class);
```
b. Excel读取
```
    @PostMapping("read-notice")
    public R<List<NoticeExcel>> readNotice(MultipartFile file) {
       List<NoticeExcel> list = ExcelUtil.read(file, NoticeExcel.class);
       return R.data(list);
    }
```
c. Excel导入
```
    @PostMapping("write-notice")
    public R<Boolean> writeNotice(MultipartFile file) {
       List<Notice> noticeList = new ArrayList<>();
       List<NoticeExcel> list = ExcelUtil.read(file, NoticeExcel.class);
       list.forEach(noticeExcel -> {
          String category = DictCache.getKey("notice", noticeExcel.getCategoryName());
          noticeExcel.setCategory(Func.toInt(category));
          Notice notice = BeanUtil.copy(noticeExcel, Notice.class);
          noticeList.add(notice);
       });
       return R.data(noticeService.saveBatch(noticeList));
    }
```
d. 导出单个Sheet页
```
    @GetMapping("export-notice")
    public void exportNotice(HttpServletResponse response) {
       List<NoticeExcel> list = new ArrayList<>();
       ExcelUtil.export(response, "通知公告数据", "通知公告数据表", list, NoticeExcel.class);
    }
```
e. 导出多个Sheet页
```
    ExcelWriter excelWriter = null;
    try {
        excelWriter = EasyExcel.write(response.getOutputStream()).build();

        List<LogReportCn> list = logReportCnService.downList(quotesLogDownReqDto);
        String title = "A股上报_"+ DateUtil.dateTimeNow(DateUtil.YYYY_MM_DD);
        WriteSheet writeSheet1 = EasyExcel.writerSheet(0, title).build();
        excelWriter.write(list, writeSheet1);
        quotesLogDownReqDto.setMarket(StockCommon.QuoteMarketTypeEnum.A.getCode());
        
        List<QuotesLog> quotesLogs= quotesLogService.downList(quotesLogDownReqDto);
        String detail = "A股上报明细_"+ DateUtil.dateTimeNow(DateUtil.YYYY_MM_DD);
        WriteSheet writeSheet2 = EasyExcel.writerSheet(1, detail).build();
        excelWriter.write(quotesLogs, writeSheet2);
        
        excelWriter.finish();
        response.flushBuffer();
    } catch (IOException e) {
    }
```
### 7.8 其他工具类

* 在包`com.minigod.zero.core.tool.utils.java`下面，

* 如：StringUtil、NumberUtil、DateUtil、DateTimeUtil、CollectionUtil、RsaUtil等

![img.png](doc/other/images/readme-img3.png)

##八、定时任务

* 使用的是xxl-job实现定时任务调度平台：`http://localhost:17009/xxl-job-admin`

##九、多租户设计


##服务配置

* 每个大模块的common包里面（LauncherConstant）指定了公共的Nacos配置文件，如数据库、Redis等配置建议放到这里

* 如果服务需要在Nacos配置自己特有的配置项，在Nacos上以`服务名-profile.yaml`命名新建配置文件即可
