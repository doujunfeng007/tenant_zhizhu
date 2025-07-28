## 模块结构介绍
```
├── zero-biz -- 智珠聚合业务中台
├   zero-biz-common -- 业务中台工具包
├   zero-biz-bpm -- 客户开户管理（待规划） 19001
├   zero-biz-cms -- 新闻内容管理 19002
├   zero-biz-mkt -- 行情SaaS管理 19004
├   zero-biz-oms -- 运营活动管理19003
├   zero-biz-cust -- 客户个人中心 19005
├   zero-platform -- 消息转发 19006
├   zero-search -- 搜索服务 19007
├   zero-biz-trade -- 交易服务 19020
├   zero-mkt-server -- 行情管理SaaS服务 19010
├   zero-mkt-task -- 行情管理定时任务 19028 19038
├ 注意：上面主模块中
├       controller目录提供Web管理端接口，前缀back
├       openapi目录提供App端的接口，前缀open
├
├── zero-biz-api -- 各业务Feign接口 前缀/client
├    ├── zero-biz-bpm-api -- BPM Feign
├    ├── zero-biz-cust-api -- 个人中心Feign
├    ├── zero-biz-cms-api -- CMS Feign
├    ├── zero-biz-mkt-api -- 行情管理 Feign
├    ├── zero-biz-oms-api -- OMS Feign
├    ├── zero-platform-api -- 消息转发 Feign
├    └── zero-search-api -- 搜索服务 Feign
├ 
└── zero-biz-task -- 定时任务 19008 19018

```
    日常开发中，主要在业务管理模块中迭代需求，api模块主要为其他业务模块提供本模块的API，微服务间内部调用。

    注意：子工程模块已规划建好，如有需要再新建子工程，需要先通过团队评审。

## 代码分支规范
    **！基础服务模块如果特殊需求，本次尽量不错改动 ！**
![img_5.png](doc/other/images/readme-img8.png)
1. 版本经理基于master建版本分支，如：zero-biz1.0.0（暂不带上线日期），dev、stg、prd环境部署打包都从版本分支拉代码
2. 开发人员基于版本分支建自己的开发分支，如：zero-biz1.0.0_changchun，版本完成上线后删除开发分支，下个版本新建
3. 开发人员完成需求编码后先本地Commit，然后从版本分支pull代码，解决代码合并冲突后再push到自己的开发分支
4. 开发人员自行申请将自己开发分支的代码合并到版本分支进行联调或测试部署，需选择版本经理对代码合并进行审核
5. UAT验收通过后对版本分支代码进行封板，不允许再提交或合并代码；版本上线后由版本经理将版本分支合并到master分支
6. 如果存在版本并行情况，后上线的版本需要从master归并代码到版本分支（含数据库同步），解决冲突通知开发，并更新测试环境
7. 数据库脚本统一放到/doc/sql目录下面，按版本建目录，脚本文件命名规范：服务名_执行序号_脚本类型_表名_开发人员.sql


    说明：运维CICD部署根据版本号从GitLab拉版本分支代码打包，并从数据库脚本目录拿sql脚本执行  
    注意：已执行的脚本如果需要调整请写回滚脚本在下次部署时修正，不要去改原来的脚本

    缺数据库密码配置的加密与邮件推送SDK

## 系统环境

### DEV环境：

    服务器：

    MySQL库：10.9.68.213:3306/zero_cloud  sunline/Sunline!@#123

    influxdb：

    Redis集群：
        10.9.68.177:7001, 7002
        10.9.68.178:7003, 7004
        10.9.68.179:7005, 7006

    PulsarMQ：

    Nacos：http://10.9.68.172/:8848/nacos  nacos/nacos

    Sentinel:

    xxl-job：http://localhost:17009/zero-job-admin  admin/123456

    Web后台：http://localhost:1888  admin/admin

    接口文档：http://localhost:18000/doc.html

    ELK日志：

    SkyWalking：

    Prometheus：

### STG环境：


### PRD环境：